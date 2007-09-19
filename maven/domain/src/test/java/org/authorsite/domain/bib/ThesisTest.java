package org.authorsite.domain.bib;

import java.util.HashSet;
import java.util.Set;

import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;

import junit.framework.TestCase;


public class ThesisTest extends TestCase {

    
    public void testEqualsHashCodeComparable() {
        Individual i = new Individual();
        i.setName("King");
        i.setGivenNames("John");
        
        Collective ou = new Collective();
        ou.setName("Oxford University");
        ou.setPlace("Oxford");
        
        Thesis t1 = new Thesis();
        t1.setTitle("Writing and Rewriting the First World War");
        t1.setAuthor(i);
        t1.setAwardingBody(ou);
        t1.setWorkDates(new WorkDates(1999));
        t1.setDegree("D. Phil");
        
        Thesis t2 = new Thesis();
        t2.setTitle("Writing and Rewriting the First World War");
        t2.setAuthor(i);
        t2.setAwardingBody(ou);
        t2.setWorkDates(new WorkDates(1999));
        t2.setDegree("D. Phil");
        
        assertEquals(t1, t2);
        assertEquals(t2, t1);
        
        assertEquals(t1.hashCode(), t2.hashCode());
        assertEquals(0, t1.compareTo(t2));
        assertEquals(0, t2.compareTo(t1));
        
        Thesis t3 = new Thesis();
        t3.setTitle("The one I really wanted to write");
        t3.setAuthor(i);
        t3.setAwardingBody(ou);
        t3.setWorkDates(new WorkDates(1999));
        t3.setDegree("D. Phil");
        
        assertFalse(t1.equals(t3));
        assertFalse(t3.equals(t1));
        
        assertTrue(t1.compareTo(t3) > 0);
        assertTrue(t3.compareTo(t1) < 0);
    }
    
    public void testSetAuthor() {
	Thesis t = new Thesis();
	Individual author = new Individual("Bar", "Foo");
	t.setAuthor(author);
	
	assertTrue(t.getWorkProducers().contains(
		new WorkProducer(WorkProducerType.AUTHOR, author)));
	assertTrue(t.getWorkProducersOfType(WorkProducerType.AUTHOR).contains(author));
	assertEquals(author, t.getAuthor());
    }
    
    public void testSetAuthorTwice() {
	Thesis t = new Thesis();
	Individual author = new Individual("Bar", "Foo");
	t.setAuthor(author);
	
	assertTrue(t.getWorkProducers().contains(
		new WorkProducer(WorkProducerType.AUTHOR, author)));
	assertTrue(t.getWorkProducersOfType(WorkProducerType.AUTHOR).contains(author));
	
	Individual author2 = new Individual("wibble", "wobble");
	t.setAuthor(author2);
	assertFalse(t.getWorkProducers().contains(
		new WorkProducer(WorkProducerType.AUTHOR, author)));
	assertFalse(t.getWorkProducersOfType(WorkProducerType.AUTHOR).contains(author));
	assertTrue(t.getWorkProducers().contains(
		new WorkProducer(WorkProducerType.AUTHOR, author2)));
	assertTrue(t.getWorkProducersOfType(WorkProducerType.AUTHOR).contains(author2));
	
	assertEquals(1, t.getWorkProducers().size());
	
	
    }
    
    public void testSetAwardingBody() {
	Collective ou = new Collective("Oxford University");
	Thesis t = new Thesis();
	t.setAwardingBody(ou);
	
	assertTrue(t.getWorkProducers().contains(
		new WorkProducer(WorkProducerType.AWARDING_BODY, ou)));
	assertTrue(t.getWorkProducersOfType(WorkProducerType.AWARDING_BODY)
		.contains(ou));
	assertEquals(ou, t.getAwardingBody());
    }
    
    public void testSetAwardingBodyTwice() {
	Collective ou = new Collective("Oxford University");
	Thesis t = new Thesis();
	t.setAwardingBody(ou);
	
	assertTrue(t.getWorkProducers().contains(
		new WorkProducer(WorkProducerType.AWARDING_BODY, ou)));
	assertTrue(t.getWorkProducersOfType(WorkProducerType.AWARDING_BODY)
		.contains(ou));
	
	Collective cu = new Collective("Cambridge University");
	t.setAwardingBody(cu);
	assertFalse(t.getWorkProducers().contains(
		new WorkProducer(WorkProducerType.AWARDING_BODY, ou)));
	assertFalse(t.getWorkProducersOfType(WorkProducerType.AWARDING_BODY)
		.contains(ou));
	
	assertTrue(t.getWorkProducers().contains(
		new WorkProducer(WorkProducerType.AWARDING_BODY, cu)));
	assertTrue(t.getWorkProducersOfType(WorkProducerType.AWARDING_BODY)
		.contains(cu));
	
	assertEquals(1, t.getWorkProducers().size());
	
	
    }
    
    public void testAreProducersOkOk() {
	Collective c = new Collective("A University");
	Individual i = new Individual("Foo", "Bar");
	Thesis t = new Thesis("A thesis", i, c );
	assertTrue(t.areProducersOk());
	assertEquals(2, t.getWorkProducers().size());
    }
    
