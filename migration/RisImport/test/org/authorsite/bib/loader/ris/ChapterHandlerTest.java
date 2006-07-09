package org.authorsite.bib.loader.ris;

import org.authorsite.bib.Book;
import org.authorsite.bib.Chapter;
import org.authorsite.bib.Collective;
import org.authorsite.bib.Individual;

import junit.framework.TestCase;

public class ChapterHandlerTest extends TestCase {

    public void testSimple() throws Exception {
        RISEntry entry = new RISEntry();
        entry.addEntryLine(new RISEntryLine("TY", "CHAP"));
        entry.addEntryLine(new RISEntryLine("A1", "Bar, Foo"));
        entry.addEntryLine(new RISEntryLine("T1", "Sheep, Wombles and Goats"));
        entry.addEntryLine(new RISEntryLine("Y1", "2004"));
        entry.addEntryLine(new RISEntryLine("SP", "200-210"));
        entry.addEntryLine(new RISEntryLine("T2", "A Compendium of odd Creatures"));
        entry.addEntryLine(new RISEntryLine("A2", "King, John"));
        entry.addEntryLine(new RISEntryLine("A2", "Wobble, Wibble"));
        entry.addEntryLine(new RISEntryLine("PB", "Foo Publishing"));
        entry.addEntryLine(new RISEntryLine("CY", "Sheeptown"));
        
        Individual jk = new Individual();
        jk.setName("King");
        jk.setGivenNames("John");
        
        Individual fb = new Individual();
        fb.setName("Bar");
        fb.setGivenNames("Foo");
        
        Individual ww = new Individual();
        ww.setName("Wobble");
        ww.setGivenNames("Wibble");
        
        Collective fp = new Collective();
        fp.setName("Foo Publishing");
        fp.setPlace("Sheeptown");
        
        Book b = new Book();
        b.addAuthor(jk);
        b.addAuthor(ww);
        b.setPublisher(fp);
        b.setYear(2004);
        b.setTitle("A Compendium of odd Creatures");
        
        Chapter c = new Chapter();
        c.addAuthor(fb);
        c.setTitle("Sheep, Wombles and Goats");
        c.setYear(2004);
        c.setPages("200-210");
        c.setBook(b);
        
        ChapterHandler handler = new ChapterHandler();
        handler.handleEntry(entry);
        
        assertTrue(Bibliography.getInstance().getBooks().containsKey(b));
        assertTrue(Bibliography.getInstance().getChapters().containsKey(c));
        
        Chapter aC = Bibliography.getInstance().getAuthoritativeChapter(c);
        assertTrue(aC.getId() > 0);
        assertTrue(aC.getBook().getId() > 0);
        assertTrue(aC.getBook().equals(b));
    }
    
    public void testTwoChaptersFromSameBook() throws Exception {
        RISEntry entry = new RISEntry();
        entry.addEntryLine(new RISEntryLine("TY", "CHAP"));
        entry.addEntryLine(new RISEntryLine("A1", "Bar, Foo"));
        entry.addEntryLine(new RISEntryLine("T1", "Sheep, Wombles and Goats"));
        entry.addEntryLine(new RISEntryLine("Y1", "2004"));
        entry.addEntryLine(new RISEntryLine("SP", "200-210"));
        entry.addEntryLine(new RISEntryLine("T2", "A Compendium of odd Creatures"));
        entry.addEntryLine(new RISEntryLine("A2", "King, John"));
        entry.addEntryLine(new RISEntryLine("A2", "Wobble, Wibble"));
        entry.addEntryLine(new RISEntryLine("PB", "Foo Publishing"));
        entry.addEntryLine(new RISEntryLine("CY", "Sheeptown"));
        
        RISEntry entry2 = new RISEntry();
        entry2.addEntryLine(new RISEntryLine("TY", "CHAP"));
        entry2.addEntryLine(new RISEntryLine("A1", "King, John"));
        entry2.addEntryLine(new RISEntryLine("T1", "An Introduction to Oddness"));
        entry2.addEntryLine(new RISEntryLine("SP", "1-10"));
        entry2.addEntryLine(new RISEntryLine("Y1", "2004"));
        entry2.addEntryLine(new RISEntryLine("T2", "A Compendium of odd Creatures"));
        entry2.addEntryLine(new RISEntryLine("A2", "King, John"));
        entry2.addEntryLine(new RISEntryLine("A2", "Wobble, Wibble"));
        entry2.addEntryLine(new RISEntryLine("PB", "Foo Publishing"));
        entry2.addEntryLine(new RISEntryLine("CY", "Sheeptown"));
        
        
        Individual jk = new Individual();
        jk.setName("King");
        jk.setGivenNames("John");
        
        Individual fb = new Individual();
        fb.setName("Bar");
        fb.setGivenNames("Foo");
        
        Individual ww = new Individual();
        ww.setName("Wobble");
        ww.setGivenNames("Wibble");
        
        Collective fp = new Collective();
        fp.setName("Foo Publishing");
        fp.setPlace("Sheeptown");
        
        Book b = new Book();
        b.addAuthor(jk);
        b.addAuthor(ww);
        b.setPublisher(fp);
        b.setYear(2004);
        b.setTitle("A Compendium of odd Creatures");
        
        Chapter c = new Chapter();
        c.addAuthor(fb);
        c.setTitle("Sheep, Wombles and Goats");
        c.setYear(2004);
        c.setPages("200-210");
        c.setBook(b);
        
        Chapter c2 = new Chapter();
        c2.addAuthor(jk);
        c2.setTitle(("An Introduction to Oddness"));
        c2.setYear(2004);
        c2.setPages("1-10");
        c2.setBook(b);
        
        ChapterHandler handler = new ChapterHandler();
        handler.handleEntry(entry);
        handler.handleEntry(entry2);
        
        assertTrue(Bibliography.getInstance().getChapters().containsKey(c2));
        
        Chapter ac2 = Bibliography.getInstance().getAuthoritativeChapter(c2);
        Chapter ac1 = Bibliography.getInstance().getAuthoritativeChapter(c);
        
        assertSame(ac2.getBook(), ac1.getBook());
        assertSame(ac2.getAuthors().iterator().next(), ac1.getBook().getAuthors().iterator().next());
    }
    
    public void testTwoChapterSameAuthorsSamePublisherDifferentBooks() {
        fail("not built yet");
    }
    
}
