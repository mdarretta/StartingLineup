package org.startinglineup.utils;

import java.text.NumberFormat;

/**
 * Encapsulates a set of static string formatting methods.
 * 
 * @author Mike Darretta
 */
public final class Formatter {

	/**
	 * Default constructor is private to prevent instantiation.
	 */
	private Formatter() {
		super();
	}

	/**
	 * Returns a formatted statistic string for a specified number of decimal spaces.
	 * @param stat The stat to format.
	 * @param decimalPlaces The number of decimal spaces to render.
	 * @return The formatted string.
	 */
	public static String getFormattedStat(float stat, int decimalPlaces) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(decimalPlaces);
		nf.setMaximumFractionDigits(decimalPlaces);
		return nf.format(stat);
	}

	/**
	 * Returns a formatted integer string with a specified number of tabs for "padding".
	 * @param intVal The integer value.
	 * @param totalLength The total string length including padding.
	 * @param isPrePadding Indicates whether the padding is before or after the value.
	 * @return The formatted string.
	 */
	public static String getPaddedInt(int intVal, int totalLength, boolean isPrePadding) {
		String returnStr = new String("" + intVal);
		if (returnStr.length() < totalLength) {
			returnStr = formatPadding(returnStr, totalLength, isPrePadding);
		}

		return returnStr;
	}

	/**
	 * Returns a formatted string with a specified number of tabs for "padding".
	 * @param string The string.
	 * @param totatLength The total string length including padding.
	 * @param isPrePadding Indicates whether the padding is before or after the value.
	 * @return The formatted string.
	 */
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

	/**
	 * Returns a formatted string for an integer with a specified number of tabs.
	 * @param value The integer.
	 * @param numTabs The number of tabs.
	 * @param isPretabbed Indicates whether the tabs are before or after the integer.
	 * @return The formatted string.
	 */
	public static String format(int value, int numTabs, boolean isPretabbed) {
		return format(value, 0, numTabs, isPretabbed);
	}

	/**
	 * Returns a formatted string for an integer with a specified padding and number of tabs.
	 * @param value The integer.
	 * @param padding The necessary padding to include in the formatted string.
	 * @param numTabs The number of tabs.
	 * @param isPretabbed Indicates whether the tabs are before or after the integer.
	 * @return The formatted string.
	 */
	public static String format(int value, int padding, int numTabs, boolean isPretabbed) {
		return format("" + value, padding, numTabs, isPretabbed);
	}

	/**
	 * Returns a formatted string with a specified number of tabs.
	 * @param string The string.
	 * @param numTabs The number of tabs.
	 * @param isPretabbed Indicates whether the tabs are before or after the string.
	 * @return The formatted string.
	 */
	public static String format(String string, int numTabs, boolean isPretabbed) {
		return format(string, 0, numTabs, isPretabbed);
	}

	/**
	 * Returns a formatted string with a specified padding and number of tabs.
	 * @param string The string.
	 * @param padding The necessary padding to include in the formatted string.
	 * @param numTabs The number of tabs.
	 * @param isPretabbed Indicates whether the tabs are before or after the string.
	 * @return The formatted string.
	 */
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
