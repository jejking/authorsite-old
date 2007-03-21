/*
 * LinkTagTest.java
 * JUnit based test
 *
 * Created on 21 March 2007, 21:04
 */

package org.authorsite.web.tags;

import junit.framework.*;
import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.Article;
import org.authorsite.domain.bib.Book;
import org.authorsite.domain.bib.Chapter;
import org.authorsite.domain.bib.Journal;
import org.authorsite.domain.email.EmailFolder;
import org.authorsite.domain.email.EmailMessage;
import org.authorsite.security.SystemUser;

/**
 *
 * @author jejking
 */
public class LinkTagTest extends TestCase {
    
    LinkTag tag = new LinkTag();
    
    public LinkTagTest(String testName) {
        super(testName);
    }

    public void testCollective() {
        Collective c = new Collective();
        c.setName("Foo Bar Inc");
        c.setId(1234);
        
        String url = tag.getUrlForObject(c);
        assertEquals("/people/collectives/1234", url);
        String text = tag.getLinkTextForObject(c);
        assertEquals("Foo Bar Inc", text);
        
        Collective c2 = new Collective();
        c2.setId(5678);
        c2.setName("University of Sheepsville");
        c2.setPlace("The Field");
        
        assertEquals("University of Sheepsville, The Field", tag.getLinkTextForObject(c2));
        
    }
    
    public void testIndividual() {
        Individual i = new Individual();
        i.setId(1234);
        i.setName("Wurst");
        
        assertEquals("/people/individuals/1234", tag.getUrlForObject(i));
        assertEquals("Wurst", tag.getLinkTextForObject(i));
        
        i.setGivenNames("Hans");
        assertEquals("Wurst, Hans", tag.getLinkTextForObject(i));
        
    }
    
    public void testArticle() {
        Article a = new Article();
        a.setId(1234);
        a.setTitle("Understanding Unit Testing at Great Length");
        
        assertEquals("/works/articles/1234", tag.getUrlForObject(a));
        assertEquals(a.getTitle(), tag.getLinkTextForObject(a));
    }
    
    public void testBook() {
        Book b = new Book();
        b.setId(678);
        b.setTitle("A Book about Sausages");
        
        assertEquals("/works/books/678", tag.getUrlForObject(b));
        assertEquals(b.getTitle(), tag.getLinkTextForObject(b));
    }
            
    public void testChapter() {
        Chapter ch = new Chapter();
        ch.setId(987);
        ch.setTitle("Chapter One, A Walk in the Park");
        
        assertEquals("/works/chapters/987", tag.getUrlForObject(ch));
        assertEquals(ch.getTitle(), tag.getLinkTextForObject(ch));
    }
    
    public void testJournal() {
        Journal j = new Journal();
        j.setId(2345);
        j.setTitle("Sausage Studies");
        
        assertEquals("/works/journals/2345", tag.getUrlForObject(j));
        assertEquals(j.getTitle(), tag.getLinkTextForObject(j));
    }
    
    public void testEmailFolder() {
        EmailFolder priv = new EmailFolder();
        priv.setId(123);
        priv.setName("private");
        
        EmailFolder jejking = new EmailFolder();
        jejking.setName("jejking");
        priv.setParent(jejking);
        jejking.setParent(EmailFolder.ROOT);
        
        
        
        assertEquals("/mail/folders/jejking/private", tag.getUrlForObject(priv));
        assertEquals("/jejking/private", tag.getLinkTextForObject(priv));
    }
    
    public void testEmailMessage() {
        
        EmailMessage em = new EmailMessage();
        em.setId(98765);
        em.setMsgId("blah-wibble-postfix-123456-000x");
        assertEquals("/mail/messages/98765", tag.getUrlForObject(em));
        assertEquals(em.getMsgId(), tag.getLinkTextForObject(em));
    }

    public void testSystemUser() {
        SystemUser user = new SystemUser();
        user.setId(98);
        user.setUserName("hanswurst");
        assertEquals("/admin/users/98", tag.getUrlForObject(user));
        assertEquals("hanswurst", tag.getLinkTextForObject(user));
    }
}
