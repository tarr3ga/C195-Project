/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static String defaultTimeZone;
    
    private static final String DATE_TIME_FORMAT = "MMM dd, yyyy hh:mm a";
    private static final String STORABLE_DATE_TIME_FORMAT = "yyyy-MM-ddTHH:mmZ";
    
    public static final String[] TIMES = {"12:00", "12:15", "12:30", "12:45", "1:00", "1:15", "1:30", "1:45",
                                    "2:00", "2:15", "2:30", "2:45", "3:00", "3:15", "3:30", "3:45",
                                    "4:00", "4:15", "4:30", "4:45", "5:00", "5:15", "5:30", "5:45",
                                    "6:00", "6:15", "6:30", "6:45", "7:00", "7:15", "7:30", "7:45",
                                    "8:00", "8:15", "8:30", "8:45", "9:00", "9:15", "9:30", "9:45",
                                    "10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45"};   
    
    public static final String[] TIMEZONES = { "GMT+1  CET", "GMT+2  EET", "GMT+3  MSK", "GMT+4  SMT", "GMT+5  PKT", "GMT+6  OMSK", 
                                         "GMT+7  CXT", "GMT+8  CST", "GMT+9  JST", "GMT+10 EAST", "GMT+11 SAKT", "GMT+12 NZT",
                                         "GMT+0  GMT", "GMT-1  WAT", "GMT-2  AT", "GMT-3  ART", "GMT-4  AST", "GMT-5  EST", 
                                         "GMT-6  CST", "GMT-7  MST", "GMT-8  PST", "GMT-9  AKST", 
                                         "GMT-10 HST", "GMT-11 NT", "GMT-12 IDLW" };
    
    private static final String[] TIMEZONES_ABBREVIATIONS = {"GMT", "EGT", "GST", 
        "ADT", "EST", "CST", "MST", "AST", "HST"};
    private static final HashMap<String, Integer> TIMEZONE_OFFSETS = new HashMap<>();
    
    private static void getTimeZones() {
        int index = 2;
        
        for(String s : TIMEZONES_ABBREVIATIONS) {
            TIMEZONE_OFFSETS.put(s, index);
            index--;
        }
    }
    
    public static ZonedDateTime getZonedDateTimeFromString(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        formatter.ofPattern(DATE_TIME_FORMAT);
        
        ZonedDateTime parsed = ZonedDateTime.parse(dateTime, formatter);
        return parsed;
    }
    
    public static ZonedDateTime getZonedDateTimeFromDateParts(String timeZoneName, 
            String date, String time, String AmPm) {
        
        ZoneId zone;

        if(!timeZoneName.equals(defaultTimeZone)) {
            zone = ZoneId.of(timeZoneName);
        } else {
            zone = TimeZone.getDefault().toZoneId();
        }

        ZonedDateTime zonedDateTime = ZonedDateTime.now();

        try {
            String[] dateTimeParts = getDateParts(date, time, AmPm);

            zonedDateTime = ZonedDateTime.of(LocalDateTime.of(Integer.parseInt(dateTimeParts[0]), 
                    Integer.parseInt(dateTimeParts[1]), Integer.parseInt(dateTimeParts[2]), Integer.parseInt(dateTimeParts[3]), 
                    Integer.parseInt(dateTimeParts[4]) ), zone);
            zonedDateTime.format(FORMATTER);
        } catch(NumberFormatException ex) {

        }
                
        return zonedDateTime;
    }
    
    public static ZonedDateTime getUnalteredZonedDateTimeFromString(String dateTime) {
        String[] date = dateTime.split("T");
        String[] timeWithOffset = date[1].split("\\[");
        String time = timeWithOffset[0].substring(0,5);
        //String offset = timeWithOffset[1].substring(4,6);
        
        String[] dateParts = date[0].split("-");
        String[] timeParts = time.split(":");
        
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
        
        return formattedDateTime;
    }
    
    public static String[] getDatePartsFromZonedDateTime(ZonedDateTime dateTime) {
        String[] dateTimeParts = new String[7];
        
        dateTimeParts[0] = String.valueOf(dateTime.getYear());
        dateTimeParts[1] = String.valueOf(dateTime.getMonthValue());
        dateTimeParts[2] = String.valueOf(dateTime.getDayOfMonth());
        
        int hour = 0;
        
        if(dateTime.getHour() > 11)
            hour = dateTime.getHour() - 12;
        else
            hour = dateTime.getHour();
        
        dateTimeParts[3] = String.valueOf(hour);
        
        String minute = String.valueOf(dateTime.getMinute());
        
        if(minute.length() == 1) 
            minute = "0" + minute;
        
        dateTimeParts[4] = minute;
        
        String AmPm = "AM";
        
        if(dateTime.getHour() > 11)
            AmPm = "PM";
        
        dateTimeParts[5] = AmPm;
        
        //TODO Get Timezone information
        dateTimeParts[6] = "";
        
        return dateTimeParts;
    }
    
    public static String[] getDateParts(String date, String time, String AmPm) {
        String[] dateTimeParts = new String[5];
        
        String[] dateParts = date.split("-");
        
        dateTimeParts[0] = dateParts[0];
        dateTimeParts[1] = dateParts[1];
        dateTimeParts[2] = dateParts[2];
        
        String[] hourMinutes = time.split(":");
               
        dateTimeParts[4] = hourMinutes[1];
        
        if(AmPm.equals("AM") && hourMinutes[0].equals("12")) {
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
    
    public static LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        
        return localDate;
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
            int month = z.getMonthValue() - 1;
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
        
        long hour = TimeUnit.MILLISECONDS.toHours(differenceInMillis);
        dateTime = dateTime.minusHours(hour);
        
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
                timeZoneName = "Europe/Paris";
                break;
            case "GMT+2  EET":
                timeZoneName = "Africa/Cairo";
                break;
            case "GMT+3  MSK":
                timeZoneName = "Asia/Qatar";
                break;
            case "GMT+4  SMT":
                timeZoneName = "Europe/Moscow";
                break;
            case "GMT+5  PKT":
                timeZoneName = "Asia/Karachi";
                break;
            case "GMT+6  OMSK":
                timeZoneName = "Antarctica/Vostok";
                break;
            case "GMT+7  CXT":
                timeZoneName = "Asia/Jakarta";
                break;
            case "GMT+8  CST":
                timeZoneName = "Australia/Brisbane";
                break;
            case "GMT+9  JST":
                timeZoneName = "Asia/Seoul";
                break;
            case "GMT+10 EAST":
                timeZoneName = "Australia/Sydney";
                break;
            case "GMT+11 SAKT":
                timeZoneName = "Asia/Vladivostok";
                break;
            case "GMT+12 NZT":
                timeZoneName = "Pacific/Fiji";
                break;
            case "GMT+0  GMT":
                timeZoneName = "Atlantic/Canary";
                break;
            case "GMT-1  WAT":
                timeZoneName = "Atlantic/Cape_Verde";
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
        }   
        
        return timeZoneName;
    }
    
    public static String getTimeZoneAbbreviation(String gmt) {
        String timeZoneAbbreviation = "";
        
        switch(gmt) {
            case "Europe/Paris":
                timeZoneAbbreviation = "GMT+1  CET";
                break;
            case "Africa/Cairo":
                timeZoneAbbreviation= "GMT+2  EET";
                break;
            case "Asia/Qatar":
                timeZoneAbbreviation = "GMT+3  MSK";
                break;
            case "Europe/Moscow":
                timeZoneAbbreviation = "GMT+4  SMT";
                break;
            case "Asia/Karachi":
                timeZoneAbbreviation = "GMT+5  PKT";
                break;
            case "Antarctica/Vostok":
                timeZoneAbbreviation = "GMT+6  OMSK";
                break;
            case "Asia/Jakarta":
                timeZoneAbbreviation = "GMT+7  CXT";
                break;
            case "Asia/Singapore":
                timeZoneAbbreviation = "GMT+8  CST";
                break;
            case "Asia/Tokyo":
                timeZoneAbbreviation = "GMT+9  JST";
                break;
            case "Australia/Sydney":
                timeZoneAbbreviation = "GMT+10 EAST";
                break;
            case "Asia/Vladivostok":
                timeZoneAbbreviation = "GMT+11 SAKT";
                break;
            case "Pacific/Fiji":
                timeZoneAbbreviation = "MT+12 NZT";
                break;
            case "Greenwich":
                timeZoneAbbreviation = "GMT+0  GMT";
                break;
            case "Atlantic/Azores":
                timeZoneAbbreviation = "GMT-1  WAT";
                break;
            case "Atlantic/South_Georgia":
                timeZoneAbbreviation = "GMT-2  AT";
                break;
            case "America/Buenos_Aires":
                timeZoneAbbreviation = "GMT-3  ART";
                break;
            case "America/Goose_Bay":
                timeZoneAbbreviation = "GMT-4  AST";
                break;  
            case "America/New_York":
                timeZoneAbbreviation = "GMT-5  EST";
                break;  
            case "America/Chicago":
                timeZoneAbbreviation = "GMT-6  CST";
                break;  
            case "America/Boise":
                timeZoneAbbreviation = "GMT-7  MST";
                break;
            case "America/Los_Angeles":
                timeZoneAbbreviation = "GMT-8  PST";
                break;
            case "America/Anchorage":
                timeZoneAbbreviation = "GMT-9  AKST";
                break;
            case "Pacific/Honolulu":
                timeZoneAbbreviation = "GMT-10 HST";
                break;
            case "Pacific/Midway":
                timeZoneAbbreviation = "GMT-11 NT";
                break;
        }   
        
        return timeZoneAbbreviation;
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