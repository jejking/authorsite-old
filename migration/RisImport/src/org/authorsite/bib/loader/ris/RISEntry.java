package org.authorsite.bib.loader.ris;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
    
    public RISEntryLine getMostRecentEntry() {
        return this.mostRecentEntry;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        List<RISEntryLine> ty = this.entries.get("TY");
        sb.append(ty.get(0));
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
 