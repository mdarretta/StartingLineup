package org.startinglineup.component;

import java.util.Collection;
import java.util.HashMap;

public class Teams {

	private HashMap<TeamAbbr,Team> teams;
	private static Teams instance = new Teams();
	
	public enum TeamAbbr {
		ARI("ARI"), ATL("ATL"), BAL("BAL"), BOS("BOS"), CHI("CHI"), CIN("CIN"), 
		CLE("CLE"), COL("COL"), CWS("CWS"), DET("DET"), HOU("HOU"), KC("KC"), 
		LAD("LAD"), LAA("LAA"), MIA("MIA"), MIL("MIL"), MIN("MIN"), NYM("NYM"), 
		NYY("NYY"), OAK("OAK"), PHI("PHI"), PIT("PIT"), SD("SD"), SEA("SEA"), 
		SF("SF"), STL("STL"), TEX("TEX"), TB("TB"), TOR("TOR"), WAS("WAS");
		
		private String abbr;
		
		private TeamAbbr(String abbr) {
			this.abbr = abbr;
		}
		
		public String getAbbr() {
			return abbr;
		}
	}

	private Teams() {
		teams = new HashMap<TeamAbbr,Team>();
		buildTeams();
	}

	private void buildTeams() {
		teams.put(TeamAbbr.BAL, new Team("Baltimore","Orioles",TeamAbbr.BAL.getAbbr()));
		teams.put(TeamAbbr.BOS, new Team("Boston","Red Sox",TeamAbbr.BOS.getAbbr()));
		teams.put(TeamAbbr.NYY, new Team("New York","Yankees",TeamAbbr.NYY.getAbbr()));
		teams.put(TeamAbbr.TB, new Team("Tampa Bay","Rays",TeamAbbr.TB.getAbbr()));
		teams.put(TeamAbbr.TOR, new Team("Toronto","Blue Jays",TeamAbbr.TOR.getAbbr()));
		teams.put(TeamAbbr.CWS, new Team("Chicago","White Sox",TeamAbbr.CWS.getAbbr()));
		teams.put(TeamAbbr.CLE, new Team("Cleveland","Indians",TeamAbbr.CLE.getAbbr()));
		teams.put(TeamAbbr.DET, new Team("Detroit","Tigers",TeamAbbr.DET.getAbbr()));
		teams.put(TeamAbbr.KC, new Team("Kansas City","Royals",TeamAbbr.KC.getAbbr()));
		teams.put(TeamAbbr.MIN, new Team("Minnesota","Twins",TeamAbbr.MIN.getAbbr()));
		teams.put(TeamAbbr.HOU, new Team("Houston","Astros",TeamAbbr.HOU.getAbbr()));
		teams.put(TeamAbbr.LAA, new Team("Los Angeles","Angels of Anaheim",TeamAbbr.LAA.getAbbr()));
		teams.put(TeamAbbr.OAK, new Team("Oakland","Athletics",TeamAbbr.OAK.getAbbr()));
		teams.put(TeamAbbr.SEA, new Team("Seattle","Mariners",TeamAbbr.SEA.getAbbr()));
		teams.put(TeamAbbr.TEX, new Team("Texas","Rangers",TeamAbbr.TEX.getAbbr()));
		teams.put(TeamAbbr.ATL, new Team("Atlanta","Braves",TeamAbbr.ATL.getAbbr()));
		teams.put(TeamAbbr.MIA, new Team("Miami","Marlins",TeamAbbr.MIA.getAbbr()));
		teams.put(TeamAbbr.NYM, new Team("New York","Mets",TeamAbbr.NYM.getAbbr()));
		teams.put(TeamAbbr.PHI, new Team("Philadelphia","Phillies",TeamAbbr.PHI.getAbbr()));
		teams.put(TeamAbbr.WAS, new Team("Washington","Nationals",TeamAbbr.WAS.getAbbr()));
		teams.put(TeamAbbr.CHI, new Team("Chicago","Cubs",TeamAbbr.CHI.getAbbr()));
		teams.put(TeamAbbr.CIN, new Team("Cincinnati","Reds",TeamAbbr.CIN.getAbbr()));
		teams.put(TeamAbbr.MIL, new Team("Milwaukee","Brewers",TeamAbbr.MIL.getAbbr()));
		teams.put(TeamAbbr.PIT, new Team("Pittsburgh","Pirates",TeamAbbr.PIT.getAbbr()));
		teams.put(TeamAbbr.STL, new Team("St. Louis","Cardinals",TeamAbbr.STL.getAbbr()));
		teams.put(TeamAbbr.ARI, new Team("Arizona","Diamondbacks",TeamAbbr.ARI.getAbbr()));
		teams.put(TeamAbbr.COL, new Team("Colorado","Rockies",TeamAbbr.COL.getAbbr()));
		teams.put(TeamAbbr.LAD, new Team("Los Angeles","Dodgers",TeamAbbr.LAD.getAbbr()));
		teams.put(TeamAbbr.SD, new Team("San Diego","Padres",TeamAbbr.SD.getAbbr()));
		teams.put(TeamAbbr.SF, new Team("San Francisco","Giants",TeamAbbr.SF.getAbbr()));
	}
	
