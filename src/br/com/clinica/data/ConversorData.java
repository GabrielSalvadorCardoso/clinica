package br.com.clinica.data;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConversorData {
	public static String calendarToString(Calendar calendarDate, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		java.util.Date utilDate = new java.util.Date(calendarDate.getTimeInMillis());
		return sdf.format(utilDate);
	}
	
	public static Calendar stringToCalendar(String stringDate, String pattern) {
		try {
			java.util.Date utilDate = new SimpleDateFormat(pattern).parse(stringDate);
			Calendar calendarDate = Calendar.getInstance();
			calendarDate.setTime(utilDate);
			return calendarDate;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}		
	}
	
	public static String timeToString(Time time, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		java.util.Date utilDate = new java.util.Date(time.getTime());
		return sdf.format(utilDate);
	}
	
	public static Time stringToTime(String timeString, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			java.util.Date utilDate = sdf.parse(timeString);
			return new java.sql.Time(utilDate.getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
