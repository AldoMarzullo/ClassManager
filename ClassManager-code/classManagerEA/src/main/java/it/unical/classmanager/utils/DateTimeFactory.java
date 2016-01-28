package it.unical.classmanager.utils;

import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * This is a utility class for generate some temporal values.
 * 
 * @author Aloisius92
 */
public class DateTimeFactory {
	private static int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	private static int startYear = 1970;
	private static Random random = new Random();
		
	/**
	 * This function creates a random date.
	 * 
	 * @return A random Calendar Date
	 */
	public static Calendar getRandomDate(){
		int month = random.nextInt(12);
		int day = random.nextInt(daysInMonth[month]);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int year = startYear + random.nextInt(currentYear-startYear);
		return new GregorianCalendar(year, month, day);
	}	
	
	/**
	 * This function creates a random date in a specified year.
	 * 
	 * @param year the specified year
	 * 
	 * @return A Calendar Date
	 */
	public static Calendar getRandomDate(int year){
		int month = random.nextInt(12);
		int day = random.nextInt(daysInMonth[month]);
		
		GregorianCalendar date = new GregorianCalendar(year, month, day);
		System.out.println("Created Date: "+date.getTime());
		
		return date;
	}		
	
	/**
	 * This function creates a random date less than the specified year.
	 * 
	 * @param year the specified year
	 * 
	 * @return A Calendar Date
	 */	
	public static Calendar getRandomDateLessThanYear(int year){
		int month = random.nextInt(12);
		int day = random.nextInt(daysInMonth[month]);
		int randomYear = startYear + random.nextInt(year-startYear);
		return new GregorianCalendar(randomYear, month, day);
	}	
	
	/**
	 * This function creates a random date in a specified year and month.
	 * 
	 * @param year the specified year
	 * @param month the specified month
	 * 
	 * @return A Calendar Date
	 */
	public static Calendar getRandomDate(int year, int month){
		month = month >= 12 ? 11 : month;
		month = month < 0 ? 0 : month;		
		int day = random.nextInt(daysInMonth[month]);
		return new GregorianCalendar(year, month, day);
	}
	
	/**
	 * This function creates a date in the specified year, month and day.
	 * 
	 * @param year the specified year
	 * @param month the specified month
	 * @param day the specified day
	 * 
	 * @return A Calendar Date
	 */
	public static Calendar getDate(int year, int month, int day){
		return new GregorianCalendar(year, month, day);
	}
	
	/**
	 * This function creates a date between two months.
	 * 
	 * @param beginMonth the start month
	 * @param endMonth the end month
	 * 
	 * @return A Calendar Date
	 */
	public static Calendar getRandomDateBetweenMonths(int beginMonth, int endMonth){	
		int month;
		if(beginMonth>endMonth){	
			int diff = daysInMonth.length-beginMonth;
			beginMonth = (beginMonth+diff)%daysInMonth.length;
			endMonth = (endMonth+diff)%daysInMonth.length;
			
			month = random.nextInt(endMonth);
			month = (month-diff)%daysInMonth.length;
			month = month < 0 ? daysInMonth.length+month : month;
		} else {
			month = beginMonth + random.nextInt(endMonth-beginMonth);		
		}
		int day = random.nextInt(daysInMonth[month]);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int year = startYear + random.nextInt(currentYear-startYear);
		return new GregorianCalendar(year, month, day);
	}
	
	/**
	 * This function creates a date between two months, in the specified year.
	 * 
	 * @param year the specified year
	 * @param beginMonth the start month
	 * @param endMonth the end month
	 * 
	 * @return A Calendar Date
	 */
	public static Calendar getRandomDateBetweenMonths(int year, int beginMonth, int endMonth){
		int month;
		if(beginMonth>endMonth){	
			int diff = daysInMonth.length-beginMonth;
			beginMonth = (beginMonth+diff)%daysInMonth.length;
			endMonth = (endMonth+diff)%daysInMonth.length;
			
			month = random.nextInt(endMonth);
			month = (month-diff)%daysInMonth.length;
			month = month < 0 ? daysInMonth.length+month : month;
		} else {
			month = beginMonth + random.nextInt(endMonth-beginMonth);		
		}
		month = beginMonth + random.nextInt(endMonth-beginMonth);
		int day = random.nextInt(daysInMonth[month]);
		
		GregorianCalendar date = new GregorianCalendar(year, month, day);
		System.out.println("Created Date: "+date.getTime());	
		
		return date;
	}
	
	/**
	 * This function create a random year.
	 * 
	 * @return A random year
	 */
	public static int getRandomYear(){
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int randomYear = startYear + random.nextInt(currentYear-startYear);
		System.out.println("Random Year: "+randomYear);
		return startYear + random.nextInt(currentYear-startYear);
	}
	
	/**
	 * This function create a random year between two years.
	 * 
	 * @param beginYear the startYear
	 * @param endYear the endYear
	 * 
	 * @return A random year
	 */
	public static int getRandomYearBetween(int beginYear, int endYear){
		int diff = Math.abs(endYear - beginYear);
		int randomYear = beginYear + random.nextInt(diff);
		System.out.println("Random Year Between ("+beginYear+","+endYear+"): "+randomYear);			
		return randomYear;
	}
	
	/**
	 * This function create a random time.
	 * 
	 * @return A random time
	 */
	@SuppressWarnings("deprecation")
	public static Time getRandomTime(){
		return new Time(random.nextInt(24), random.nextInt(60), random.nextInt(60));
	}
	
	/**
	 * This function create a random time between two times.
	 * 
	 * @param beginTime the start time
	 * @param endTime the end time
	 * 
	 * @return A random time
	 */
	public static Time getRandomTimeBetween(Time beginTime, Time endTime){
		long diff = endTime.getTime()-beginTime.getTime();
		return new Time(diff);
	}
}
