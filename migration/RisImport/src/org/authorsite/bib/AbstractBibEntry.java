package org.authorsite.bib;


public abstract class AbstractBibEntry {

    private long id;
    
    public AbstractBibEntry() {
        super();
    }
    
    public AbstractBibEntry(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public abstract String toSql();
    
    public String escapeSingleApostrophes(String stringToEscape) {
        if ( stringToEscape == null ) {
            return null;
        }
        
        return stringToEscape.replace("'", "\\'");
    }
    
    
}
