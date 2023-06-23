package org.startinglineup.data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import org.startinglineup.Properties;

public class PropertiesExport {
	
    public PropertiesExport() {
        super();
    }
	
    public void export() throws IOException, ClassCastException, NullPointerException { 
        java.util.Properties props = new java.util.Properties();
        Set<String> keys = org.startinglineup.Properties.getInstance().getProperties().keySet();

        Iterator<String> i = keys.iterator();
        String key = null;
        String value = null;
        while (i.hasNext()) {
            key = i.next();
            value = org.startinglineup.Properties.getInstance().get(key);
            if (value == null) {
                value = "";
            }
            props.setProperty(key, value);
        }

        FileOutputStream fos = new FileOutputStream(Properties.getInstance().getPathname());
        props.store(fos, "Persisting properties"); 
    }
}
