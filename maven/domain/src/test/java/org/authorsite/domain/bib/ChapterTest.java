package org.authorsite.domain.bib;

import java.util.HashSet;
import java.util.Set;

import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;

import junit.framework.TestCase;


public class ChapterTest extends TestCase {

    
    public void testEqualsHashCodeCompareTo() {
        Individual i1 = new Individual();
        i1.setName("King");
        i1.setGivenNames("John");
        
        Individual i2 = new Individual();
        i2.setName("Bar");
        i2.setGivenNames("Foo");
        
        Collective c = new Collective();
        c.setName("Foo Publishing Co");
        c.setPlace("here");
        
        Book b = new Book();
        b.setTitle("Musings on Stuff");
        b.setWorkDates(new WorkDates(2006));
        b.setPublisher(c);
        b.addEditor(i1);
        
        Chapter ch1 = new Chapter();
        ch1.setBook(b);
        ch1.addAuthor(i2);
        ch1.addAuthor(i1);
        ch1.setWorkDates(new WorkDates(2006));
        ch1.setTitle("Introduction to Stuff");
        
        Chapter ch2 = new Chapter();
        ch2.setBook(b);
        ch2.setWorkDates(new WorkDates(2006));
        ch2.addAuthor(i1);
        ch2.addAuthor(i2);
        
        ch2.setTitle("Introduction to Stuff");
        
        assertEquals(ch2, ch1);
        assertEquals(ch1, ch2);
        assertEquals(ch1.hashCode(), ch2.hashCode());
        assertEquals(0, ch1.compareTo(ch2));
        assertEquals(0, ch2.compareTo(ch1));
        
        Chapter ch3 = new Chapter();
        ch3.setBook(b);
        ch3.addAuthor(i1);
        ch3.setWorkDates( new WorkDates(2006));
        ch3.setTitle("Introduction to Stuff");
        
        assertFalse(ch1.equals(ch3));
        assertFalse(ch3.equals(ch1));
        assertFalse(ch1.hashCode() == ch3.hashCode());
        
    }
    
    public void testAreProducersOkAuthor() {
	Chapter ch = new Chapter();
	ch.addAuthor(new Individual("Foo", "Bar"));
	assertTrue(ch.areProducersOk());
    }
    
    public void testAreProducersOkEditor() {
	Chapter ch = new Chapter();
	ch.addEditor(new Individual("Foo", "Bar"));
	assertTrue(ch.areProducersOk());
    }
    
    public void testAreProducersOkAuthorEditor() {
	Chapter ch = new Chapter();
	ch.addAuthor(new Individual("Foo", "Bar"));
	ch.addEditor(new Individual("Wibble", "Wobble"));
	assertTrue(ch.areProducersOk());
    }
    
    public void testAreProducersOkTwoAuthors() {
	Chapter ch = new Chapter();
	ch.addAuthor(new Individual("Foo", "Bar"));
	ch.addAuthor(new Individual("Wibble", "Wobble"));
	assertTrue(ch.areProducersOk());
    }
    
    public void testAreProducersOkTwoEdtiors() {
	Chapter ch = new Chapter();
	ch.addEditor(new Individual("Foo", "Bar"));
	ch.addEditor(new Individual("Wibble", "Wobble"));
	assertTrue(ch.areProducersOk());
    }
    
    public void testAreProducersOkTwoAuthorsTwoEditors() {
	Chapter ch = new Chapter();
	ch.addEditor(new Individual("Foo", "Bar"));
	ch.addEditor(new Individual("Wibble", "Wobble"));
	ch.addAuthor(new Individual("Wurst", "Hans"));
	ch.addAuthor(new Individual("Sausage", "Peter"));
	assertTrue(ch.areProducersOk());
    }
    
    public void testAreProducersOkAuthorPublisher() {
	Individual i = new Individual("Foo", "Bar");
	Collective c = new Collective("A Publisher");
	
	WorkProducer author = new WorkProducer(WorkProducerType.AUTHOR, i);
	WorkProducer publisher = new WorkProducer(WorkProducerType.PUBLISHER, c);
	Set<WorkProducer> workProducers = new HashSet<WorkProducer>();
	workProducers.add(author);
	workProducers.add(publisher);
	
	Chapter ch = new Chapter();
	ch.setWorkProducers(workProducers);
	assertFalse(ch.areProducersOk());
    }
    
    public void testAreProducersOkAuthorAwardingBody() {
	Individual i = new Individual("Foo", "Bar");
	Collective c = new Collective("A Publisher");
	
	WorkProducer author = new WorkProducer(WorkProducerType.AUTHOR, i);
	WorkProducer awardingBody = new WorkProducer(WorkProducerType.AWARDING_BODY, c);
	Set<WorkProducer> workProducers = new HashSet<WorkProducer>();
	workProducers.add(author);
	workProducers.add(awardingBody);
	
	Chapter ch = new Chapter();
	ch.setWorkProducers(workProducers);
	assertFalse(ch.areProducersOk());
    }
    
}
