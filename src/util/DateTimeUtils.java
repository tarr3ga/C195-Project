/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.TimeZone;


/**
 *
 * @author jamyers
 */
public class DateTimeUtils {
    private static final String DATE_TIME_FORMAT = "MMM dd, yyyy hh:mm ";
    private static final String STORABLE_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    
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
    
    public static String getFormatedDateTimeString(LocalDateTime dateTime) {
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
    
    public static String getStorableDateTimeString(LocalDateTime dateTime) {
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
            dateTimeParts[3] = hourMinutes[0];
        } else if(AmPm.equals("PM") && hourMinutes[0] != "12") {
            Integer hour = Integer.parseInt(hourMinutes[0]);
            hour += 12;
            dateTimeParts[3] = hour.toString();
        }
       
        return dateTimeParts;
    }
    
    public static String adjustTimeForTimezone(String start, String end, String timezone) {
        String time = "";
        getTimeZones();
        
        String zone = TimeZone.getDefault().getDisplayName();
        
        return time;
    }
}
