package org.startinglineup.data;

import java.util.Enumeration;
import org.startinglineup.Properties;
import org.startinglineup.StartingLineupException;

public class PropertiesImport {

	public PropertiesImport() {
		super();
	}
	
	public void run() throws StartingLineupException {
		try {
			java.util.Properties props = new java.util.Properties();
			props.load(ClassLoader.getSystemClassLoader().getResourceAsStream(
					Properties.PATHNAME));
			Enumeration<Object> keys = props.keys();
			String key = null;
			while (keys.hasMoreElements()) {
				key = (String) keys.nextElement();
				if (!key.startsWith("#") && (!key.trim().equals(""))) {
					Properties.getInstance().add(key, props.getProperty(key));
				}
			}
		} catch (Exception e) {
			throw new StartingLineupException("Exception processing properties file.");
		}
	}
}
