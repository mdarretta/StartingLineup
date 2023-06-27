package org.startinglineup.utils;

import java.text.NumberFormat;

public class Formatter {

	private Formatter() {
		super();
	}

	public static String getFormattedStat(float stat, int decimalPlaces) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(decimalPlaces);
		nf.setMaximumFractionDigits(decimalPlaces);
		return nf.format(stat);
	}

	public static String getPaddedInt(int intVal, int totalLength, boolean isPrePadding) {
		String returnStr = new String("" + intVal);
		if (returnStr.length() < totalLength) {
			returnStr = formatPadding(returnStr, totalLength, isPrePadding);
		}

		return returnStr;
	}

	private static String formatPadding(String string, int totalLength, boolean isPrePadding) {
		if (isPrePadding) {
			for (int x = string.length(); x < totalLength; x++) {
				string = "\t" + string;
			}
		} else {
			for (int x = string.length(); x < totalLength; x++) {
				string = string + "\t";
			}
		}

		return string;
	}

	public static String format(int value, int numTabs, boolean isPretabbed) {
		return format(value, 0, numTabs, isPretabbed);
	}

	public static String format(int value, int padding, int numTabs, boolean isPretabbed) {
		return format("" + value, padding, numTabs, isPretabbed);
	}

	public static String format(String string, int numTabs, boolean isPretabbed) {
		return format(string, 0, numTabs, isPretabbed);
	}

	public static String format(String string, int padding, int numTabs, boolean isPretabbed) {
		String tabs = "";

		for (int x = 0; x < numTabs; x++) {
			tabs += "\t";
		}

		// Calculate the padding as the difference of the input padding minus the string
		// size
		padding = padding - string.length();
		if (padding > 0) {
			for (int x = 0; x < padding; x++) {
				string += " ";
			}
		}

		if (isPretabbed) {
			string = tabs + string;
		} else {
			string += tabs;
		}

		return string;
	}
}
