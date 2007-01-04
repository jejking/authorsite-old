package org.authorsite.utils.bib.loader.ris;

import java.util.HashMap;
import java.util.Map;


public class RISEntryHandlerFactory {

    private static final RISEntryHandlerFactory INSTANCE = new RISEntryHandlerFactory();
    
    private final Map<String, RISEntryHandler> handlers = new HashMap<String, RISEntryHandler>();
    
    private final RISEntryHandler unsupportedEntryHandler = new UnsupportedEntryHandler();
    
    private RISEntryHandlerFactory() {
        super();
        handlers.put("JOUR", new ArticleHandler());
        handlers.put("CHAP", new ChapterHandler());
        handlers.put("BOOK", new BookHandler());
        handlers.put("THES", new ThesisHandler());
    }
    
    public static RISEntryHandlerFactory getInstance() {
        return RISEntryHandlerFactory.INSTANCE;
    }
    
    public RISEntryHandler getHandler(RISEntry entry) {
        if ( entry == null ) {
            throw new IllegalArgumentException("Entry param is null");
        }
        String entryType = entry.getType();
        if ( entryType == null ) {
            System.err.println("Entry had no type information: " + entry);
        }
        RISEntryHandler handler = handlers.get(entry.getType());
        if ( handler != null ) {
            return handler;
        }
        else {
            return unsupportedEntryHandler;
        }
    }
    
}
