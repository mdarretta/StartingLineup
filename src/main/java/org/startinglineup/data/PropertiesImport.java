package org.startinglineup.data;

import java.io.File;
import org.startinglineup.Properties;

public class PropertiesImport extends FileImport {
	
	public PropertiesImport() {
		super(new File("conf/startingLineup.properties"));
		super.pattern = "=";
	}
	
	public void process(String[] data) throws FileImportException {
		try {
                    Properties.getInstance().setPathname(file.getAbsolutePath());
		    String key = null;
		    String value = null;
		    
			key = data[0].trim();
			if (data.length > 1) {
				value = data[1].trim();
			}
                        if (!key.startsWith("#") && (!key.trim().equals(""))) {
			    Properties.getInstance().add(key, value);
                        }
		} catch (Exception e) {
			throw new FileImportException("Exception processing file: " + file.getAbsolutePath(), e);
		}
	}
}
