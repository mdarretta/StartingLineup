package org.startinglineup.data;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.startinglineup.Properties;
import org.startinglineup.StartingLineupException;
import org.startinglineup.component.UniqueComponent;
import org.startinglineup.simulator.Game;
import org.startinglineup.simulator.HomeTeam;
import org.startinglineup.simulator.Schedule;
import org.startinglineup.simulator.VisitingTeam;

public class ScheduleImport extends TemplattedFileImport {
	
	private HashMap<String, Integer> teamGameNumberMap;
	private Game game;
	
	private Date startingDate = null;
	private Date endingDate = null;
	private int startingGame = 0;
	private int endingGame = 0;

	
	public ScheduleImport(File file) throws FileImportException, ParseException {
		
		super(file);
                Schedule.getInstance().clear();
		teamGameNumberMap = new HashMap<String, Integer>();
		
        DateFormat format = new SimpleDateFormat("M/d/yy", Locale.ENGLISH);

        String startingDateStr = Properties.getInstance().get(Properties.STARTING_DATE_TO_MODEL_PROP);
        String endingDateStr = Properties.getInstance().get(Properties.ENDING_DATE_TO_MODEL_PROP);
        String startingGameStr = Properties.getInstance().get(Properties.STARTING_GAME_TO_MODEL_PROP);
        String endingGameStr = Properties.getInstance().get(Properties.ENDING_GAME_TO_MODEL_PROP);
			
        if (!startingDateStr.equals("") && !endingDateStr.equals("")) {
			startingDate = format.parse(startingDateStr);
			endingDate = format.parse(endingDateStr);

		} else if (!startingGameStr.equals("") && !endingGameStr.equals("")) {
			startingGame = Integer.valueOf(startingGameStr);
			endingGame = Integer.valueOf(endingGameStr);
		}
	}
	
	
	protected void instantiateTemplate() throws FileImportException {
        this.reader = new TemplateReader(new File(
        		Properties.getInstance().get(Properties.SCHEDULE_IMPORT_TEMPLATE_PROP))) {
        };
        
        reader.run();
	}

	protected boolean instantiateTarget(UniqueComponent target) throws StartingLineupException {
		boolean rtnBoolean = false;
   		if (target instanceof Game) {
			game = (Game) target;
			rtnBoolean = true;
		}
		
		return rtnBoolean;
	}
	
	protected void processTarget(UniqueComponent target) throws StartingLineupException {

		try {
			game = (Game) reader.getCurrentTarget();
        	game = (Game) reader.populateDerivedItem(hiddenItem, hiddenData, game);
        	game = (Game) reader.populateItem(game, recursiveItem, recursiveData);
        	        	
        	Template.TemplateItem headerItem = reader.getTemplate().getHeaderItem(dataIdx-1);
        	game = (Game) reader.populateItem(game, headerItem, reader.getHeaderItemData(headerItem));
        	        	
        	if (isValidGame(game)) {			     
            	if (startingDate != null) {
					addGameToScheduleByDate(game);		
            	} else if (startingGame != 0) {
					addGamesToScheduleByGameNumber(game);
				} else {
					addGame(game);
				}
        	}
		} catch (Exception e) {
			throw new StartingLineupException(e);
		}
	}
	
	private boolean isValidGame(Game game) {
		boolean isValid = (game.getDate() != null && game.getVisitingTeam() != null && game.getHomeTeam() != null);
		if (isValid) {
			isValid = (!game.getVisitingTeam().getTeam().getAbbr().equals("") && !game.getHomeTeam().getTeam().getAbbr().equals(""));
		}
		return isValid;
	}
	
	private void addGameToScheduleByDate(Game game) {
		Date date = game.getDate();
		if ((date.compareTo(endingDate) <= 0) && (date.compareTo(startingDate) >= 0)) {
			addGame(game);
		}
	}
	
	private void addGamesToScheduleByGameNumber(Game game) {
		
		HomeTeam home = game.getHomeTeam();
		VisitingTeam visitor = game.getVisitingTeam();
		
		updateMapForKey(home.getTeam().getAbbr());
		updateMapForKey(visitor.getTeam().getAbbr());
		
		boolean homeGameToModel = isWithinGamesToModel(home.getTeam().getAbbr(), startingGame, endingGame);
		boolean visitorGameToModel = isWithinGamesToModel(visitor.getTeam().getAbbr(), startingGame, endingGame);
		
		if (homeGameToModel && visitorGameToModel) {
			addGame(game);
		} else {
			if (homeGameToModel) {
				rollbackMapForKey(home.getTeam().getAbbr());
			}
			
			if (visitorGameToModel) {
				rollbackMapForKey(visitor.getTeam().getAbbr());
			}
		}
	}
	
	private void addGame(Game game) {
		game.reset();
		Schedule.getInstance().addGame(game);
	}
	
	private void updateMapForKey(String team) {
		if (teamGameNumberMap.get(team) == null) {
			teamGameNumberMap.put(team, new Integer(1));
		} else {
			int gameNumber = teamGameNumberMap.get(team).intValue();
			teamGameNumberMap.replace(team, (gameNumber+1));
		}
	}
	
	private void rollbackMapForKey(String team) {
		int gameNumber = teamGameNumberMap.get(team).intValue();
		teamGameNumberMap.replace(team, new Integer(gameNumber-1));
	}
	
	private boolean isWithinGamesToModel(String team, int startingGame, int endingGame) {
		int gameNumber = teamGameNumberMap.get(team).intValue();
		
		return (gameNumber >= startingGame && gameNumber <= endingGame);
	}
}
