package org.startinglineup.data;

import java.io.File;

import org.startinglineup.StartingLineupException;
import org.startinglineup.component.UniqueComponent;

public abstract class TemplattedFileImport extends CSVImport {
	
	protected TemplateReader reader;
	protected Template.TemplateItem recursiveItem = null;
	protected Template.TemplateItem hiddenItem = null;
	protected String recursiveData = null;
	protected String hiddenData = null;
	protected int dataIdx = 0;
	protected boolean isFirstLine = true;
	
	public TemplattedFileImport(File file) throws FileImportException {
		super(file);
		instantiateTemplate();
	}

	public void process(String[] data) throws FileImportException {
		try {
            Template template = reader.getTemplate();
 
    		UniqueComponent target = null;
         	
    		if (template.hasHeader() && isFirstLine) {
			    reader.processHeader(data);
    			isFirstLine = false;
			} else {

            for (int x=0; x<template.getItems().length; x++) {
            	Template.TemplateItem item = template.getItem(x);
       		 
            	if (item == null) {
            		continue;
            	}
            	
            	if (item.isRecursive()) {
            		recursiveItem = item;
            		recursiveData = data[dataIdx++];
            	} else if (item.isHidden()) {
            		hiddenItem = item;
            	} else if (item.isTarget()) {
            		target = (UniqueComponent) Class.forName(item.getFieldClassName()).newInstance();
            		if (instantiateTarget(target)) {
            			reader.setCurrentTarget(target);
            		}
            	} else if (item.isDone()) {
            		processTarget(target);
            		reader.resetCurrentTarget();
            	} else {
            		reader.populateItem(target, item, data[dataIdx++]);
            	}
            }
            
            dataIdx = 0;
			isFirstLine = false;
			}
			
		} catch (Exception e) {
			throw new FileImportException("Exception processing file: " + file.getAbsolutePath(), e);
		}
	}
	
	protected abstract void instantiateTemplate() throws FileImportException;
	
	protected abstract boolean instantiateTarget(UniqueComponent target) throws StartingLineupException;
	
	protected abstract void processTarget(UniqueComponent target) throws StartingLineupException;
}
