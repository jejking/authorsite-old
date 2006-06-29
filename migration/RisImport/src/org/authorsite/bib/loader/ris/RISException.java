package org.authorsite.bib.loader.ris;

public class RISException extends Exception {

    public RISException(String string) {
        super(string);
    }
    
    public RISException(String string, RISException e) {
        super(string, e);
    }

}
