package org.authorsite.utils.bib.loader.ris;

import org.apache.log4j.Logger;


public class UnsupportedEntryHandler implements RISEntryHandler {

    private static final Logger LOGGER = Logger.getLogger(UnsupportedEntryHandler.class);
    
    public void handleEntry(RISEntry entry) {
        LOGGER.error("Could not handle " + entry);
    }

}
