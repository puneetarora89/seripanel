package com.seri.web.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalendarUtil {
	
	private static Logger logger = LoggerFactory.getLogger(CalendarUtil.class.getName());
	
	public static Date addDays(int days){
		Calendar calender = Calendar.getInstance();
		calender.add(Calendar.DAY_OF_MONTH, days);
		return calender.getTime();
	}
	
	public static Date getDate(){
		return new Date();
	}
	
	public static String getDateInFormat(String format){
		DateFormat dateFormat = null;
		try {
			dateFormat = new SimpleDateFormat(format);
		} catch (Exception e) {
			logger.error("Date format {} is Wrong.",format);
			e.printStackTrace();
		}
        return dateFormat != null ? dateFormat.format(getDate()) : "";
    }
	
	public static SimpleDateFormat getSystemDateFormat(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		return dateFormat;
	}

}
