package org.authorsite.domain.bib;

import java.util.ArrayList;
import java.util.List;

import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;

import junit.framework.TestCase;

public class AbstractAuthoredEditoredWorkTest extends TestCase {

    private class TestWork extends AbstractAuthoredEditedWork {

	@Override
	protected boolean areProducersOk() {
	    // TODO Auto-generated method stub
	    return false;
	}
	
    }
    
    public void testAddAuthor() {
	Individual i = new Individual();
	i.setName("Bar");
	i.setGivenNames("Foo");
	
	Collective c = new Collective();
	c.setName("Fab, Inc");
	
	TestWork testWork = new TestWork();
	testWork.addAuthor(i);
	testWork.addAuthor(c);
	
	assertEquals(2, testWork.getWorkProducers().size());
	assertEquals(2, testWork.getAuthors().size());
	
	assertEquals(2, testWork.getWorkProducersOfType(WorkProducerType.AUTHOR).size());
	assertTrue(testWork.getAuthors().contains(i));
	assertTrue(testWork.getAuthors().contains(c));
    }
    
    public void testAddAuthors() {
	Individual i = new Individual();
	i.setName("Bar");
	i.setGivenNames("Foo");
	
	Collective c = new Collective();
	c.setName("Fab, Inc");
	
	List<AbstractHuman> authors = new ArrayList<AbstractHuman>();
	authors.add(i);
	authors.add(c);
	
	TestWork testWork = new TestWork();
	testWork.addAuthors(authors);
	
	
	assertEquals(2, testWork.getWorkProducers().size());
	assertEquals(2, testWork.getAuthors().size());
	
	assertEquals(2, testWork.getWorkProducersOfType(WorkProducerType.AUTHOR).size());
	assertTrue(testWork.getAuthors().contains(i));
	assertTrue(testWork.getAuthors().contains(c));
    }
    
    public void testRemoveAuthor() {
	Individual i = new Individual();
	i.setName("Bar");
	i.setGivenNames("Foo");
	
	Collective c = new Collective();
	c.setName("Fab, Inc");
	
	List<AbstractHuman> authors = new ArrayList<AbstractHuman>();
	authors.add(i);
	authors.add(c);
	
	TestWork testWork = new TestWork();
	testWork.addAuthors(authors);
	
	assertEquals(2, testWork.getAuthors().size());
	
	testWork.removeAuthor(i);
	assertEquals(1, testWork.getAuthors().size());
	assertTrue(testWork.getAuthors().contains(c));
	
	testWork.removeAuthor(c);
	assertTrue(testWork.getAuthors().isEmpty());
    }
    
    public void testAddEditor() {
	Individual i = new Individual();
	i.setName("Bar");
	i.setGivenNames("Foo");
	
	Collective c = new Collective();
	c.setName("Fab, Inc");
	
	TestWork testWork = new TestWork();
	testWork.addEditor(i);
	testWork.addEditor(c);
	
	assertEquals(2, testWork.getWorkProducers().size());
	assertEquals(2, testWork.getEditors().size());
	
	assertEquals(2, testWork.getWorkProducersOfType(WorkProducerType.EDITOR).size());
	assertTrue(testWork.getEditors().contains(i));
	assertTrue(testWork.getEditors().contains(c));
    }
    
    public void testAddEditors() {
	Individual i = new Individual();
	i.setName("Bar");
	i.setGivenNames("Foo");
	
	Collective c = new Collective();
	c.setName("Fab, Inc");
	
	List<AbstractHuman> editors = new ArrayList<AbstractHuman>();
	editors.add(i);
	editors.add(c);
	
	TestWork testWork = new TestWork();
	testWork.addEditors(editors);
	
	
	assertEquals(2, testWork.getWorkProducers().size());
	assertEquals(2, testWork.getEditors().size());
	
	assertEquals(2, testWork.getWorkProducersOfType(WorkProducerType.EDITOR).size());
	assertTrue(testWork.getEditors().contains(i));
	assertTrue(testWork.getEditors().contains(c));
    }
    
    public void testRemoveEditor() {
	Individual i = new Individual();
	i.setName("Bar");
	i.setGivenNames("Foo");
	
	Collective c = new Collective();
	c.setName("Fab, Inc");
	
	List<AbstractHuman> editors = new ArrayList<AbstractHuman>();
	editors.add(i);
	editors.add(c);
	
	TestWork testWork = new TestWork();
	testWork.addEditors(editors);
	
	assertEquals(2, testWork.getWorkProducers().size());
	assertEquals(2, testWork.getEditors().size());
	
	testWork.removeEditor(i);
	assertEquals(1, testWork.getWorkProducers().size());
	assertEquals(1, testWork.getEditors().size());
	assertTrue(testWork.getEditors().contains(c));
	
	testWork.removeEditor(c);
	assertTrue(testWork.getWorkProducers().isEmpty());
	assertTrue(testWork.getEditors().isEmpty());
    }
    
}
