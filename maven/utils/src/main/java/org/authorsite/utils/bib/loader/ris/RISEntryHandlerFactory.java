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
