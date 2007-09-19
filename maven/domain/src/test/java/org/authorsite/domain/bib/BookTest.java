package org.authorsite.domain.bib;

import java.util.HashSet;
import java.util.Set;

import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;

import junit.framework.TestCase;


public class BookTest extends TestCase {

    
    
    public void testEqualsHashCodeCompareAllSet() {
        Individual i1 = new Individual();
        i1.setId(1);
        i1.setName("King");
        i1.setGivenNames("John");
        
        Individual i2 = new Individual();
        i2.setId(2);
        i2.setName("Bar");
        i2.setGivenNames("Foo");
        
        Individual i3 = new Individual();
        i3.setId(3);
        i3.setName("Editor");
        i3.setGivenNames("A. N. Other");
        
        Collective c4 = new Collective();
        c4.setId(4);
        c4.setName("Java Publishing Inc");
       
        Book b1 = new Book();
        b1.setId(1);
        b1.addAuthor(i1);
        b1.addAuthor(i2);
        b1.addEditor(i3);
        b1.setPublisher(c4);
        b1.setTitle("Musings on Stuff");
        b1.setWorkDates( new WorkDates(2006));
        
        Book b2 = new Book();
        b2.setId(2);
        b2.addAuthor(i1);
        b2.addAuthor(i2);
        b2.addEditor(i3);
        b2.setPublisher(c4);
        b2.setTitle("Musings on Stuff");
        b2.setWorkDates( new WorkDates(2006));
        
        assertEquals(b1, b2);
        assertEquals(b2, b1);
        assertEquals(b1.hashCode(), b2.hashCode());
        assertEquals(0,b1.compareTo(b2) );
        assertEquals(0, b2.compareTo(b1));
        
        Book b3 = new Book();
        b3.setId(3);
        b3.addAuthor(i3);
        b3.setPublisher(c4);
        b3.setTitle("Musings on Stuff");
        b3.setWorkDates(new WorkDates(2006));
        
        assertFalse(b1.equals(b3));
        assertFalse(b3.equals(b1));
        assertFalse(b1.hashCode() == b3.hashCode());


    }
    
    public void testAreProducersOkAuthorPublisher() {
	Book b = new Book();
	Individual i = new Individual("Foo", "Bar");
	Collective c = new Collective("A Publisher");
	b.addAuthor(i);
	b.setPublisher(c);
	assertTrue(b.areProducersOk());
    }
    
    public void testAreProducersOkAuthorEditorPublisher() {
	Book b = new Book();
	Individual i = new Individual("Foo", "Bar");
	Individual i2 = new Individual("Wibble", "Wobble");
	Collective c = new Collective("A Publisher");
	b.addAuthor(i);
	b.addEditor(i2);
	b.setPublisher(c);
	assertTrue(b.areProducersOk());
    }
    
    public void testAreProducersOkTwoAuthorsTwoEditors() {
	Book b = new Book();
	Individual i = new Individual("Foo", "Bar");
	Individual i2 = new Individual("Wibble", "Wobble");
	Individual i3 = new Individual("Wurst", "Hans");
	Individual i4 = new Individual("Sausage", "Bob");
	Collective c = new Collective("A Publisher");
	b.addAuthor(i);
	b.addEditor(i2);
	b.addEditor(i3);
	b.addAuthor(i4);
	b.setPublisher(c);
	assertTrue(b.areProducersOk());
    }
    
    public void testAreProducersOkEditorsPublisher() {
	Book b = new Book();
	Individual i = new Individual("Foo", "Bar");
	Individual i2 = new Individual("Wibble", "Wobble");
	Collective c = new Collective("A Publisher");
	b.addEditor(i);
	b.addEditor(i2);
	b.setPublisher(c);
	assertTrue(b.areProducersOk());
    }
    
    public void testAreProducersOkAuthorOnly() {
	Book b = new Book();
	Individual i = new Individual("Foo", "Bar");
	Collective c = new Collective("A Publisher");
	b.addAuthor(i);
	assertTrue(b.areProducersOk());
    }
    
    public void testAreProducersOkEditorOnly() {
	Book b = new Book();
	Individual i2 = new Individual("Wibble", "Wobble");
	Collective c = new Collective("A Publisher");
	b.addEditor(i2);
	b.setPublisher(c);
	assertTrue(b.areProducersOk());
    }
    
    public void testAreProducersOkPublisherOnly() {
	Book b = new Book();
	Collective c = new Collective("A Publisher");
	b.setPublisher(c);
	assertTrue(b.areProducersOk());	
    }
    
    public void testAreProducersOkTwoPublishers() {
	Book b = new Book();
	Individual i2 = new Individual("Wibble", "Wobble");
	Collective c = new Collective("A Publisher");
	
	WorkProducer pub1 = new WorkProducer(WorkProducerType.PUBLISHER, i2);
	WorkProducer pub2 = new WorkProducer(WorkProducerType.PUBLISHER, c);
	Set<WorkProducer> workProducers = new HashSet<WorkProducer>();
	workProducers.add(pub1);
	workProducers.add(pub2);
	b.setWorkProducers(workProducers);
	assertFalse(b.areProducersOk());
	
    }
    
    public void testAreProducersOkAuthorAndAwardingBody() {
	Book b = new Book();
	Individual i2 = new Individual("Wibble", "Wobble");
	Collective c = new Collective("A Publisher");
	
	WorkProducer author = new WorkProducer(WorkProducerType.AUTHOR, i2);
	WorkProducer awardingBody = new WorkProducer(WorkProducerType.AWARDING_BODY, c);
	Set<WorkProducer> workProducers = new HashSet<WorkProducer>();
	workProducers.add(author);
	workProducers.add(awardingBody);
	b.setWorkProducers(workProducers);
	assertFalse(b.areProducersOk());
    }
    
}
