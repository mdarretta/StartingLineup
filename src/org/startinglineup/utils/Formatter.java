package org.startinglineup.utils;

import java.text.NumberFormat;

public class Formatter {

   private Formatter() {}

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

	public static String getPaddedString(String strVal, int totalLength, boolean isPrePadding) {
		if (strVal.length() < totalLength) {
			strVal = formatPadding(strVal, totalLength, isPrePadding);
		}

		return strVal;
	}

	private static String formatPadding(String string, int totalLength, boolean isPrePadding) {
      if (isPrePadding) {
         for (int x=string.length(); x < totalLength; x++) {
            string = " " + string;
         }
      } else {
         for (int x=string.length(); x < totalLength; x++) {
            string = string + " ";
         }
      }

      return string;
   } 
}
