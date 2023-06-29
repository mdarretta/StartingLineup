package org.startinglineup.data;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.startinglineup.component.Player;
import org.startinglineup.component.TeamNotFoundException;
import org.startinglineup.component.UniqueComponent;

public class TemplateReader extends CSVImport {
	
	private Template template = null;
	private int index = 0;
	private UniqueComponent target = null;
	private Template.TemplateItem currItem = null;
	private HashMap<Template.TemplateItem, String> headerItems;
		
	public TemplateReader(String pathname) {
		super(pathname);
		this.template = new Template(1000);
		this.headerItems = new HashMap<Template.TemplateItem, String>();
	}
	
	public void process(String[] data) throws FileImportException {
		try {
			template.addItem(index++, data);
	    } catch (ClassNotFoundException cnf) {
	    	throw new FileImportException(cnf);
	    }
	}
	
	public UniqueComponent populateItem(UniqueComponent o, Template.TemplateItem item, String data) 
			throws ParseException, IllegalAccessException, ClassNotFoundException, 
			InstantiationException, NoSuchMethodException, InvocationTargetException {

    	UniqueComponent rtnObj = null;

    	if (item != null) {
    		
	    	String fieldName = item.getFieldName();
	    	boolean isRecursive = item.isRecursive();
	    	boolean isDerived = item.isDerived();
	    	if (!fieldName.equals("")) {
	        	String fieldClassName = item.getFieldClassName();
	    		
	    		if (fieldClassName.equals("java.lang.String")) {
	    			rtnObj = populateField(o, data.trim(), fieldName, isRecursive);
	    		} else if (fieldClassName.equals("java.lang.Integer")) {
	    			rtnObj = populateField(o, Float.valueOf(data).intValue(), fieldName, isRecursive);
	    		} else if (fieldClassName.equals("java.lang.Float")) {
	    			rtnObj = populateField(o, Float.valueOf(data.trim()).floatValue(), fieldName, isRecursive);
	    		} else if (fieldClassName.equals("java.util.Date")) {
	    			DateFormat format = new SimpleDateFormat("M/d/yy", Locale.ENGLISH);
	    			Date date = format.parse(data);
	    			rtnObj = populateField(o, date, fieldName, isRecursive);
	    		} else if (isDerived) {
	    			String[] classes = fieldClassName.split(Template.DELIMITER_STR);
	    			Class<?> derivedClass = Class.forName(classes[0]);
	    			UniqueComponent currentObj =  (UniqueComponent) Class.forName(
	    					classes[1]).getDeclaredConstructor().newInstance();
	    			
	    			currentObj = populateField(currentObj, data, item.getSubclassFieldName(), isRecursive);
	    			Constructor<?> cons = derivedClass.getConstructor(currentObj.getClass());
	    			UniqueComponent derivedObj = (UniqueComponent) cons.newInstance(currentObj);
	    			rtnObj = populateField(o,derivedObj,fieldName,isRecursive);
	    		} else {
	    			rtnObj = populateField(o, data, fieldName, isRecursive);	    			
	    		}
	    	}
    	}
    	
    	return rtnObj;
	}
	
	public void addHeaderItem(Template.TemplateItem item, String data) {
		headerItems.put(item, data);
	}
	
	public String getHeaderItemData(Template.TemplateItem item) {
		return headerItems.get(item);
	}
	
	public Object populateDerivedItem(Template.TemplateItem item, String data, UniqueComponent... targets) 
	    throws TeamNotFoundException, IllegalAccessException, ParseException, ClassNotFoundException, 
	    InstantiationException, NoSuchMethodException, InvocationTargetException {
		
		Boolean found = false;
		UniqueComponent rtnObj = null;
		for (UniqueComponent target : targets) {
			if (found) {
				break;
			}
			Class<?> objectClass = target.getClass();
			for (Field field : objectClass.getDeclaredFields()) {
			    if (field.getName().equals(item.getFieldName())) {
			    	rtnObj = populateItem(target, item, data);
			    	found = true;
			    	break;
			    }
			}
		}
		
		return populateItem(rtnObj, item, data);
	}
	
	private UniqueComponent populateField(UniqueComponent target, Object value, String fieldName, boolean isRecursive)
			throws IllegalAccessException {
		
		Field field = getField(target.getClass(), fieldName);
		
		field.setAccessible(true);
		field.set(target, value);
		return target;
	}
	
	public void setCurrentTarget(UniqueComponent target) {
		this.target = target;
	}
	
	public UniqueComponent getCurrentTarget() {
		return this.target;
	}
	
	public void setCurrentItem(Template.TemplateItem currItem) {
		this.currItem = currItem;
	}
	
	public Template.TemplateItem getCurrentItem() {
		return currItem;
	}
	
	public void resetCurrentTarget() {
		this.target = null;
	}
	
	/**
	 * Processes a header item..
	 * @param data The header data to process.
	 * @throws FileImportException Error reading header item.
	 */
   	public void processHeader(String data[]) throws FileImportException {
   		try {
       		for (int x=0; x < template.getNumHeaderItems(); x++) {
       		    addHeaderItem(template.getHeaderItem(x), data[x]);
       		}
    	} catch (Exception e) {
    		throw new FileImportException(e);
    	}
   	}
   	
	private static Field getField(Class<?> myClass, String fieldName) {
		
		boolean done = false;
		Field field = null;
		
		while(!done) {
			try {
			    field = myClass.getDeclaredField(fieldName);
			    done = true;
			} catch (NoSuchFieldException nsf) {
				// check the superclass
			    myClass = myClass.getSuperclass();
			}
		}
		
		return field;
	}
 	
	public static Player parsePlayerName(Player player) throws IllegalAccessException {
		
    	Field field = getField(Player.class, "lastname");
		String[] splitName = player.getLastname().split(" ");
		
		if (splitName.length > 1) {
			String firstname = splitName[0].trim();
			String lastname = splitName[1].trim();
			for (int y=2; y<splitName.length; y++) {
				lastname = lastname + " " + splitName[y].trim();
			}
			
			field = getField(Player.class, "firstname");
			field.setAccessible(true);
			field.set(player, firstname);
			
			field = getField(Player.class, "lastname");
			field.setAccessible(true);
			field.set(player, lastname);     
		}
    	
    	return player;
	}
	
	public Template getTemplate() {
		return template;
	}
	
}
