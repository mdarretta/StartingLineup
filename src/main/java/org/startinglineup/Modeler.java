package org.startinglineup;

import java.util.Collection;

import org.startinglineup.component.AdvancedMetric;
import org.startinglineup.data.BatterImport;
import org.startinglineup.data.PitcherImport;
import org.startinglineup.data.PropertiesExport;
import org.startinglineup.data.PropertiesImport;
import org.startinglineup.data.ScheduleImport;
import org.startinglineup.data.StartingLineupImport;
import org.startinglineup.data.StartingRotationImport;
import org.startinglineup.data.OffensiveAdvancedMetricsImport;
import org.startinglineup.data.PitchingAdvancedMetricsImport;
import org.startinglineup.simulator.BatterStats;
import org.startinglineup.simulator.BatterStatsMap;
import org.startinglineup.simulator.GameStats;
import org.startinglineup.simulator.Schedule;
import org.startinglineup.simulator.Standings;
import org.startinglineup.simulator.StandingsByAdvancedMetrics;

import java.io.File;

public class Modeler {

    private BatterImport batterImport = null;
    private ScheduleImport scheduleImport = null;
    private StartingLineupImport lineupImport = null;
    private PitcherImport pitcherImport = null;
    private StartingRotationImport rotationImport = null;
    private PitchingAdvancedMetricsImport pitchingWARImport = null;
    private OffensiveAdvancedMetricsImport offensiveWARImport = null;

    public Modeler() {
               
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

            // Optional import
            if (!Properties.getInstance().get(Properties.PITCHING_ADVANCED_METRICS_IMPORT_FILE_PROP).equals("")) {
                pitchingWARImport = new PitchingAdvancedMetricsImport(new File(
                        Properties.getInstance().get(Properties.PITCHING_ADVANCED_METRICS_IMPORT_FILE_PROP)));
                pitchingWARImport.run();
            }

            // Optional import
            if (!Properties.getInstance().get(Properties.OFFENSIVE_ADVANCED_METRICS_IMPORT_FILE_PROP).equals("")) {
                offensiveWARImport = new OffensiveAdvancedMetricsImport(new File(
                        Properties.getInstance().get(Properties.OFFENSIVE_ADVANCED_METRICS_IMPORT_FILE_PROP)));
                offensiveWARImport.run();
            }

            // Optional imports
            scheduleImport = new ScheduleImport(new File(
            		Properties.getInstance().get(Properties.SCHEDULE_IMPORT_FILE_PROP)));
            scheduleImport.run();
        } catch (Exception e) {
        	e.printStackTrace();
        	System.exit(0);
        }
    }
    
    public void run() {
        try {
            Collection<GameStats> gameStats = Schedule.getInstance().play();
            Standings.getInstance().reset();
            Standings.getInstance().addStats(gameStats);
    
            int numSeasonsToModel = getNumSeasons();
    
            if (numSeasonsToModel > 1) {
                Standings.getInstance().normalizeStats(numSeasonsToModel);
                BatterStatsMap.getInstance().updateStatsForSingleSeason(numSeasonsToModel);
            }
            
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    public void update(String numSeasons, String startDate, String endDate, String startGame, String endGame) {
        try {
            Properties.getInstance().add(Properties.NUMBER_OF_SEASONS_TO_MODEL_PROP, numSeasons);
            Properties.getInstance().add(Properties.STARTING_DATE_TO_MODEL_PROP, startDate);
            Properties.getInstance().add(Properties.ENDING_DATE_TO_MODEL_PROP, endDate);
            Properties.getInstance().add(Properties.STARTING_GAME_TO_MODEL_PROP, startGame);
            Properties.getInstance().add(Properties.ENDING_GAME_TO_MODEL_PROP, endGame);

            Standings.getInstance().reset();
            scheduleImport = new ScheduleImport(new File(
            		Properties.getInstance().get(Properties.SCHEDULE_IMPORT_FILE_PROP)));
            scheduleImport.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void export(String numSeasons, String startDate, String endDate, String startGame, String endGame) {
        try {
            update(numSeasons, startDate, endDate, startGame, endGame);
            new PropertiesExport().export(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getNumSeasons() {
        return Integer.valueOf(Properties.getInstance().get(Properties.NUMBER_OF_SEASONS_TO_MODEL_PROP));
    }

    public String getStartDate() {
        return Properties.getInstance().get(Properties.STARTING_DATE_TO_MODEL_PROP);
    }

    public String getEndDate() {
        return Properties.getInstance().get(Properties.ENDING_DATE_TO_MODEL_PROP);
    }

    public String getStartGame() {
        return Properties.getInstance().get(Properties.STARTING_GAME_TO_MODEL_PROP);
    }

    public String getEndGame() {
        return Properties.getInstance().get(Properties.ENDING_GAME_TO_MODEL_PROP);
    }

    public Collection<BatterStats> getLeadersByBattingAverage() {
        return BatterStatsMap.getInstance().getStatsByBattingAverage();
    }

    public Collection<BatterStats> getStatsByBattingAverage() {
        return BatterStatsMap.getInstance().getStatsByBattingAverage();
    }

    public Collection<BatterStats> getStatsByHomeRuns() {
        return BatterStatsMap.getInstance().getStatsByHomeRuns();
    }

    public Collection<BatterStats> getStatsByRbis() {
        return BatterStatsMap.getInstance().getStatsByRbis();
    }

    public Collection<BatterStats> getStatsByOps() {
        return BatterStatsMap.getInstance().getStatsByOPS();
    }
  
    public String getStandings() {
        return Standings.getInstance().toString();
    }

    public String getResults() {
        int numSeasonsToModel = getNumSeasons();

        if (numSeasonsToModel > 1) {
            Standings.getInstance().normalizeStats(numSeasonsToModel);
            BatterStatsMap.getInstance().updateStatsForSingleSeason(numSeasonsToModel);
        }
        
        String results = "\n";

        results += "Leaders by BA\n";
        results += "-------------\n";
        results += BatterStatsMap.getInstance().forString(
                        BatterStatsMap.getInstance().getStatsByBattingAverage(), 10);
        results += "\n";
        results += "Leaders by Home Runs\n";
        results += "-------------\n";
        results += BatterStatsMap.getInstance().forString(
                        BatterStatsMap.getInstance().getStatsByHomeRuns(), 10);
        results += "\n";
        results += "Leaders by RBIs\n";
        results += "-------------\n";
        results += BatterStatsMap.getInstance().forString(
                        BatterStatsMap.getInstance().getStatsByRbis(), 10);
        results += "\n";
        results += "Leaders by OPS\n";
        results += "-------------\n";
        results += BatterStatsMap.getInstance().forString(
                        BatterStatsMap.getInstance().getStatsByOPS(), 10);
        results += Standings.getInstance();
        results += new StandingsByAdvancedMetrics(AdvancedMetric.MetricType.WAR).toString();
        
        /*
         * Test code
         */
        results += "Number of current players: " + 
                (org.startinglineup.data.ALStartingLineupMap.getInstance().getNumPlayers() +
                org.startinglineup.data.NLStartingLineupMap.getInstance().getNumPlayers()) + "\n";
        results += "Number of current pitchers: " + 
                org.startinglineup.data.StartingRotationMap.getInstance().getNumPlayers();
        return results;
    }
}
