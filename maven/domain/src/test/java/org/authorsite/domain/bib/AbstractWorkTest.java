package org.authorsite.domain.bib;

import java.util.HashSet;
import java.util.Set;

import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;

import junit.framework.TestCase;

public class AbstractWorkTest extends TestCase {

    private class TestWork extends AbstractWork {

	@Override
	protected boolean areProducersOk() {
	    // TODO Auto-generated method stub
	    return false;
	}
	
    }
    
    public void testGetWorkProducersOfType() {
	
	TestWork testWork = new TestWork();
		
	Individual i = new Individual();
	i.setName("Bar");
	i.setGivenNames("Foo");
	
	Collective c = new Collective();
	c.setName("Fab, Inc");
	
	Individual i2 = new Individual();
	i2.setName("Sausage");
	i2.setGivenNames("Hans");
	
	testWork.addWorkProducer(WorkProducerType.AUTHOR, i);
	testWork.addWorkProducer(WorkProducerType.AUTHOR, c);
	testWork.addWorkProducer(WorkProducerType.EDITOR, i2);
	
	assertEquals(2,testWork.getWorkProducersOfType(WorkProducerType.AUTHOR).size());
	assertEquals(1, testWork.getWorkProducersOfType(WorkProducerType.EDITOR).size());
	
	Set<AbstractHuman> authors = testWork.getWorkProducersOfType(WorkProducerType.AUTHOR);
	assertTrue(authors.contains(i));
	assertTrue(authors.contains(c));
	
	Set<AbstractHuman> editors = testWork.getWorkProducersOfType(WorkProducerType.EDITOR);
	assertTrue(editors.contains(i2));
	
	Set<AbstractHuman> publishers = testWork.getWorkProducersOfType(WorkProducerType.PUBLISHER);
	assertTrue(publishers.isEmpty());
	
	assertEquals(3, testWork.getWorkProducers().size());
    }
    
    public void testRemoveWorkProducer() {
	TestWork testWork = new TestWork();
	
	
	Individual i = new Individual();
	i.setName("Bar");
	i.setGivenNames("Foo");
	
	Collective c = new Collective();
	c.setName("Fab, Inc");
	
	Individual i2 = new Individual();
	i2.setName("Sausage");
	i2.setGivenNames("Hans");
	
	testWork.addWorkProducer(WorkProducerType.AUTHOR, i);
	testWork.addWorkProducer(WorkProducerType.AUTHOR, c);
	testWork.addWorkProducer(WorkProducerType.EDITOR, i2);
	
	assertEquals(3, testWork.getWorkProducers().size());
	
	testWork.removeWorkProducer(WorkProducerType.AUTHOR, i);
	
	assertEquals(2, testWork.getWorkProducers().size());
	
	assertEquals(1, testWork.getWorkProducersOfType(WorkProducerType.AUTHOR).size());
	assertTrue(testWork.getWorkProducersOfType(WorkProducerType.AUTHOR).contains(c));
	
	assertEquals(1, testWork.getWorkProducersOfType(WorkProducerType.EDITOR).size());
	
	WorkProducer wp = new WorkProducer( WorkProducerType.EDITOR, i2);
	testWork.removeWorkProducer(wp);
	assertTrue(testWork.getWorkProducersOfType(WorkProducerType.EDITOR).isEmpty());
	
	assertEquals(1, testWork.getWorkProducers().size());
	
    }
    
    public void testAddWorkProducer() {
	TestWork testWork = new TestWork();
	
	Individual i = new Individual();
	i.setName("Bar");
	i.setGivenNames("Foo");
	
	Collective c = new Collective();
	c.setName("Fab, Inc");
	
	Individual i2 = new Individual();
	i2.setName("Sausage");
	i2.setGivenNames("Hans");
	
	testWork.addWorkProducer(new WorkProducer(WorkProducerType.AUTHOR, i));
	testWork.addWorkProducer(new WorkProducer(WorkProducerType.AUTHOR, c));
	testWork.addWorkProducer(new WorkProducer(WorkProducerType.EDITOR, i2));
	
	assertEquals(2,testWork.getWorkProducersOfType(WorkProducerType.AUTHOR).size());
	assertEquals(1, testWork.getWorkProducersOfType(WorkProducerType.EDITOR).size());
	
	Set<AbstractHuman> authors = testWork.getWorkProducersOfType(WorkProducerType.AUTHOR);
	assertTrue(authors.contains(i));
	assertTrue(authors.contains(c));
	
	Set<AbstractHuman> editors = testWork.getWorkProducersOfType(WorkProducerType.EDITOR);
	assertTrue(editors.contains(i2));
	
	Set<AbstractHuman> publishers = testWork.getWorkProducersOfType(WorkProducerType.PUBLISHER);
	assertTrue(publishers.isEmpty());
	
	assertEquals(3, testWork.getWorkProducers().size());
	
	assertTrue(testWork.getWorkProducers().contains(new WorkProducer(WorkProducerType.AUTHOR, i)));
	assertTrue(testWork.getWorkProducers().contains(new WorkProducer(WorkProducerType.AUTHOR, c)));
	assertTrue(testWork.getWorkProducers().contains(new WorkProducer(WorkProducerType.EDITOR, i2)));
    }
}
