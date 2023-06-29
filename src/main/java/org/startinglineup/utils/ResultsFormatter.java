package org.startinglineup.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Utility method to format a set of string results into a defined rendering.
 * The current main purpose of this utility class is to format a set of results
 * into columnar format.
 * 
 * @author Mike Darretta
 */
public class ResultsFormatter {

	/**
	 * A collection of mapped string results.
	 */
	private Collection<StringMap> resultsMap;

	/**
	 * Default constructor.
	 */
	public ResultsFormatter() {
		super();
		resultsMap = new ArrayList<StringMap>();
	}

	/**
	 * Adds a set of mapped string results.
	 * @param results The string results.
	 */
	public void addResults(StringMap results) {
		resultsMap.add(results);
	}

	/**
	 * Returns a set of mapped string results.
	 * @return The string results.
	 */
	public Collection<StringMap> getResults() {
		return resultsMap;
	}

	/**
	 * Returns a string representation of the encapsulated string results 
	 * in columnar format.
	 * @param maxRows The maximum number of rows to inspect. Since the mapped
	 * string results each have an independent number of string "rows", this
	 * allows for proper formatting across all mapped string sets.
	 * @return The formatted results.
	 */
	public String formatResultsAsColumnarStr(int maxRows) {

		String rtnStr = "";

		Iterator<StringMap> i = null;
		String str = null;
		
		for (int x=0; x < maxRows; x++) {
			i = resultsMap.iterator();
			while (i.hasNext()) {
				str = i.next().get(x);
				if (str == null) {
					str = "";
				}
				rtnStr += Formatter.format(str, 10, 2, false);
			}
			rtnStr += "\n";
		}
		
		return rtnStr;
	}

	/**
	 * Creates a string map.
	 * @return The string map.
	 */
	public StringMap createMap() {
		return new StringMap();
	}

	/**
	 * Inner class encapsulates a mapping of string against a line number.
	 */
	public class StringMap {

		/**
		 * A map of strings keyed by line number.
		 */
		private HashMap<Integer, String> map;

		/**
		 * Default constructor.
		 */
		private StringMap() {
			map = new HashMap<Integer, String>();
		}

		/**
		 * Adds a map entry.
		 * @param lineNum The line number.
		 * @param line The string line.
		 */
		public void put(Integer lineNum, String line) {
			map.put(lineNum, line);
		}

		/**
		 * Returns the string for the line number.
		 * @param lineNum The line number.
		 * @return The string.
		 */
		public String get(Integer lineNum) {
			return map.get(lineNum);
		}

		/**
		 * Returns the string for the line number.
		 * @param lineNum The line number.
		 * @return The string.
		 */
		public String get(int lineNum) {
			return get(Integer.valueOf(lineNum));
		}

		/**
		 * Returns the number of lines for this string mapping.
		 * @return
		 */
		public int numLines() {
			return map.size();
		}
	}
}
