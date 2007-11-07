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
import org.authorsite.domain.bib.Chapter;
import org.authorsite.domain.bib.WorkDates;

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
        b.setWorkDates(new WorkDates(2004));
        b.setTitle("A Compendium of odd Creatures");
        
        Chapter c = new Chapter();
        c.addAuthor(fb);
        c.setTitle("Sheep, Wombles and Goats");
        c.setWorkDates(new WorkDates(2004));
        c.setPages("200-210");
        c.setBook(b);
        
        ChapterHandler handler = new ChapterHandler();
        Chapter assembled = (Chapter) handler.buildWorkFromEntry(entry);
        assertEquals( c, assembled);
        assertEquals(b, assembled.getBook());
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
        b.setWorkDates(new WorkDates(2004));
        b.setTitle("A Compendium of odd Creatures");
        
        Chapter c = new Chapter();
        c.addAuthor(fb);
        c.setTitle("Sheep, Wombles and Goats");
        c.setWorkDates(new WorkDates(2004));
        c.setPages("200-210");
        c.setBook(b);
        
        Chapter c2 = new Chapter();
        c2.addAuthor(jk);
        c2.setTitle(("An Introduction to Oddness"));
        c2.setWorkDates(new WorkDates(2004));
        c2.setPages("1-10");
        c2.setBook(b);
        
        ChapterHandler handler = new ChapterHandler();
        Chapter assembled1 = (Chapter) handler.buildWorkFromEntry(entry);
        Chapter assembled2 = (Chapter) handler.buildWorkFromEntry(entry2);
        
        assertEquals(c, assembled1);
        assertEquals(c2, assembled2);
    }
    
    public void testTwoChapterSameAuthorsSamePublisherDifferentBooks() throws Exception {
        RISEntry entry = new RISEntry();
        entry.addEntryLine(new RISEntryLine("TY", "CHAP"));
        entry.addEntryLine(new RISEntryLine("A1", "Bar, Foo"));
        entry.addEntryLine(new RISEntryLine("T1", "Sheep, Wombles and Goats"));
        entry.addEntryLine(new RISEntryLine("Y1", "2004"));
        entry.addEntryLine(new RISEntryLine("SP", "200-210"));
        entry.addEntryLine(new RISEntryLine("T2", "A Compendium of odd Creatures"));
        entry.addEntryLine(new RISEntryLine("A2", "King, John"));
        entry.addEntryLine(new RISEntryLine("PB", "Foo Publishing"));
        entry.addEntryLine(new RISEntryLine("CY", "Sheeptown"));
        
        RISEntry entry2 = new RISEntry();
        entry2.addEntryLine(new RISEntryLine("TY", "CHAP"));
        entry2.addEntryLine(new RISEntryLine("A1", "Bar, Foo"));
        entry2.addEntryLine(new RISEntryLine("T1", "An Introduction to Oddness"));
        entry2.addEntryLine(new RISEntryLine("SP", "1-10"));
        entry2.addEntryLine(new RISEntryLine("Y1", "2005"));
        entry2.addEntryLine(new RISEntryLine("T2", "More Odd Creatures"));
        entry2.addEntryLine(new RISEntryLine("A2", "King, John"));
        entry2.addEntryLine(new RISEntryLine("PB", "Foo Publishing"));
        entry2.addEntryLine(new RISEntryLine("CY", "Sheeptown"));
        
        
        Individual jk = new Individual();
        jk.setName("King");
        jk.setGivenNames("John");
        
        Individual fb = new Individual();
        fb.setName("Bar");
        fb.setGivenNames("Foo");
        
        Collective fp = new Collective();
        fp.setName("Foo Publishing");
        fp.setPlace("Sheeptown");
        
        Book b1 = new Book();
        b1.addAuthor(jk);
        b1.setPublisher(fp);
        b1.setWorkDates(new WorkDates(2004));
        b1.setTitle("A Compendium of odd Creatures");
        
        Book b2 = new Book();
        b2.addAuthor(jk);
        b2.setPublisher(fp);
        b2.setWorkDates(new WorkDates(2005));
        b2.setTitle("More Odd Creatures");
        
        Chapter c = new Chapter();
        c.addAuthor(fb);
        c.setTitle("Sheep, Wombles and Goats");
        c.setWorkDates(new WorkDates(2004));
        c.setPages("200-210");
        c.setBook(b1);
        
        Chapter c2 = new Chapter();
        c2.addAuthor(fb);
        c2.setTitle(("An Introduction to Oddness"));
        c2.setWorkDates(new WorkDates(2005));
        c2.setPages("1-10");
        c2.setBook(b2);
        
        ChapterHandler handler = new ChapterHandler();
        Chapter assembled1 = (Chapter) handler.buildWorkFromEntry(entry);
        Chapter assembled2 = (Chapter) handler.buildWorkFromEntry(entry2);
        
        assertEquals( c, assembled1);
        assertEquals( c2, assembled2);
    }
    
}
