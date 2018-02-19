package br.com.clinica.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConversorData {
	public static String calendarToString(Calendar calendarDate, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		java.util.Date utilDate = new java.util.Date(calendarDate.getTimeInMillis());
		return sdf.format(utilDate);
	}
}
