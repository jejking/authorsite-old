package org.authorsite.bib.loader.ris;


public class Parser {

    private static final String tagStartPattern = "^[A-Z0-9]{2}  - ";
    
    public boolean lineIsTagged(String line)
    {
        if ( line == null ) {
            return false;
        }
        if ( line.length() >= 6 )
        {
            String tagPart = line.substring(0, 6);
            if ( tagPart.matches(tagStartPattern)) {
                return true;
            }
            else {
                return false;
            }
        }
        else 
        {
            return false;
        }
    }
    
}
