/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


/**
 *
 * @author jamyers
 */
public class DateTimeUtils {
    private static final String DATE_TIME_FORMAT = "MMM dd, yyyy hh:mm a";
    private static final String STORABLE_DATE_TIME_FORMAT = "yyyy-MM-ddTHH:mmZ";
    
    private static final String[] timezones = {"GMT", "EGT", "GST", 
        "ADT", "EST", "CST", "MST", "AST", "HST"};
    private static final HashMap<String, Integer> timezoneOffsets = new HashMap<>();
    
    private static void getTimeZones() {
        int index = 2;
        
        for(String s : timezones) {
            timezoneOffsets.put(s, index);
            index--;
        }
    }
    
    public static ZonedDateTime getZonedDateTimeFromString(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        formatter.ofPattern(DATE_TIME_FORMAT);
        
        ZonedDateTime parsed = ZonedDateTime.parse(dateTime, formatter);
        return parsed;
    }
    
    public static ZonedDateTime getUnalteredZonedDateTimeFromString(String dateTime) {
        String[] date = dateTime.split("T");
        String[] timeWithOffset = date[1].split("\\[");
        String[] time = timeWithOffset[0].split("-");
        
        String[] dateParts = date[0].split("-");
        String[] timeParts = time[0].split(":");
        
        String zoneName = timeWithOffset[1].substring(0, timeWithOffset[1].length() - 1);
        
        ZoneId zone = ZoneId.of(zoneName);
        
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        
        ZonedDateTime parsed = ZonedDateTime.of(year, month, day, hour, minute, 0, 0, zone);
        
        return parsed;
    }
    
    public static Timestamp getTimeStampFromStrng(String s) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date parsedDate = new Date();
        
        try {
            parsedDate = dateFormat.parse(s);
        } catch (Exception ex) {
            
        }
        
        Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
        
