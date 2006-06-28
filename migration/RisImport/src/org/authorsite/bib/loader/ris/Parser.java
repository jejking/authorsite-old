package org.authorsite.bib.loader.ris;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Parser {

    private static final String tagStartPattern = "^[A-Z0-9]{2}  - ";
    private ArrayList<RISEntry> entries = new ArrayList<RISEntry>();
    
    private RISEntry currentEntry;
    
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
    
    
    public void readFile(String fileName) throws IOException {
        File f = new File(fileName);
        if ( f.exists() ) {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF8"));
            String str;
            while ((str = in.readLine()) != null) {
                processLine(str);
            }
            in.close();
        }
        else {
            System.err.println("No such file as " + fileName);
        }
        
    }
    
    private void processLine(String str) {
        String trimmed = str.trim();
        if ( trimmed.length() == 0 ) {
            return;
        }
        
    }

    public void exportSqlToFile(String fileName) throws IOException {
        throw new UnsupportedOperationException("not built yet");
    }
    
    
        
}
