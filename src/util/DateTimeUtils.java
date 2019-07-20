/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author jamyers
 */
public class DateTimeUtils {
    private static final String DATE_TIME_FORMAT = "MMM dd, yyyy hh:mm ";
    private static final String STORABLE_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    
    public static String getFormatedDateTimeString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        String formattedDateTime = dateTime.format(formatter);
        
        return formattedDateTime;
    }
    
    public static String getStorableDateTimeString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(STORABLE_DATE_TIME_FORMAT);
        String formattedDateTime = dateTime.format(formatter);
        
        System.out.println(formattedDateTime);
        
        return formattedDateTime;
    }
}
