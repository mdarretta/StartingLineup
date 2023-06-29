package org.startinglineup;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Singleton to store and access external properties.
 * 
 * @author Mike Darretta
 */
public class Properties {

	/**
	 * The property map.
	 */
	private HashMap<String, String> map;

	/**
	 * The singleton instance.
	 */
	private static Properties instance = new Properties();
	
	/**
	 * The path to the main properties file.
	 */
	private String pathname;

	public static final String PATHNAME = "conf/startinglineup.properties";
	public static final String BATTER_IMPORT_FILE_PROP = "batterImportFile";
	public static final String BATTER_IMPORT_TEMPLATE_PROP = "batterImportTemplate";
	public static final String LINEUP_IMPORT_FILE_PROP = "lineupImportFile";
	public static final String LINEUP_IMPORT_TEMPLATE_PROP = "lineupImportTemplate";
	public static final String PITCHER_IMPORT_FILE_PROP = "pitcherImportFile";
	public static final String PITCHER_IMPORT_TEMPLATE_PROP = "pitcherImportTemplate";
	public static final String ROTATION_IMPORT_FILE_PROP = "rotationImportFile";
	public static final String ROTATION_IMPORT_TEMPLATE_PROP = "rotationImportTemplate";
	public static final String SCHEDULE_IMPORT_FILE_PROP = "scheduleImportFile";
	public static final String SCHEDULE_IMPORT_TEMPLATE_PROP = "scheduleImportTemplate";
	public static final String PITCHING_ADVANCED_METRICS_IMPORT_FILE_PROP = "pitchingAdvancedMetricsImport";
	public static final String PITCHING_ADVANCED_METRICS_IMPORT_TEMPLATE_PROP = "pitchingAdvancedMetricsTemplate";
	public static final String OFFENSIVE_ADVANCED_METRICS_IMPORT_FILE_PROP = "offensiveAdvancedMetricsImport";
	public static final String OFFENSIVE_ADVANCED_METRICS_IMPORT_TEMPLATE_PROP = "offensiveAdvancedMetricsTemplate";
	public static final String NUMBER_OF_SEASONS_TO_MODEL_PROP = "numberOfSeasonsToModel";
	public static final String STARTING_DATE_TO_MODEL_PROP = "startingDateToModel";
	public static final String ENDING_DATE_TO_MODEL_PROP = "endingDateToModel";
	public static final String STARTING_GAME_TO_MODEL_PROP = "startingGameToModel";
	public static final String ENDING_GAME_TO_MODEL_PROP = "endingGameToModel";
	public static final String AT_BAT_RESULT_GENERATOR_CLASS_PROP = "atBatResultGeneratorClass";

	/**
	 * Constructor instantiates the properties map.
	 */
	private Properties() {
		map = new HashMap<String, String>();
	}

	/**
	 * Returns the singleton instance.
	 * @return The singleton instance.
	 */
	public static Properties getInstance() {
		return instance;
	}

	/**
	 * Adds a property.
	 * @param key The property key.
	 * @param value The property value.
	 */
	public void add(String key, String value) {
		map.put(key, value);
	}

	/**
	 * Returns a property.
	 * @param key The property key.
	 * @return The property value.
	 */
	public String get(String key) {
		return map.get(key);
	}

	/**
	 * Returns the property map.
	 * @return The property map.
	 */
	public HashMap<String, String> getProperties() {
		return map;
	}

	/**
	 * Sets the main properties file pathname.
	 * @param pathname The pathname.
	 */
	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

	/**
	 * Returns the main properties file pathname.
	 * @return The pathname.
	 */
	public String getPathname() {
		return pathname;
	}

	/**
	 * Returns a stringified version of this instance.
	 * @return The stringified version of this instance.
	 */
	public String toString() {
		String rtnStr = "";
		Iterator<String> keys = map.keySet().iterator();
		String key = null;
		while (keys.hasNext()) {
			key = keys.next();
			rtnStr += (key + ":" + get(key) + "\n");
		}

		return rtnStr;
	}
}
