/**
 * This file is part of the authorsite application.
 *
 * The authorsite application is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * The authorsite application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the authorsite application; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 */
package org.authorsite.utils.bib.loader.ris;

public class RISEntryLine {

    private static final String keyPattern = "^[A-Z0-9]{2}";
    
    private String key;
    private String value;
    
    public RISEntryLine(String str) throws RISException {
        if ( str == null ) {
            throw new RISException("String str is null");
        }
        if ( str.length() < 5 ) {
            throw new RISException("String str has length " + str.length() + ", which is too short for a tagged line");
        }
        key = str.substring(0, 2);
        if ( str.length() == 5 ) {
            value = "";
        }
        else {
            value = str.substring(6);
        }
    }
    
    
    public RISEntryLine(String key, String value) throws RISException {
        if ( key == null ) {
            throw new RISException("String key is null");
        }
        if ( value == null ) {
            throw new RISException("String value is null");
        }
        if ( !key.matches(RISEntryLine.keyPattern)) {
            throw new RISException("Key " + key + " does not match required pattern");
        }
        this.key = key;
        this.value = value; 
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void appendToValue(String additionalValue) {
        if ( additionalValue == null ) {
            throw new IllegalArgumentException("String additionalValue is null");
        }
        this.value = this.value + " " + additionalValue.trim();
    }
    
    @Override
    public String toString() {
        return this.key + "  - " + this.value;
    }
    
    
    
}
