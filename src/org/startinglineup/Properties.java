package org.startinglineup;

import java.util.HashMap;

public class Properties {
	
	private HashMap<String, String> map;
	private static Properties instance = new Properties();

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
	public static final String NUMBER_OF_SEASONS_TO_MODEL_PROP = "numberOfSeasonsToModel";
	public static final String STARTING_DATE_TO_MODEL_PROP = "startingDateToModel";
	public static final String ENDING_DATE_TO_MODEL_PROP = "endingDateToModel";
	public static final String STARTING_GAME_TO_MODEL_PROP = "startingGameToModel";
	public static final String ENDING_GAME_TO_MODEL_PROP = "endingGameToModel";
	public static final String AT_BAT_RESULT_GENERATOR_CLASS_PROP = "atBatResultGeneratorClass";

	public Properties() {
		map = new HashMap<String,String>();
	}
	
	public static Properties getInstance() {
		return instance;
	}
	
	public void add(String key, String value) {
		map.put(key, value);
	}

	public String get(String key) {
		return map.get(key);
	}
}
