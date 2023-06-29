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
import org.startinglineup.utils.Formatter;


/**
 * Model to support simulation execution.
 * 
 * @author Mike Darretta
 */
public class Modeler {

	/**
	 * The batter import controller.
	 */
	private BatterImport batterImport = null;

	/**
	 * The schedule import controller.
	 */
	private ScheduleImport scheduleImport = null;

	/**
	 * The starting lineup import controller.
	 */
	private StartingLineupImport lineupImport = null;

	/**
	 * The pitcher import controller.
	 */
	private PitcherImport pitcherImport = null;

	/**
	 * The starting rotation import controller.
	 */
	private StartingRotationImport rotationImport = null;

	/**
	 * The pitching advanced metrics import controller.
	 */
	private PitchingAdvancedMetricsImport pitchingWARImport = null;

	/**
	 * The offense advanced metrics import controller.
	 */
	private OffensiveAdvancedMetricsImport offensiveWARImport = null;

	/**
	 * Constructor imports all configuration files to prepare for execution.
	 */
	public Modeler() {

		try {
			new PropertiesImport().run();

			batterImport = new BatterImport(Properties.getInstance().get(Properties.BATTER_IMPORT_FILE_PROP));
			batterImport.run(true);

			lineupImport = new StartingLineupImport(Properties.getInstance().get(Properties.LINEUP_IMPORT_FILE_PROP));
			lineupImport.run();

			pitcherImport = new PitcherImport(Properties.getInstance().get(Properties.PITCHER_IMPORT_FILE_PROP));
			pitcherImport.run();

			rotationImport = new StartingRotationImport(Properties.getInstance().get(Properties.ROTATION_IMPORT_FILE_PROP));
			rotationImport.run();

			// Optional import
			if (!Properties.getInstance().get(Properties.PITCHING_ADVANCED_METRICS_IMPORT_FILE_PROP).equals("")) {
				pitchingWARImport = new PitchingAdvancedMetricsImport(
						Properties.getInstance().get(Properties.PITCHING_ADVANCED_METRICS_IMPORT_FILE_PROP));
				pitchingWARImport.run();
			}

			// Optional import
			if (!Properties.getInstance().get(Properties.OFFENSIVE_ADVANCED_METRICS_IMPORT_FILE_PROP).equals("")) {
				offensiveWARImport = new OffensiveAdvancedMetricsImport(
						Properties.getInstance().get(Properties.OFFENSIVE_ADVANCED_METRICS_IMPORT_FILE_PROP));
				offensiveWARImport.run();
			}

			// Optional imports
			scheduleImport = new ScheduleImport(
					Properties.getInstance().get(Properties.SCHEDULE_IMPORT_FILE_PROP));
			scheduleImport.run();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Executes the simulation.
	 */
	public void run() {
		try {

			int numSeasonsToModel = getNumSeasons();

			Standings.getInstance().reset();
			BatterStatsMap.getInstance().clear();

			for (int x = 0; x < numSeasonsToModel; x++) {

				Collection<GameStats> gameStats = Schedule.getInstance().play();

				Standings.getInstance().addStats(gameStats);
			}

			if (numSeasonsToModel > 1) {
				Standings.getInstance().normalizeStats(numSeasonsToModel);
				BatterStatsMap.getInstance().updateStatsForSingleSeason(numSeasonsToModel);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the imported properties based on the view inputs.
	 * 
	 * @param numSeasons The number of seasons to simulate.
	 * @param startDate  The (optional) start date for the simulation.
	 * @param endDate    The (optional) end date for the simulation.
	 * @param startGame  The (optional) starting game number for the simulation.
	 * @param endGame    The (optional) ending game number for the simulation.
	 */
	public void update(String numSeasons, String startDate, String endDate, String startGame, String endGame) {
		try {
			Properties.getInstance().add(Properties.NUMBER_OF_SEASONS_TO_MODEL_PROP, numSeasons);
			Properties.getInstance().add(Properties.STARTING_DATE_TO_MODEL_PROP, startDate);
			Properties.getInstance().add(Properties.ENDING_DATE_TO_MODEL_PROP, endDate);
			Properties.getInstance().add(Properties.STARTING_GAME_TO_MODEL_PROP, startGame);
			Properties.getInstance().add(Properties.ENDING_GAME_TO_MODEL_PROP, endGame);

			Standings.getInstance().reset();
			scheduleImport = new ScheduleImport(Properties.getInstance().get(Properties.SCHEDULE_IMPORT_FILE_PROP));
			scheduleImport.run();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Exports the updated property values based on the view imputs.
	 * 
	 * @param numSeasons The number of seasons to simulate.
	 * @param startDate  The (optional) start date for the simulation.
	 * @param endDate    The (optional) end date for the simulation.
	 * @param startGame  The (optional) starting game number for the simulation.
	 * @param endGame    The (optional) ending game number for the simulation.
	 * @exception StartingLineupException Exception wrapper.
	 */
	public void export(String numSeasons, String startDate, String endDate, String startGame, String endGame)
	    throws StartingLineupException {
		
		try {
			update(numSeasons, startDate, endDate, startGame, endGame);
			new PropertiesExport().export();
		} catch (Exception e) {
			throw new StartingLineupException(e);
		}
	}

	/**
	 * Returns the number of seasons to simulate.
	 * 
	 * @return The number of seasons.
	 */
	public int getNumSeasons() {
		return Integer.valueOf(Properties.getInstance().get(Properties.NUMBER_OF_SEASONS_TO_MODEL_PROP));
	}

	/**
	 * Returns the simulation start date.
	 * 
	 * @return The simulation start date.
	 */
	public String getStartDate() {
		return Properties.getInstance().get(Properties.STARTING_DATE_TO_MODEL_PROP);
	}

	/**
	 * Returns the simulation end date.
	 * 
	 * @return The simulation end date.
	 */
	public String getEndDate() {
		return Properties.getInstance().get(Properties.ENDING_DATE_TO_MODEL_PROP);
	}

	/**
	 * Returns the simulation start game.
	 * 
	 * @return The simulation start game.
	 */
	public String getStartGame() {
		return Properties.getInstance().get(Properties.STARTING_GAME_TO_MODEL_PROP);
	}

	/**
	 * Returns the simulation end game.
	 * 
	 * @return The simulation end game.
	 */
	public String getEndGame() {
		return Properties.getInstance().get(Properties.ENDING_GAME_TO_MODEL_PROP);
	}

	/**
	 * Returns a collection of batter stats, sorted by batting average.
	 * 
	 * @return a collection of batter stats.
	 */
	public Collection<BatterStats> getStatsByBattingAverage() {
		return BatterStatsMap.getInstance().getStatsByBattingAverage();
	}

	/**
	 * Returns a collection of batter stats, sorted by home runs.
	 * 
	 * @return a collection of batter stats.
	 */
	public Collection<BatterStats> getStatsByHomeRuns() {
		return BatterStatsMap.getInstance().getStatsByHomeRuns();
	}

	/**
	 * Returns a collection of batter stats, sorted by RBIs.
	 * 
	 * @return a collection of batter stats.
	 */
	public Collection<BatterStats> getStatsByRbis() {
		return BatterStatsMap.getInstance().getStatsByRbis();
	}

	/**
	 * Returns a collection of batter stats, sorted by OPS.
	 * 
	 * @return a collection of batter stats.
	 */
	public Collection<BatterStats> getStatsByOps() {
		return BatterStatsMap.getInstance().getStatsByOPS();
	}

	/**
	 * Returns the standings generated by the simulation.
	 * 
	 * @return The standings.
	 */
	public String getStandings() {
		return Standings.getInstance().toString();
	}

	/**
	 * Returns a formatted string to display the results of the simulation.
	 * 
	 * @return The simulation results.
	 */
	public String getResults() {

		String results = "\n";

		results += Formatter.format("Leaders by BA", 20, 1, false) + " | ";
		results += Formatter.format("PA", 3, 0, false) + " | ";
		results += Formatter.format("Avg", 5, 0, false) + " | ";
		results += Formatter.format("HR", 2, 0, false) + " | ";
		results += Formatter.format("RBI", 3, 0, false) + " | ";
		results += Formatter.format("OPS", 0, 0, false) + "\n";
		results += "-------------\n";
		results += BatterStatsMap.getInstance().forString(BatterStatsMap.getInstance().getStatsByBattingAverage(), 10);
		results += "\n";
		results += "Leaders by Home Runs\n";
		results += "--------------------\n";
		results += BatterStatsMap.getInstance().forString(BatterStatsMap.getInstance().getStatsByHomeRuns(), 10);
		results += "\n";
		results += "Leaders by RBIs\n";
		results += "---------------\n";
		results += BatterStatsMap.getInstance().forString(BatterStatsMap.getInstance().getStatsByRbis(), 10);
		results += "\n";
		results += "Leaders by OPS\n";
		results += "--------------\n";
		results += BatterStatsMap.getInstance().forString(BatterStatsMap.getInstance().getStatsByOPS(), 10);
		results += Standings.getInstance();

		return results;
	}

	/**
	 * Returns a formatted string to display the calculated results based on
	 * advanced metrics. These results are independent of the simulation results.
	 * 
	 * @return The results calculated by the advanced metrics.
	 */
	public String getAdvancedMetricResults(AdvancedMetric.MetricType type) {
		return new StandingsByAdvancedMetrics(type).toString();
	}
}
