package org.startinglineup;

import java.util.Collection;

import org.startinglineup.data.BatterImport;
import org.startinglineup.data.PitcherImport;
import org.startinglineup.data.PropertiesImport;
import org.startinglineup.data.ScheduleImport;
import org.startinglineup.data.StartingLineupImport;
import org.startinglineup.data.StartingRotationImport;
import org.startinglineup.simulator.BatterStatsMap;
import org.startinglineup.simulator.GameStats;
import org.startinglineup.simulator.Schedule;
import org.startinglineup.simulator.Standings;

import java.io.File;

public class Driver {
	
    public static void main(String[] args) {
    	
        BatterImport batterImport = null;
        ScheduleImport scheduleImport = null;
        StartingLineupImport lineupImport = null;
        PitcherImport pitcherImport = null;
        StartingRotationImport rotationImport = null;
        int numSeasonsToModel = 1;
               
        try {
            new PropertiesImport().run();

            batterImport = new BatterImport(new File(
            		Properties.getInstance().get(Properties.BATTER_IMPORT_FILE_PROP)));
            batterImport.run(true);           
            
            lineupImport = new StartingLineupImport(new File(
            		Properties.getInstance().get(Properties.LINEUP_IMPORT_FILE_PROP)));
            lineupImport.run();            		
            
            pitcherImport = new PitcherImport(new File(
            		Properties.getInstance().get(Properties.PITCHER_IMPORT_FILE_PROP)));
            pitcherImport.run();
            
            rotationImport = new StartingRotationImport(new File(
            		Properties.getInstance().get(Properties.ROTATION_IMPORT_FILE_PROP)));
            rotationImport.run();

            scheduleImport = new ScheduleImport(new File(
            		Properties.getInstance().get(Properties.SCHEDULE_IMPORT_FILE_PROP)));
            scheduleImport.run();
            
            numSeasonsToModel = Integer.valueOf(
            		Properties.getInstance().get(Properties.NUMBER_OF_SEASONS_TO_MODEL_PROP));
        } catch (Exception e) {
        	e.printStackTrace();
        	System.exit(0);
        }
        
        for (int x=0; x < numSeasonsToModel; x++) {
        	System.out.println("Running model " + (x+1) + "....");
    		run();
    	}
    	
        Standings.getInstance().normalizeStats(numSeasonsToModel);
        BatterStatsMap.getInstance().updateStatsForSingleSeason(numSeasonsToModel);
        
        System.out.println();
        System.out.println("Leaders by BA");
        System.out.println("-------------");
        System.out.println(BatterStatsMap.getInstance().forString(
        		BatterStatsMap.getInstance().getStatsByBattingAverage(), 10));
        System.out.println("Leaders by Home Runs");
        System.out.println("-------------");
        System.out.println(BatterStatsMap.getInstance().forString(
        		BatterStatsMap.getInstance().getStatsByHomeRuns(), 10));
        System.out.println("Leaders by RBIs");
        System.out.println("-------------");
        System.out.println(BatterStatsMap.getInstance().forString(
        		BatterStatsMap.getInstance().getStatsByRbis(), 10));
        System.out.println("Leaders by OPS");
        System.out.println("-------------");
        System.out.println(BatterStatsMap.getInstance().forString(
        		BatterStatsMap.getInstance().getStatsByOPS(), 10));
        System.out.println(Standings.getInstance());
    }
    
    private static void run() {
        Collection<GameStats> gameStats = Schedule.getInstance().play();
        Standings.getInstance().addStats(gameStats);
    }
}
