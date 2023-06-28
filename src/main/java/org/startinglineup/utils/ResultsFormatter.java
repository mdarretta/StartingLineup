package org.startinglineup.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class ResultsFormatter {

	private Collection<StringMap> resultsMap;

	public ResultsFormatter() {
		super();
		resultsMap = new ArrayList<StringMap>();
	}

	public void addResults(StringMap results) {
		resultsMap.add(results);
	}

	public Collection<StringMap> getResults() {
		return resultsMap;
	}

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

	public StringMap createMap() {
		return new StringMap();
	}

	public class StringMap {

		private HashMap<Integer, String> map;

		private StringMap() {
			map = new HashMap<Integer, String>();
		}

		public void put(Integer lineNum, String line) {
			map.put(lineNum, line);
		}

		public String get(Integer lineNum) {
			return map.get(lineNum);
		}

		public String get(int lineNum) {
			return get(Integer.valueOf(lineNum));
		}

		public int numLines() {
			return map.size();
		}
	}
}
