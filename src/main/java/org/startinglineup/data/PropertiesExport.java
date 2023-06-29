package org.startinglineup.data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.HashMap;
import org.startinglineup.Properties;

public class PropertiesExport {
	
    public PropertiesExport() {
        super();
    }
	
    public void export() throws IOException, ClassCastException, NullPointerException { 
        java.util.Properties props = new java.util.Properties();
		props.load(ClassLoader.getSystemClassLoader().getResourceAsStream(
				Properties.PATHNAME));
        HashMap<String, String> currentProps = Properties.getInstance().getProperties();

        Iterator<String> keys = currentProps.keySet().iterator();
        String key = null;
        String value = null;
        while (keys.hasNext()) {
            key = keys.next();
            value = Properties.getInstance().get(key);
            props.setProperty(key, value);
        }

        FileOutputStream fos = new FileOutputStream(Properties.PATHNAME);
        props.store(fos, "File updated by user input");
    }
}