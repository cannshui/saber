package dn.cs.saber.util;

import java.sql.Timestamp;
import java.util.Date;


/**
 * Date: 2014-3-25
 * 
 * @author hewen_deng
 * @version 1.0
 */
public class SimpleUtil {
	
	public static int convertDataFlag(String dataFlag) {
		return 0;
	}
	
	public static String toJsonStr(String... pairs) {
		// TODO: Just a vague(?) return.
		if (pairs == null || pairs.length % 2 != 0) {
			return "{tag: \"ERROR\"}";
		}
		StringBuilder jsonBuilder = new StringBuilder("{");
		jsonBuilder
			.append(pairs[0]).append(":")
			.append("\"").append(pairs[1]).append("\"");
		for (int i = 0, iLen = pairs.length; i < iLen; i++) {
			jsonBuilder.append(pairs[i]).append("");
		}
		return "";
	}
	
	public static void fixStrSuffix(StringBuilder strBuilder, int repLen, String newSuffix) {
		int len = strBuilder.length();
		strBuilder.replace(len - repLen, len, newSuffix);
	}
	
	public static void delStrSuffix(StringBuilder strBuilder, int sfxLen) {
		int len = strBuilder.length();
		strBuilder.delete(len - sfxLen, len);
	}
	
	/**
	 * Format a float value correspond to the given precision.
	 * 
	 * @param f
	 * @param prec Use Integer as declearation type, so can be null.
	 * @return
	 */
	public static String formatFloat(float f, Integer prec) {
		if (prec == null) {
			prec = 0;
		}
		return String.format("%." + prec + "f", f);
	}
	
	public static String formatSqlDate(Date date) {
		if (date == null) {
			date = new Date();
		}
		return new Timestamp(date.getTime()).toString();
	}
	
	/**
	 * A simple filter to escape special characters for building sql.
	 * Now only '[Single quotation mark].
	 * 
	 * <p>TODO: Lower performance??? 
	 *  
	 * @param value
	 * @return
	 */
	public static String formatSqlSFiled(String value) {
		if (value == null) {
			return null;
		}
		value = value.replace("'", "''");
		return "'" + value + "'";
	}
	
}