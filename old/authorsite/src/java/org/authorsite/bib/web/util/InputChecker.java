/*
 * InputChecker.java
 *
 * Created on 24 January 2003, 12:13
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/util/InputChecker.java,v 1.6 2003/03/29 13:11:54 jejking Exp $
 *
 * Copyright (C) 2003  John King
 *
 * This file is part of the authorsite.org bibliographic
 * application.
 *
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.authorsite.bib.web.util;
import java.util.regex.*;
/**
 * Provides methods to perform extra checks on data submitted in web forms.
 * @author  jejking
 * @version $Revision: 1.6 $
 */
public class InputChecker {
    
    private static Pattern namePattern;
        
    static {
        System.out.println("called static init block");
        // - original - namePattern = Pattern.compile("'\\B|\\B'|-\\B|\\B-|(<[^>]*>)|[^-' a-zA-Z]");
        namePattern = Pattern.compile("'\\B|\\B'|-\\B|\\B-|(<[^>]*>)|[^-' \\p{L}]");
    }
    
    /** Creates a new instance of SqlInjectionGuard */
    public InputChecker() {
    }
    
    /**
     * <p>Cleans up an input string by substituting a null string for any characters which are not:</p>
     * <ul>
     * <li>an alphabetic character</li>
     * <li>a single quote/apostrophe with a "word" boundary both before and after it, as in can't or O'Brian</li>
     * <li>a single hyphen/dash with a "word" boundary both before and after it, as in Double-Barrelled</li>
     * </ul>
     */
    public static String cleanName(String inputString) {
        Matcher nameMatcher = namePattern.matcher(inputString);
        String cleanedName = nameMatcher.replaceAll("");
        System.out.println("cleaned Name: " + inputString + " to: " + cleanedName);
        return inputString;
    }
    
    public static String cleanComment(String commentField) {
            return commentField;
    }
    
    public static String cleanTitle(String inputTitle) {
        return inputTitle;
    }
    
    private boolean isFeasibleEmailAddress(String inputEmailAddress) {
        return true;
    }
    
    
    /**
     * Ensures a string submitted as an email is ok...
     */ 
    public static String cleanEmail(String inputString) {
        return inputString;
    }
    
    public static boolean isInteger(String stringToCheck) {
        try {
            int i = Integer.parseInt(stringToCheck);
            return true;
        }
        catch (NumberFormatException nfe) {
            return false;
        }
    }
    
    public static boolean isFloat(String stringToCheck) {
        try {
            float f = Float.parseFloat(stringToCheck);
            return true;
        }
        catch (NumberFormatException nfe) {
            return false;
        }
    }
}
