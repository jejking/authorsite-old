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

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.authorsite.domain.bib.AbstractWork;


public class Parser {

    private static final String tagStartPattern = "^[A-Z0-9]{2}  -";
    
    private final List<RISEntry> entries = new LinkedList<RISEntry>();
    
    private RISEntry currentEntry;
    
    public List<AbstractWork> parse( BufferedReader reader) throws IOException, RISException {
	String str;
        int lineNumber = 0;
        while ((str = reader.readLine()) != null) {
            lineNumber++;
            processLine(str, lineNumber);
        }
        reader.close();
        return convertRISEntries();
    }
    
    
    private List<AbstractWork> convertRISEntries() throws RISException {
	List<AbstractWork> works = new LinkedList<AbstractWork>();
	for (RISEntry entry : this.entries) {
	    RISEntryHandler handler = RISEntryHandlerFactory.getInstance().getHandler(entry);
	    AbstractWork work = handler.buildWorkFromEntry(entry);
	    if ( work != null ) {
		works.add(work);
	    }
	}
	return works;
    }


    boolean lineIsTagged(String line)
    {
        if ( line == null ) {
            return false;
        }
        if ( line.length() >= 5 )
        {
            String tagPart = line.substring(0, 5);
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
    
    
    private void processLine(String str, int lineNumber) {
        
        try {
            String trimmed = str.trim();
            // if line is blank, just ignore
            if ( trimmed.length() == 0 ) {
                return;
            }
            
            if ( this.lineIsTagged( str ))
            {
                RISEntryLine line = null;
                
                try {
                    line = new RISEntryLine(str);
                } catch (RISException e) {
                    throw new RISException("Exception at line " + lineNumber, e);
                }
                
                // end of record
                if ( line.getKey().equals("ER") )  {
                    if ( this.currentEntry != null ) {
                        this.entries.add( currentEntry );
                        this.currentEntry = null;
                    }
                    else {
                        throw new RISException("encountered end of record marker at line " + lineNumber + ", without one having been started"); 
                    }
                    return;
                }
                
                if ( line.getKey().equals("ID")) {
                   System.out.println("Reading ID  " + line.getValue());
                   return;
                }

                // start of record
                if ( line.getKey().equals("TY")) {

                    if ( this.currentEntry == null ) {
                        this.currentEntry = new RISEntry();
                        this.currentEntry.addEntryLine(line);
                    }
                    else {
                        throw new RISException("encountered start of record marker at line " + lineNumber + ", without the previous one having been finished");
                    }
                    return;
                }
                
                if ( this.currentEntry == null ) {
                    throw new RISException("at line " + lineNumber + " encountered RIS before a TY record start marker tag");
                }
                this.currentEntry.addEntryLine(line);
            }
            else {
               // probably value content continued from previous line...
               this.currentEntry.getMostRecentEntry().appendToValue(str);
            }
        }
        catch (RISException e) {
            System.err.println("RIS Exception on line " + lineNumber);
            e.printStackTrace();
        }
        
        
        
    }

}
