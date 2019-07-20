/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author jamyers
 */
public class TextSanitizer {
    public static String sanitizeTextBlock(String text) {
        String sanitizedText = "";     
        
        String check = text.substring((text.length() - 11), 11);
        
        if(check.equals("!sanitized!"))
            return text;
        
        char[] workingText = text.toCharArray();
        
        for(char c : workingText) {
            if(c == '\'' || c == '\"') {
                sanitizedText += "\\";
            }
            
            sanitizedText += c;
        }
        
        sanitizedText += "!sanitized!";
        
        System.out.println("util.TextSanitizer.sanitizeTextBlock() " + sanitizedText);
        
        return sanitizedText;
    }
}