    public void testGetAuthor() {
	Collective c = new Collective("A University");
	Individual i = new Individual("Foo", "Bar");
	
	Thesis t = new Thesis();
	WorkProducer awardingBody = new WorkProducer(WorkProducerType.AWARDING_BODY, c);
	WorkProducer author = new WorkProducer(WorkProducerType.AUTHOR, i);
	Set<WorkProducer> workProducerSet = new HashSet<WorkProducer>();
	workProducerSet.add(awardingBody);
	workProducerSet.add(author);
	
	t.setWorkProducers(workProducerSet);
	assertEquals(i, t.getAuthor());
    }
    
    public void testGetAwardingBody() {
	Collective c = new Collective("A University");
	Individual i = new Individual("Foo", "Bar");
	
	Thesis t = new Thesis();
	WorkProducer awardingBody = new WorkProducer(WorkProducerType.AWARDING_BODY, c);
	WorkProducer author = new WorkProducer(WorkProducerType.AUTHOR, i);
	Set<WorkProducer> workProducerSet = new HashSet<WorkProducer>();
	workProducerSet.add(awardingBody);
	workProducerSet.add(author);
	
	t.setWorkProducers(workProducerSet);
	assertEquals(c, t.getAwardingBody());
	
	assertTrue(t.areProducersOk());
    }
    
    public void testAreProducersOkCollectiveAuthorCollectiveAwardingBody() {
	Collective c = new Collective("A University");
	Collective c2 = new Collective("A Company");
	
	Thesis t = new Thesis();
	WorkProducer awardingBody = new WorkProducer(WorkProducerType.AWARDING_BODY, c);
	WorkProducer author = new WorkProducer(WorkProducerType.AUTHOR, c2);
	Set<WorkProducer> workProducerSet = new HashSet<WorkProducer>();
	workProducerSet.add(awardingBody);
	workProducerSet.add(author);
	
	t.setWorkProducers(workProducerSet);
	assertFalse(t.areProducersOk());
	
    }
    
    public void testAreProducersOkIndividualAuthorIndividualAwardingBody() {
	
	Individual i = new Individual("Foo", "Bar");
	Individual i2 = new Individual("Wibble", "Wobble");
	
	Thesis t = new Thesis();
	WorkProducer awardingBody = new WorkProducer(WorkProducerType.AWARDING_BODY, i2);
	WorkProducer author = new WorkProducer(WorkProducerType.AUTHOR, i);
	Set<WorkProducer> workProducerSet = new HashSet<WorkProducer>();
	workProducerSet.add(awardingBody);
	workProducerSet.add(author);
	
	t.setWorkProducers(workProducerSet);
	assertFalse(t.areProducersOk());
    }
    
    public void testAreProducersOkCollectiveAuthorInddividualAwardingBody() {
	Individual i = new Individual("Foo", "Bar");
	Collective c = new Collective("A Corp");
	
	Thesis t = new Thesis();
	WorkProducer awardingBody = new WorkProducer(WorkProducerType.AWARDING_BODY, i);
	WorkProducer author = new WorkProducer(WorkProducerType.AUTHOR, c);
	Set<WorkProducer> workProducerSet = new HashSet<WorkProducer>();
	workProducerSet.add(awardingBody);
	workProducerSet.add(author);
	
	t.setWorkProducers(workProducerSet);
	assertFalse(t.areProducersOk());
    }
    
    
    public void testAreProducersOkTwoAuthors() {
	Individual i = new Individual("Foo", "Bar");
	Individual i2 = new Individual("Wibble", "Wobble");
	Collective c = new Collective("A Corp");
	
	Thesis t = new Thesis();
	WorkProducer awardingBody = new WorkProducer(WorkProducerType.AWARDING_BODY, c);
	WorkProducer author = new WorkProducer(WorkProducerType.AUTHOR, i);
	WorkProducer author2 = new WorkProducer(WorkProducerType.AUTHOR, i2);
	Set<WorkProducer> workProducerSet = new HashSet<WorkProducer>();
	workProducerSet.add(awardingBody);
	workProducerSet.add(author);
	workProducerSet.add(author2);
	
	t.setWorkProducers(workProducerSet);
	assertFalse(t.areProducersOk());
    }
    
    public void testAreProducersOkNoAwardingBody() {
	Individual i = new Individual("Foo", "Bar");
	
	Thesis t = new Thesis();
	WorkProducer author = new WorkProducer(WorkProducerType.AUTHOR, i);
	Set<WorkProducer> workProducerSet = new HashSet<WorkProducer>();
	workProducerSet.add(author);
		
	t.setWorkProducers(workProducerSet);
	assertFalse(t.areProducersOk());
    }
    
    public void testAreProducersOkNone() {
	Thesis t = new Thesis();
	assertFalse(t.areProducersOk());
    }
    
    public void testAreProducersOkPublisher() {
	Collective c = new Collective("A University");
	Collective c2 = new Collective("A Publisher");
	Individual i = new Individual("Foo", "Bar");
	
	Thesis t = new Thesis();
	WorkProducer awardingBody = new WorkProducer(WorkProducerType.AWARDING_BODY, c);
	WorkProducer author = new WorkProducer(WorkProducerType.AUTHOR, i);
	WorkProducer publisher = new WorkProducer(WorkProducerType.PUBLISHER, c2);
	Set<WorkProducer> workProducerSet = new HashSet<WorkProducer>();
	workProducerSet.add(awardingBody);
	workProducerSet.add(author);
	workProducerSet.add(publisher);
	
	t.setWorkProducers(workProducerSet);
	assertFalse(t.areProducersOk());
	
    }
    
    
    
    
}
