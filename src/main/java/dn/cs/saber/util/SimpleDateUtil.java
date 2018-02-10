package dn.cs.saber.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Only using "yyyy-MM-dd HH:mm:ss" format.
 * 
 * Date: 2014-4-22
 * 
 * @author hewen_deng
 * @version 1.0
 */
public class SimpleDateUtil {

	private static final int[][] MONTH_DAYS = {
		//1  2   3   4   5   6   7   8   9   10  11  12
		{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31},
		{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}
	};
	
	/**
	 * Parse as yyyy-MM-dd HH:mm:ss.
	 * 
	 * @param date
	 * @return
	 */
	public static String parse19(Date date) {
		SimpleDateFormat sdf19 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf19.format(date);
	}
	
	/**
	 * Parse as yyyy-MM-dd HH:mm:ss.
	 * 
	 * @param source
	 * @return
	 */
	public static Date parse19(String source) {
		SimpleDateFormat sdf19 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf19.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Parse as yyyy-MM-dd HH:mm.
	 * 
	 * @param date
	 * @return
	 */
	public static String parse16(Date date) {
		SimpleDateFormat sdf16 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf16.format(date);
	}
	
	/**
	 * Parse as yyyy-MM-dd HH:mm.
	 * 
	 * @param source
	 * @return
	 */
	public static Date parse16(String source) {
		SimpleDateFormat sdf16 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			return sdf16.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Parse as yyyy-MM-dd HH.
	 * 
	 * @param date
	 * @return
	 */
	public static String parse13(Date date) {
		SimpleDateFormat sdf13 = new SimpleDateFormat("yyyy-MM-dd HH");
		return sdf13.format(date);
	}
	
	/**
	 * Parse as yyyy-MM-dd HH.
	 * 
	 * @param source
	 * @return
	 */
	public static Date parse13(String source) {
		SimpleDateFormat sdf13 = new SimpleDateFormat("yyyy-MM-dd HH");
		try {
			return sdf13.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Parse as yyyy-MM-dd.
	 * 
	 * @param date
	 * @return
	 */
	public static String parse10(Date date) {
		SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");
		return sdf10.format(date);
	}
	
	/**
	 * Parse as yyyy-MM-dd.
	 * 
	 * @param source
	 * @return
	 */
	public static Date parse10(String source) {
		SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf10.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Parse as the custom format.
	 * 
	 * @param date
	 * @param custom
	 * @return
	 */
	public static String parse(Date date, String custom) {
		SimpleDateFormat sdf = new SimpleDateFormat(custom);
		return sdf.format(date);
	}
	
	/**
	 * Parse as the custom format.
	 * 
	 * @param source
	 * @param custom
	 * @return
	 */
	public static Date parse(String source, String custom) {
		SimpleDateFormat sdf = new SimpleDateFormat(custom);
		try {
			return sdf.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Check if a leap year.
	 * 
	 * @param cal
	 * @return 1: Leap; 0: Nonleap.
	 */
	public static int checkLeap(Calendar cal) {
		return checkLeap(cal.get(Calendar.YEAR));
	}
	
	/**
	 * Check if a leap year.
	 * 
	 * @param date
	 * @return 1: Leap; 0: Nonleap.
	 */
	public static int checkLeap(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return checkLeap(cal.get(Calendar.YEAR));
	}
	
	/**
	 * Check if a leap year.
	 * 
	 * @param cal
	 * @return 1: Leap; 0: Nonleap.
	 */
	public static int checkLeap(int year) {
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * Get the given month's day number.
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonthDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return MONTH_DAYS[checkLeap(cal)][cal.get(Calendar.MONTH)];
	}
	
	/**
	 * Get the given year's day number of all(12) months.
	 * 
	 * @param year
	 * @return
	 */
	public static int[] getMonthDays(int year) {
		return MONTH_DAYS[year];
	}
	
}
