package org.authorsite.bib.loader.ris;

public class RISEntryLine {

    private String key;
    private String value;
    
    public RISEntryLine(String str) {
        if ( str == null ) {
            throw new IllegalArgumentException("String str is null");
        }
        if ( str.length() < 6 ) {
            throw new IllegalArgumentException("String str has length " + str.length() + ", which is too short for a tagged line");
        }
        key = str.substring(0, 2);
        value = str.substring(6);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
    
    
    
}
