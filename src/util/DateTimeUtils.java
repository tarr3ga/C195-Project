/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;


/**
 *
 * @author jamyers
 */
public class DateTimeUtils {
    private static final String DATE_TIME_FORMAT = "MMM dd, yyyy hh:mm z";
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
    
    public static String getFormatedDateTimeString(ZonedDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        formatter.ofPattern(DATE_TIME_FORMAT);
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
        
        HashMap<String, Integer> sorted = new HashMap<>();
        
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
