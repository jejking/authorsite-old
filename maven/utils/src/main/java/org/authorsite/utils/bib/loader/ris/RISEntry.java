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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class RISEntry {

    private RISEntryLine mostRecentEntry = null;
    
    private HashMap<String, ArrayList<RISEntryLine>> entries;
    
    public RISEntry() {
        entries = new HashMap<String, ArrayList<RISEntryLine>>();
    }
    
    public void addEntryLine(RISEntryLine line) throws RISException {
        if ( line == null ) {
            throw new RISException("line param is null");
        }
        
        if ( line.getValue() == null || line.getValue().length() == 0 )
        {
            return;
        }
        
        ArrayList entryList = entries.get( line.getKey() );
        if ( entryList == null )
        {
            entryList = new ArrayList<String>(3);
            entries.put( line.getKey(), entryList );
        }
        entryList.add( line );
        this.mostRecentEntry = line;
    }

    @SuppressWarnings("unchecked")
    public List<String> getValues(String key) {
        if ( key == null )
        {
            throw new IllegalArgumentException("param key is null");
        }
        
        List<RISEntryLine> entryList = entries.get(key);
        if ( entryList == null )
        {
            return Collections.EMPTY_LIST;
        }
        else {
            List<String> strings = new ArrayList(entryList.size());
            for (RISEntryLine line : entryList) {
                strings.add(line.getValue());
            }
            return strings;
        }
        
    }
    
    public String getType() {
        List<RISEntryLine> entryList = entries.get("TY");
        if ( entryList == null ) {
            return null;
        }
        else {
            return entryList.get(0).getValue();
        }
    }
    
    public RISEntryLine getMostRecentEntry() {
        return this.mostRecentEntry;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        List<RISEntryLine> ty = this.entries.get("TY");
        sb.append( ty == null ? "UNKNOWN" : ty.get(0));
        Set<String> keySet = this.entries.keySet();
        for ( String key : keySet ) {
            if (key.equals("TY")) {
                continue;
            }
            List<RISEntryLine> values = this.entries.get( key );
            for ( RISEntryLine value : values ) {
                sb.append("\n");
                sb.append(value);
            }
            
        }
        sb.append("\n");
        sb.append("ER  -");
        return sb.toString();
    }
  
}
 