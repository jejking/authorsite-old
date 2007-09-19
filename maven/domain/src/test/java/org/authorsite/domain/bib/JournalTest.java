package org.authorsite.domain.bib;

import java.util.HashSet;
import java.util.Set;

import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;

import junit.framework.TestCase;

public class JournalTest extends TestCase {

    
    
    public void testEqualsHashCodeCompare() {
        Journal j1 = new Journal();
        j1.setTitle("Journal of Java Studies");
        
        Journal j2 = new Journal();
        j2.setTitle("Journal of Java Studies");
        
        assertEquals(j1, j2);
        assertEquals(j2, j1);
        assertEquals(j1.hashCode(), j2.hashCode());
        assertEquals(0, j1.compareTo(j2));
        assertEquals(0, j2.compareTo(j1));
        
        Journal j3 = new Journal();
        j3.setTitle("Java Studies");
        
        assertFalse(j1.equals(j3));
        assertFalse(j3.equals(j1));
        assertFalse(j1.hashCode() == j3.hashCode());
        assertTrue(j1.compareTo(j3) > 0);
        assertTrue(j3.compareTo(j1) < 0);
    }
    
    public void testSetPublisher() {
	Journal j = new Journal("Journal of Wibble");
	Individual i = new Individual("bar", "foo");
	j.setPublisher(i);
	
	assertTrue(j.getWorkProducers().contains( new WorkProducer(WorkProducerType.PUBLISHER, i)));
	assertEquals(1, j.getWorkProducers().size());
	assertTrue(j.getWorkProducersOfType(WorkProducerType.PUBLISHER).contains(i));
	assertEquals(i, j.getPublisher());
    }
    
    public void testSetPublisherTwice() {
	Journal j = new Journal("Journal of Wibble");
	Individual i = new Individual("bar", "foo");
	j.setPublisher(i);
	
	assertTrue(j.getWorkProducers().contains( new WorkProducer(WorkProducerType.PUBLISHER, i)));
	assertEquals(1, j.getWorkProducers().size());
	assertTrue(j.getWorkProducersOfType(WorkProducerType.PUBLISHER).contains(i));
	assertEquals(i, j.getPublisher());
	assertTrue(j.areProducersOk());
	
	Collective c = new Collective("Foo, Inc");
	j.setPublisher(c);
	
	assertTrue(j.getWorkProducers().contains( new WorkProducer(WorkProducerType.PUBLISHER, c)));
	assertEquals(1, j.getWorkProducers().size());
	assertTrue(j.getWorkProducersOfType(WorkProducerType.PUBLISHER).contains(c));
	assertEquals(c, j.getPublisher());
	assertTrue(j.areProducersOk());
    }
    
    public void testFullConstructor() {
	Individual i = new Individual("bar", "foo");
	Journal j = new Journal("Journal of Wibble", i);
	
	assertTrue(j.getWorkProducers().contains( new WorkProducer(WorkProducerType.PUBLISHER, i)));
	assertEquals(1, j.getWorkProducers().size());
	assertTrue(j.getWorkProducersOfType(WorkProducerType.PUBLISHER).contains(i));
	assertEquals(i, j.getPublisher());
    }
    
    public void testProducersOkOk() {
	Individual i = new Individual("bar", "foo");
	Journal j = new Journal("Journal of Wibble", i);
	
	assertTrue(j.areProducersOk());
    }
    
    public void testProducersOkNoPublisher() {
	Journal j = new Journal("Journal of Wibble");
	assertTrue(j.areProducersOk());
    }
    
    public void testProducersOkTwoPublishers() {
	Individual i = new Individual("bar", "foo");
	Collective c = new Collective("Foo, Inc");
	
	WorkProducer iPubl = new WorkProducer(WorkProducerType.PUBLISHER, i);
	WorkProducer cPubl = new WorkProducer(WorkProducerType.PUBLISHER, c);
	Set<WorkProducer> workProducers = new HashSet<WorkProducer>();
	workProducers.add(iPubl);
	workProducers.add(cPubl);
	
	Journal j = new Journal("Journal of Foo");
	j.setWorkProducers(workProducers);
	
	assertFalse(j.areProducersOk());
    }
    
    public void testProducersOkOnePublisherOneAuthor() {
	Individual i = new Individual("bar", "foo");
	Collective c = new Collective("Foo, Inc");
	
	WorkProducer iPubl = new WorkProducer(WorkProducerType.AUTHOR, i);
	WorkProducer cPubl = new WorkProducer(WorkProducerType.PUBLISHER, c);
	Set<WorkProducer> workProducers = new HashSet<WorkProducer>();
	workProducers.add(iPubl);
	workProducers.add(cPubl);
	
	Journal j = new Journal("Journal of Foo");
	j.setWorkProducers(workProducers);
	assertFalse(j.areProducersOk());
    }
    
    public void testGetPublisher() {
	Journal j = new Journal("Journal of Foo");
	
	Collective c = new Collective("Foo, Inc");
	
	WorkProducer cPubl = new WorkProducer(WorkProducerType.PUBLISHER, c);
	Set<WorkProducer> workProducers = new HashSet<WorkProducer>();
	workProducers.add(cPubl);
	
	j.setWorkProducers(workProducers);
	
	assertEquals(c, j.getPublisher());
    }
}