        return timestamp;
    }
    
    public static String getFormatedDateTimeString(ZonedDateTime dateTime) {
        //DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        //formatter.ofPattern(DATE_TIME_FORMAT);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        
        String formattedDateTime = dateTime.format(formatter);
        
        return formattedDateTime;
    }
    
    public static String getFormatedDateTimeStringFromTimestamp(Timestamp dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        DateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
        
        String formattedDateTime = format.format(dateTime);
        
        return formattedDateTime;
    }
    
    public static String getStorableDateTimeString(ZonedDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(STORABLE_DATE_TIME_FORMAT);
        String formattedDateTime = dateTime.format(formatter);
        
        System.out.println(formattedDateTime);
        
        return formattedDateTime;
    }
    
    public static String[] getDateParts(String date, String time, String AmPm) {
        String[] dateTimeParts = new String[5];
        
        String[] dateParts = date.split("-");
        
        dateTimeParts[0] = dateParts[0];
        dateTimeParts[1] = dateParts[1];
        dateTimeParts[2] = dateParts[2];
        
        String[] hourMinutes = time.split(":");
               
        dateTimeParts[4] = hourMinutes[1];
        
        if(AmPm.equals("AM") && hourMinutes[0] != "12") {
            Integer convert = Integer.parseInt(dateTimeParts[3]);
            convert -= 1;
            dateTimeParts[3] = String.valueOf(convert);
        } else if(AmPm.equals("PM") && !hourMinutes[0].equals("12")) {
            Integer hour = Integer.parseInt(hourMinutes[0]);
            hour += 11;
            dateTimeParts[3] = hour.toString();
        } else {
            dateTimeParts[3] = "11";
        }
       
        return dateTimeParts;
    }
    
    public static String adjustTimeForTimezone(String start, String end, String timezone) {
        String time = "";
        getTimeZones();
        
        String zone = TimeZone.getDefault().getDisplayName();
        
        return time;
    }
    
    public static HashMap getAppointmentPerMonth(ArrayList<ZonedDateTime> appointments) {        
        HashMap<Integer, Integer> byMonth = new HashMap<>();
        HashMap<String, Integer> sorted = new HashMap<>();
        
        for(ZonedDateTime z : appointments) {
            int month = z.getMonthValue();
            int year = z.getYear();
            
            String dateString = "" + month + year;
            Integer date = Integer.parseInt(dateString);
            
            if(byMonth.containsKey(date)) {
                Integer count = byMonth.get(date) + 1;
                
                byMonth.replace(date, count);
            } else {
                byMonth.put(date, 1);
            }
        }
        
        for(Map.Entry<Integer, Integer> entry : byMonth.entrySet()) {
            String dateString = "" + entry.getKey();
            String monthString = dateString.substring(0, 1);
            String yearString = dateString.substring(1, 5);
            
            System.out.println(yearString);
            
            Integer month = Integer.parseInt(monthString);
            
            String monthName = getMonth(month);
            
            String finalDateString = monthName + " " + yearString;
            
            System.out.println(finalDateString);
            
            sorted.put(finalDateString, entry.getValue());
        }
        
        sorted = sortHashMapByDate(sorted);
        
        return sorted;
    }
    
    public static ZonedDateTime adjustForTimeZones(ZonedDateTime dateTime) {
        TimeZone timezone = TimeZone.getDefault();
        TimeZone offsetTimeZone = TimeZone.getTimeZone(dateTime.getZone());
        
        
        
        long differenceInMillis = timezone.getRawOffset() - offsetTimeZone.getRawOffset() + 
                timezone.getDSTSavings() - offsetTimeZone.getDSTSavings();
        System.out.println("util.DateTimeUtils.adjustForTimeZones() differenceInMillis = " + differenceInMillis);
        
        System.out.println("util.DateTimeUtils.adjustForTimeZones() hour = " + dateTime.getHour());
        
        long hour = TimeUnit.MILLISECONDS.toHours(differenceInMillis);
        dateTime = dateTime.minusHours(hour);
        
        System.out.println("util.DateTimeUtils.adjustForTimeZones() millis to hours = " + hour);
        
        System.out.println("util.DateTimeUtils.adjustForTimeZones() hour = " + dateTime.getHour());
        
        return dateTime;
    }
    
    public static Date getTimeWithSecondsZeroed(Date date) {
        Calendar cal = Calendar.getInstance();
        
        cal.set(Calendar.SECOND, 0);
        
        return cal.getTime();
    }
    
    public static String getTimeZoneName(String gmt) {
        String timeZoneName = "";
        
        switch(gmt) {
            case "GMT+1  CET":
                timeZoneName = "";
                break;
            case "GMT+2  EET":
                timeZoneName = "";
                break;
            case "GMT+3  MSK":
                timeZoneName = "";
                break;
            case "GMT+4  SMT":
                timeZoneName = "";
                break;
            case "GMT+5  PKT":
                timeZoneName = "";
                break;
            case "GMT+6  OMSK":
                timeZoneName = "";
                break;
            case "GMT+7  CXT":
                timeZoneName = "";
                break;
            case "GMT+8  CST":
                timeZoneName = "";
                break;
            case "GMT+9  JST":
                timeZoneName = "";
                break;
            case "GMT+10 EAST":
                timeZoneName = "";
                break;
            case "GMT+11 SAKT":
                timeZoneName = "";
                break;
            case "MT+12 NZT":
                timeZoneName = "";
                break;
            case "GMT+0  GMT":
                timeZoneName = "Atlantic/Azores";
                break;
            case "GMT-1  WAT":
                timeZoneName = "Atlantic/Azores";
                break;
            case "GMT-2  AT":
                timeZoneName = "Atlantic/South_Georgia";
                break;
            case "GMT-3  ART":
                timeZoneName = "America/Buenos_Aires";
                break;
            case "GMT-4  AST":
                timeZoneName = "America/Goose_Bay";
                break;  
            case "GMT-5  EST":
                timeZoneName = "America/New_York";
                break;  
            case "GMT-6  CST":
                timeZoneName = "America/Chicago";
                break;  
            case "GMT-7  MST":
                timeZoneName = "America/Boise";
                break;
            case "GMT-8  PST":
                timeZoneName = "America/Los_Angeles";
                break;
            case "GMT-9  AKST":
                timeZoneName = "America/Anchorage";
                break;
            case "GMT-10 HST":
                timeZoneName = "Pacific/Honolulu";
                break;
            case "GMT-11 NT":
                timeZoneName = "Pacific/Midway";
                break;
            case "GMT-12 IDLW":
                timeZoneName = "";
                break;
        }   
        
        return timeZoneName;
    }
    
    private static String getMonth(int month) {
        String monthName = "";
        
        switch(month) {
            case 0:
                monthName = "January";
                break;
            case 1:
                monthName = "February";
                break;
            case 2:
                monthName = "March";
                break;
            case 3:
                monthName = "April";
                break;
            case 4:
                monthName = "May";
                break;
            case 5:
                monthName = "June";
                break;
            case 6:
                monthName = "July";
                break;
            case 7:
                monthName = "Augsust";
                break;
            case 8:
                monthName = "September";
                break;
            case 9:
                monthName = "October";
                break;
            case 10:
                monthName = "November";
                break;
            case 11:
                monthName = "December";
                break;
        }
        
        return monthName;
    }
    
    private static HashMap<String, Integer> sortHashMapByDate(HashMap<String, Integer> dates) {
        String[] months = new String[12];
        for(int i =0; i < 12; i++) {
            months[i] = getMonth(i);
        }
        
        ArrayList<Integer> years = new ArrayList<>();
        
        for(Map.Entry<String, Integer> entry : dates.entrySet()) {
            String[] dateParts = entry.getKey().split(" ");
            
            if(!years.contains(Integer.parseInt(dateParts[1]))) {
                years.add(Integer.parseInt(dateParts[1]));
            }
        }
        
        Collections.sort(years);
        
        LinkedHashMap<String, Integer> sorted = new LinkedHashMap<>();
        
        for(int i = 0; i < years.size(); i++) {
            for(int i2 = 0; i2 < 12; i2++){
                for(Map.Entry<String, Integer> entry : dates.entrySet()) {
                    String[] dateParts = entry.getKey().split(" ");
                    Integer year = Integer.parseInt(dateParts[1]);
                    
                    if(dateParts[0].equals(months[i2])  && Objects.equals(year, years.get(i))) {
                        sorted.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
        return sorted;
    }   
}