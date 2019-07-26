/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author jamyers
 */
public class CustomDate {
    private final ZonedDateTime dateTime;
    
    public CustomDate(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    @Override
    public String toString() {
       return DateTimeFormatter.ofPattern("dd/MM/yyy hh:mm").format(dateTime);
    }
}