	public Team getTeam(TeamAbbr abbr) {
		return teams.get(abbr);
	}
	
	public Team getTeam(String abbrStr) throws TeamNotFoundException {
		return teams.get(getTeamAbbr(abbrStr));
	}
	
	public TeamAbbr getTeamAbbr(String abbrStr) throws TeamNotFoundException {
		return new TeamAbbrResolver().resolve(abbrStr);
	}
	
	public Collection<Team> getTeams() {
		return teams.values();
	}

	public static Teams getInstance() {
		return instance;
	}
	
	private class TeamAbbrResolver {
		
		private HashMap<String, TeamAbbr> map;

		private TeamAbbrResolver() {
			map = new HashMap<String, TeamAbbr>();
			
			map.put("ARI", TeamAbbr.ARI);
			map.put("ATL", TeamAbbr.ATL);
			map.put("BAL", TeamAbbr.BAL);
			map.put("BOS", TeamAbbr.BOS);
			map.put("CHI", TeamAbbr.CHI);
			map.put("CHC", TeamAbbr.CHI);
			map.put("CIN", TeamAbbr.CIN); 
			map.put("CLE", TeamAbbr.CLE);
			map.put("COL", TeamAbbr.COL);
			map.put("CWS", TeamAbbr.CWS);
			map.put("DET", TeamAbbr.DET);
			map.put("HOU", TeamAbbr.HOU);
			map.put("KAN", TeamAbbr.KC);
			map.put("KC",  TeamAbbr.KC);
			map.put("LAD", TeamAbbr.LAD);
			map.put("LAA", TeamAbbr.LAA);
			map.put("MIA", TeamAbbr.MIA);
			map.put("MIL", TeamAbbr.MIL);
			map.put("MIN", TeamAbbr.MIN);
			map.put("NYM", TeamAbbr.NYM); 
			map.put("NYY", TeamAbbr.NYY);
			map.put("OAK", TeamAbbr.OAK);
			map.put("PHI", TeamAbbr.PHI);
			map.put("PIT", TeamAbbr.PIT);
			map.put("SAN", TeamAbbr.SD);
			map.put("SD",  TeamAbbr.SD);
			map.put("SEA", TeamAbbr.SEA); 
			map.put("SF",  TeamAbbr.SF);
			map.put("SFG", TeamAbbr.SF);
			map.put("STL", TeamAbbr.STL);
			map.put("TEX", TeamAbbr.TEX);
			map.put("TB",  TeamAbbr.TB);
			map.put("TPA", TeamAbbr.TB);
			map.put("TOR", TeamAbbr.TOR);
			map.put("WAS", TeamAbbr.WAS);
		}
		
		private TeamAbbr resolve(String abbrStr) throws TeamNotFoundException {
			try {
				return map.get(abbrStr);
			} catch (Exception e) {
				throw new TeamNotFoundException("Exception locating team: " + abbrStr, e);
			}
		}
	}
}
