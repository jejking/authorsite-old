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

import junit.framework.TestCase;

import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.Book;
import org.authorsite.domain.bib.WorkDates;

public class BookHandlerTest extends TestCase {

    public void testSimpleBookHandling() throws Exception {
        // book with two authors, one editor

        RISEntry entry = new RISEntry();
        entry.addEntryLine(new RISEntryLine("TY", "BOOK"));
        entry.addEntryLine(new RISEntryLine("A1", "King, John"));
        entry.addEntryLine(new RISEntryLine("A1", "Bar, Foo"));
        entry.addEntryLine(new RISEntryLine("E1", "Wibble, Wobble"));
        entry.addEntryLine(new RISEntryLine("T1", "Sheep and Wombles"));
        entry.addEntryLine(new RISEntryLine("PB", "Foo Publishing"));
        entry.addEntryLine(new RISEntryLine("CY", "Sheeptown"));
        entry.addEntryLine(new RISEntryLine("Y1", "2005"));
        
        Individual jk = new Individual();
        jk.setName("King");
        jk.setGivenNames("John");
        
        Individual fb = new Individual();
        fb.setName("Bar");
        fb.setGivenNames("Foo");
        
        Individual ww = new Individual();
        ww.setName("Wibble");
        ww.setGivenNames("Wobble");
        
        Collective fp = new Collective();
        fp.setName("Foo Publishing");
        fp.setPlace("Sheeptown");
        
        Book b = new Book();
        b.addAuthor(jk);
        b.addAuthor(fb);
        b.addEditor(ww);
        b.setPublisher(fp);
        b.setTitle("Sheep and Wombles");
        b.setWorkDates(new WorkDates(2005));
        
        BookHandler handler = new BookHandler();
        Book assembled = (Book) handler.buildWorkFromEntry(entry);
        
        assertEquals(b, assembled);
    }
    
    public void testTwoBooksSharedAuthorSharedPublisher() throws Exception {
        
        RISEntry entry1 = new RISEntry();
        entry1.addEntryLine(new RISEntryLine("TY", "BOOK"));
        entry1.addEntryLine(new RISEntryLine("A1", "Bar, Foo"));
        entry1.addEntryLine(new RISEntryLine("T1", "Sheep"));
        entry1.addEntryLine(new RISEntryLine("PB", "Foo Publishing"));
        entry1.addEntryLine(new RISEntryLine("CY", "Sheeptown"));
        entry1.addEntryLine(new RISEntryLine("Y1", "2004"));
        
        RISEntry entry2 = new RISEntry();
        entry2.addEntryLine(new RISEntryLine("TY", "BOOK"));
        entry2.addEntryLine(new RISEntryLine("A1", "Bar, Foo"));
        entry2.addEntryLine(new RISEntryLine("T1", "Goats"));
        entry2.addEntryLine(new RISEntryLine("PB", "Foo Publishing"));
        entry2.addEntryLine(new RISEntryLine("CY", "Sheeptown"));
        entry2.addEntryLine(new RISEntryLine("Y1", "2005"));
        
        Individual fb = new Individual();
        fb.setName("Bar");
        fb.setGivenNames("Foo");
        
        Collective fp = new Collective();
        fp.setName("Foo Publishing");
        fp.setPlace("Sheeptown");
       
        Book book1 = new Book();
        book1.addAuthor(fb);
        book1.setPublisher(fp);
        book1.setTitle("Sheep");
        book1.setWorkDates(new WorkDates(2004));
        
        Book book2 = new Book();
        book2.addAuthor(fb);
        book2.setPublisher(fp);
        book2.setTitle("Goats");
        book2.setWorkDates(new WorkDates(2005));
        
        BookHandler handler = new BookHandler();
        Book assembled1 = (Book) handler.buildWorkFromEntry(entry1);
        Book assembled2 = (Book) handler.buildWorkFromEntry(entry2);
        
    }
    
}
