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

import java.util.Set;

import junit.framework.TestCase;

import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.Article;
import org.authorsite.domain.bib.Journal;
import org.authorsite.domain.bib.WorkDates;

public class ArticleHandlerTest extends TestCase {

    public void testSingleArticle() throws Exception {
        RISEntry entry = new RISEntry();
        entry.addEntryLine(new RISEntryLine("TY", "JOUR"));
        entry.addEntryLine(new RISEntryLine("A1", "King, John"));
        entry.addEntryLine(new RISEntryLine("A1", "Bar, Foo"));
        entry.addEntryLine(new RISEntryLine("T1", "Foo and Baa: The Sheep Connection"));
        entry.addEntryLine(new RISEntryLine("JO", "Journal of Foo Studies"));
        entry.addEntryLine(new RISEntryLine("Y1", "1999"));
        entry.addEntryLine(new RISEntryLine("VL", "V"));
        entry.addEntryLine(new RISEntryLine("IS", "2"));
        entry.addEntryLine(new RISEntryLine("SP", "192-223"));
        
        Journal j = new Journal();
        j.setTitle("Journal of Foo Studies");
        
        Individual jk = new Individual();
        jk.setName("King");
        jk.setGivenNames("John");
        
        Individual fb = new Individual();
        fb.setName("Bar");
        fb.setGivenNames("Foo");
        
        Article article = new Article();
        article.setTitle("Foo and Baa: The Sheep Connection");
        article.setWorkDates(new WorkDates(1999));
        article.setJournal(j);
        article.setVolume("V");
        article.setIssue("2");
        article.setPages("192-223");
        article.addAuthor(jk);
        article.addAuthor(fb);
        
        ArticleHandler handler = new ArticleHandler();
        Article assembledArticle = (Article) handler.buildWorkFromEntry(entry);
        
        assertEquals(article, assembledArticle);
        
    }
    
    public void testSingleArticleWithEP() throws Exception {
        RISEntry entry = new RISEntry();
        entry.addEntryLine(new RISEntryLine("TY", "JOUR"));
        entry.addEntryLine(new RISEntryLine("A1", "King, John"));
        entry.addEntryLine(new RISEntryLine("A1", "Bar, Foo"));
        entry.addEntryLine(new RISEntryLine("T1", "Foo and Baa: The Sheep Connection"));
        entry.addEntryLine(new RISEntryLine("JO", "Journal of Foo Studies"));
        entry.addEntryLine(new RISEntryLine("Y1", "1999"));
        entry.addEntryLine(new RISEntryLine("VL", "V"));
        entry.addEntryLine(new RISEntryLine("IS", "2"));
        entry.addEntryLine(new RISEntryLine("SP", "192"));
        entry.addEntryLine(new RISEntryLine("EP", "223"));
        
        Journal j = new Journal();
        j.setTitle("Journal of Foo Studies");
        
        Individual jk = new Individual();
        jk.setName("King");
        jk.setGivenNames("John");
        
        Individual fb = new Individual();
        fb.setName("Bar");
        fb.setGivenNames("Foo");
        
        Article a = new Article();
        a.setTitle("Foo and Baa: The Sheep Connection");
        a.setWorkDates(new WorkDates(1999));
        a.setJournal(j);
        a.setVolume("V");
        a.setIssue("2");
        a.setPages("192-223");
        a.addAuthor(jk);
        a.addAuthor(fb);
        
        ArticleHandler handler = new ArticleHandler();
        Article assembledArticle = (Article) handler.buildWorkFromEntry(entry);
        assertEquals( a, assembledArticle);
    }
    
    public void testMultipleArticlesSameJournalSomeSameAuthors() throws Exception {
        
        RISEntry entry = new RISEntry();
        entry.addEntryLine(new RISEntryLine("TY", "JOUR"));
        entry.addEntryLine(new RISEntryLine("A1", "King, John"));
        entry.addEntryLine(new RISEntryLine("A1", "Bar, Foo"));
        entry.addEntryLine(new RISEntryLine("T1", "Foo and Baa: The Sheep Connection"));
        entry.addEntryLine(new RISEntryLine("JO", "Journal of Foo Studies"));
        entry.addEntryLine(new RISEntryLine("Y1", "1999"));
        entry.addEntryLine(new RISEntryLine("VL", "V"));
        entry.addEntryLine(new RISEntryLine("IS", "2"));
        entry.addEntryLine(new RISEntryLine("SP", "192-223"));
        
        RISEntry entry2 = new RISEntry();
        entry2.addEntryLine(new RISEntryLine("TY", "JOUR"));
        entry2.addEntryLine(new RISEntryLine("A1", "Bar, Foo"));
        entry2.addEntryLine(new RISEntryLine("A1", "Wibble, Wobble"));
        entry2.addEntryLine(new RISEntryLine("T1", "Wibbling and Wobbling"));
        entry2.addEntryLine(new RISEntryLine("JO", "Journal of Foo Studies"));
        entry2.addEntryLine(new RISEntryLine("Y1", "2000"));
        entry2.addEntryLine(new RISEntryLine("VL", "VII"));
        entry2.addEntryLine(new RISEntryLine("IS", "3"));
        entry2.addEntryLine(new RISEntryLine("SP", "334-340"));
        
        Journal j = new Journal();
        j.setTitle("Journal of Foo Studies");
        
        Individual jk = new Individual();
        jk.setName("King");
        jk.setGivenNames("John");
        
        Individual fb = new Individual();
        fb.setName("Bar");
        fb.setGivenNames("Foo");
        
        Individual ww = new Individual();
        ww.setName("Wibble");
        ww.setGivenNames("Wobble");
        
        Article a1 = new Article();
        a1.setTitle("Foo and Baa: The Sheep Connection");
        a1.setWorkDates(new WorkDates(1999));
        a1.setJournal(j);
        a1.setVolume("V");
        a1.setIssue("2");
        a1.setPages("192-223");
        a1.addAuthor(jk);
        a1.addAuthor(fb);
        
        Article a2 = new Article();
        a2.setTitle("Wibbling and Wobbling");
        a2.setWorkDates(new WorkDates(2000));
        a2.setJournal(j);
        a2.setVolume("VII");
        a2.setIssue("3");
        a2.setPages("334-340");
        a2.addAuthor(fb);
        a2.addAuthor(ww);
        
        ArticleHandler handler = new ArticleHandler();
        Article assembled1 = (Article) handler.buildWorkFromEntry(entry);
        Article assembled2 = (Article) handler.buildWorkFromEntry(entry2);
        
        assertEquals(a1, assembled1);
        assertEquals(a2, assembled2);
    }
    
}
