package org.authorsite.utils.bib.loader.ris;

import org.authorsite.bib.Book;
import org.authorsite.bib.Collective;
import org.authorsite.bib.Individual;
import org.authorsite.bib.WorkDates;

import com.sun.org.apache.bcel.internal.generic.BIPUSH;

import junit.framework.TestCase;

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
        b.setYears(new WorkDates(2005));
        
        BookHandler handler = new BookHandler();
        handler.handleEntry(entry);
        
        assertTrue(Bibliography.getInstance().getIndividuals().containsKey(jk));
        assertTrue(Bibliography.getInstance().getIndividuals().containsKey(fb));
        assertTrue(Bibliography.getInstance().getIndividuals().containsKey(ww));
        assertTrue(Bibliography.getInstance().getCollectives().containsKey(fp));
        assertTrue(Bibliography.getInstance().getBooks().containsKey(b));
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
        book1.setYears(new WorkDates(2004));
        
        Book book2 = new Book();
        book2.addAuthor(fb);
        book2.setPublisher(fp);
        book2.setTitle("Goats");
        book2.setYears(new WorkDates(2005));
        
        BookHandler handler = new BookHandler();
        handler.handleEntry(entry1);
        handler.handleEntry(entry2);
        
        assertTrue(Bibliography.getInstance().getBooks().containsKey(book1));
        assertTrue(Bibliography.getInstance().getBooks().containsKey(book2));
        
        Book aBook1 = Bibliography.getInstance().getAuthoritativeBook(book1);
        Book aBook2 = Bibliography.getInstance().getAuthoritativeBook(book2);
        
        assertTrue(aBook1.getId() > 0);
        assertTrue(aBook1.getId() > 0);
        
        assertSame(aBook1.getAuthors().iterator().next(), aBook2.getAuthors().iterator().next());
        assertSame(aBook1.getPublisher(), aBook2.getPublisher());
        
    }
    
}
