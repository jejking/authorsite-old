/*
 * MemoryVocabSetQueryTest.java, created on 22-Oct-2003 at 20:03:43
 * 
 * Copyright John King, 2003.
 *
 *  MemoryVocabSetQueryTest.java is part of authorsite.org's VocabManager program.
 *
 *  VocabManager is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  VocabManager is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with VocabManager; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package org.authorsite.vocab.core.memory;
import java.util.*;
import java.io.*;
import junit.framework.*;
import junit.textui.*;
import org.authorsite.vocab.core.*;
import org.authorsite.vocab.VocabConstants;
import org.authorsite.vocab.exceptions.*;
import org.authorsite.vocab.core.dto.*;
/**
 * Test cases for the implementation of the <code>MemoryVocabSet</code> implementation
 * of <code>findNodes</code>.
 * 
 * @author jejking
 * @version $Revision: 1.4 $
 * @see org.authorsite.vocab.core.VocabSet#findNodes(NodeQueryBean)
 */
public class MemoryVocabSetQueryTest extends TestCase {

	private MemoryVocabSet vocabSet;

	protected void setUp() {
		try {
			Calendar earlyOctoberCal = new GregorianCalendar(2003, Calendar.OCTOBER, 5);
			Date earlyOctober = earlyOctoberCal.getTime();

			Calendar midOctoberCal = new GregorianCalendar(2003, Calendar.OCTOBER, 15);
			Date midOctober = midOctoberCal.getTime();

			Calendar lateOctoberCal = new GregorianCalendar(2003, Calendar.OCTOBER, 21);
			Date lateOctober = lateOctoberCal.getTime();

			HashSet hashSet = new HashSet();
			// 3 English Verbs
			VocabNodeDTO drinkVerb = new VocabNodeDTO();
			drinkVerb.setId(new Integer(1));
			drinkVerb.setText("drink");
			drinkVerb.setNodeType("verb");
			drinkVerb.setLocale(Locale.ENGLISH);
			drinkVerb.setDateCreated(earlyOctober);
			hashSet.add(drinkVerb);

			VocabNodeDTO runVerb = new VocabNodeDTO();
			runVerb.setId(new Integer(2));
			runVerb.setText("run");
			runVerb.setNodeType("verb");
			runVerb.setLocale(Locale.ENGLISH);
			runVerb.setDateCreated(midOctober);
			hashSet.add(runVerb);

			VocabNodeDTO brewVerb = new VocabNodeDTO();
			brewVerb.setId(new Integer(3));
			brewVerb.setText("brew");
			brewVerb.setNodeType("verb");
			brewVerb.setLocale(Locale.ENGLISH);
			brewVerb.setDateCreated(lateOctober);
			hashSet.add(brewVerb);

			// 3 English Nouns
			VocabNodeDTO drinkNoun = new VocabNodeDTO();
			drinkNoun.setId(new Integer(4));
			drinkNoun.setText("drink");
			drinkNoun.setNodeType("noun");
			drinkNoun.setLocale(Locale.ENGLISH);
			Calendar drinkNounCal = new GregorianCalendar(2003, Calendar.OCTOBER, 5, 1, 1);
			drinkNoun.setDateCreated(drinkNounCal.getTime());
			hashSet.add(drinkNoun);

			VocabNodeDTO zeitgeistNounEn = new VocabNodeDTO();
			zeitgeistNounEn.setId(new Integer(5));
			zeitgeistNounEn.setText("Zeitgeist");
			zeitgeistNounEn.setNodeType("noun");
			zeitgeistNounEn.setLocale(Locale.ENGLISH);
			Calendar zeitgeistNounEnCal = new GregorianCalendar(2003, Calendar.OCTOBER, 15, 1, 2);
			zeitgeistNounEn.setDateCreated(zeitgeistNounEnCal.getTime());
			hashSet.add(zeitgeistNounEn);

			VocabNodeDTO runNoun = new VocabNodeDTO();
			runNoun.setId(new Integer(6));
			runNoun.setText("run");
			runNoun.setNodeType("noun");
			runNoun.setLocale(Locale.ENGLISH);
			Calendar runNounCal = new GregorianCalendar(2003, Calendar.OCTOBER, 21, 3, 43);
			runNoun.setDateCreated(runNounCal.getTime());
			hashSet.add(runNoun);

			// 3 German Verbs
			VocabNodeDTO trinken = new VocabNodeDTO();
			trinken.setId(new Integer(7));
			trinken.setText("trinken");
			trinken.setNodeType("verb");
			trinken.setLocale(Locale.GERMAN);
			Calendar trinkenCal = new GregorianCalendar(2003, Calendar.OCTOBER, 5, 15, 32);
			trinken.setDateCreated(trinkenCal.getTime());
			hashSet.add(trinken);

			VocabNodeDTO laufen = new VocabNodeDTO();
			laufen.setId(new Integer(8));
			laufen.setText("laufen");
			laufen.setNodeType("verb");
			laufen.setLocale(Locale.GERMAN);
			Calendar laufenCal = new GregorianCalendar(2003, Calendar.OCTOBER, 15, 20, 54);
			laufen.setDateCreated(laufenCal.getTime());
			hashSet.add(laufen);

			VocabNodeDTO rechnen = new VocabNodeDTO();
			rechnen.setId(new Integer(9));
			rechnen.setText("computer");
			rechnen.setNodeType("verb");
			rechnen.setLocale(Locale.GERMAN);
			Calendar rechnenCal = new GregorianCalendar(2003, Calendar.OCTOBER, 21, 21, 7);
			rechnen.setDateCreated(rechnenCal.getTime());
			hashSet.add(rechnen);

			// 3 German Nouns

			VocabNodeDTO zeitgeistNounDe = new VocabNodeDTO();
			zeitgeistNounDe.setId(new Integer(10));
			zeitgeistNounDe.setText("Zeitgeist");
			zeitgeistNounDe.setNodeType("noun");
			zeitgeistNounDe.setLocale(Locale.GERMAN);
			Calendar zeitgeistNounDeCal = new GregorianCalendar(2003, Calendar.OCTOBER, 5, 17, 18);
			zeitgeistNounDe.setDateCreated(zeitgeistNounDeCal.getTime());
			hashSet.add(zeitgeistNounDe);

			VocabNodeDTO bier = new VocabNodeDTO();
			bier.setId(new Integer(11));
			bier.setText("Bier");
			bier.setNodeType("noun");
			bier.setLocale(Locale.GERMAN);
			Calendar bierCal = new GregorianCalendar(2003, Calendar.OCTOBER, 15, 12, 42);
			bier.setDateCreated(bierCal.getTime());
			hashSet.add(bier);

			VocabNodeDTO treffpunkt = new VocabNodeDTO();
			treffpunkt.setId(new Integer(12));
			treffpunkt.setText("Treffpunkt");
			treffpunkt.setNodeType("noun");
			treffpunkt.setLocale(Locale.GERMAN);
			Calendar treffpunktCal = new GregorianCalendar(2003, Calendar.OCTOBER, 21, 14, 2);
			treffpunkt.setDateCreated(treffpunktCal.getTime());
			hashSet.add(treffpunkt);

			File test = new File("test.dat");
			if (test.exists()) {
				test.delete();
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(test));
			out.writeObject(hashSet);
			out.flush();
			out.close();

			Properties props = new Properties();
			props.put(VocabConstants.MEMORY_NODESFILE_PROP, test.getAbsolutePath());

			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			factory.setProperties(props);
			vocabSet = (MemoryVocabSet) factory.getVocabSet();
		}
		catch (VocabException ve) {
			ve.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public MemoryVocabSetQueryTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(MemoryVocabSetQueryTest.class);
		return suite;
	}

	public void testFindText() {
		NodeQueryBean query = new NodeQueryBean();
		query.setText("Zeitgeist"); // should get the English and the German versions...
		Set foundNodes = vocabSet.findNodes(query);
		Assert.assertEquals(2, foundNodes.size());
		Iterator it = foundNodes.iterator();
		while (it.hasNext()) {
			VocabNode foundNode = (VocabNode) it.next();
			Assert.assertEquals("Zeitgeist", foundNode.getText());
		}
	}

	public void testFindNodeType() {
		NodeQueryBean query = new NodeQueryBean();
		query.setNodeType("verb");
		Set foundNodes = vocabSet.findNodes(query);
		Assert.assertEquals(6, foundNodes.size());
		Iterator it = foundNodes.iterator();
		while (it.hasNext()) {
			VocabNode foundNode = (VocabNode) it.next();
			Assert.assertEquals("verb", foundNode.getNodeType());
		}
	}

	public void testFindLocale() {
		NodeQueryBean query = new NodeQueryBean();
		query.setLocale(Locale.GERMAN);
		Set foundNodes = vocabSet.findNodes(query);
		Assert.assertEquals(6, foundNodes.size());
		Iterator it = foundNodes.iterator();
		while (it.hasNext()) {
			VocabNode foundNode = (VocabNode) it.next();
			Assert.assertEquals(Locale.GERMAN, foundNode.getLocale());
		}
	}

	public void testFindBefore() {
		Calendar october11Cal = new GregorianCalendar(2003, Calendar.OCTOBER, 11);
		Date october11 = october11Cal.getTime();
		NodeQueryBean query = new NodeQueryBean();
		query.setBeforeDate(october11);
		Set foundNodes = vocabSet.findNodes(query);
		Assert.assertEquals(4, foundNodes.size());
		Iterator it = foundNodes.iterator();
		while (it.hasNext()) {
			VocabNode foundNode = (VocabNode) it.next();
			Assert.assertTrue(foundNode.getDateCreated().before(october11));
		}
	}

	public void testFindAfter() {
		Calendar october11Cal = new GregorianCalendar(2003, Calendar.OCTOBER, 11);
		NodeQueryBean query = new NodeQueryBean();
		query.setAfterDate(october11Cal.getTime());
		Set foundNodes = vocabSet.findNodes(query);
		Assert.assertEquals(8, foundNodes.size());
		Iterator it = foundNodes.iterator();
		while (it.hasNext()) {
			VocabNode foundNode = (VocabNode) it.next();
			Assert.assertTrue(foundNode.getDateCreated().after(october11Cal.getTime()));
		}
	}

	public void testFindBetween() {
		Calendar october9Cal = new GregorianCalendar(2003, Calendar.OCTOBER, 9);
		Date october9 = october9Cal.getTime();
		Calendar october18Cal = new GregorianCalendar(2003, Calendar.OCTOBER, 18);
		Date october18 = october18Cal.getTime();

		NodeQueryBean query = new NodeQueryBean();
		query.setAfterDate(october9);
		query.setBeforeDate(october18);
		Set foundNodes = vocabSet.findNodes(query);
		Assert.assertEquals(4, foundNodes.size());
		Iterator it = foundNodes.iterator();
		while (it.hasNext()) {
			VocabNode foundNode = (VocabNode) it.next();
			Assert.assertTrue(foundNode.getDateCreated().after(october9) && foundNode.getDateCreated().before(october18));
		}
	}

	public void testTextAndNodeType() {
		NodeQueryBean query = new NodeQueryBean();
		query.setText("drink");
		query.setNodeType("noun");
		Set foundNodes = vocabSet.findNodes(query);
		Assert.assertEquals(1, foundNodes.size());
		Iterator it = foundNodes.iterator();
		while (it.hasNext()) {
			VocabNode foundNode = (VocabNode) it.next();
			Assert.assertEquals("drink", foundNode.getText());
			Assert.assertEquals("noun", foundNode.getNodeType());
		}
	}

	public void testTextAndLocale() {
		NodeQueryBean query = new NodeQueryBean();
		query.setText("Zeitgeist");
		query.setLocale(Locale.GERMAN);
		Set foundNodes = vocabSet.findNodes(query);
		Assert.assertEquals(1, foundNodes.size());
		Iterator it = foundNodes.iterator();
		while (it.hasNext()) {
			VocabNode foundNode = (VocabNode) it.next();
			Assert.assertEquals("Zeitgeist", foundNode.getText());
			Assert.assertEquals(Locale.GERMAN, foundNode.getLocale());
		}
	}

	public void testTextAndBefore() {
		NodeQueryBean query = new NodeQueryBean();
		query.setText("drink");
		query.setBeforeDate(new GregorianCalendar(2003, Calendar.OCTOBER, 6).getTime());
		Set foundNodes = vocabSet.findNodes(query);
		Assert.assertEquals(2, foundNodes.size());
		Iterator it = foundNodes.iterator();
		while (it.hasNext()) {
			VocabNode foundNode = (VocabNode) it.next();
			Assert.assertEquals("drink", foundNode.getText());
			Assert.assertTrue(new GregorianCalendar(2003, Calendar.OCTOBER, 6).getTime().after(foundNode.getDateCreated()));
		}
	}

	public void testTextAndAfter() {
		Calendar october20 = new GregorianCalendar(2003, Calendar.OCTOBER, 20);
		NodeQueryBean query = new NodeQueryBean();
		query.setText("Treffpunkt");
		query.setAfterDate(october20.getTime());
		Set foundNodes = vocabSet.findNodes(query);
		Assert.assertEquals(1, foundNodes.size());
		Iterator it = foundNodes.iterator();
		while (it.hasNext()) {
			VocabNode foundNode = (VocabNode) it.next();
			Assert.assertEquals("Treffpunkt", foundNode.getText());
			Assert.assertTrue(october20.getTime().before(foundNode.getDateCreated()));
		}
	}

	public void testTextAndBetween() {
		Calendar october10Cal = new GregorianCalendar(2003, Calendar.OCTOBER, 10);
		Date october10 = october10Cal.getTime();
		Calendar october20Cal = new GregorianCalendar(2003, Calendar.OCTOBER, 20);
		Date october20 = october20Cal.getTime();
		NodeQueryBean query = new NodeQueryBean();
		query.setBeforeDate(october20);
		query.setAfterDate(october10);
		query.setText("run");
		Set foundNodes = vocabSet.findNodes(query);
		Assert.assertEquals(1, foundNodes.size());
		Iterator it = foundNodes.iterator();
		while (it.hasNext()) {
			VocabNode foundNode = (VocabNode) it.next();
			Assert.assertEquals("run", foundNode.getText());
			Assert.assertTrue(october20.after(foundNode.getDateCreated()));
			Assert.assertTrue(october10.before(foundNode.getDateCreated()));
		}
	}

	public void testTextAndNodeTypeAndLocale() {
		NodeQueryBean query = new NodeQueryBean();
		query.setText("laufen");
		query.setNodeType("verb");
		query.setLocale(Locale.GERMAN);
		Set foundNodes = vocabSet.findNodes(query);
		Assert.assertEquals(1, foundNodes.size());
		Iterator it = foundNodes.iterator();
		while (it.hasNext()) {
			VocabNode foundNode = (VocabNode) it.next();
			Assert.assertEquals("laufen", foundNode.getText());
			Assert.assertEquals("verb", foundNode.getNodeType());
			Assert.assertEquals(Locale.GERMAN, foundNode.getLocale());
		}
	}

	public void testTextAndNodeTypeAndLocaleAndBetween() {
		/*
		 * 	zeitgeistNounDe.setText("Zeitgeist");
			zeitgeistNounDe.setNodeType("noun");
			zeitgeistNounDe.setLocale(Locale.GERMAN);
			Calendar zeitgeistNounDeCal = new GregorianCalendar(2003, Calendar.OCTOBER, 5, 17, 18);
			zeitgeistNounDe.setDateCreated(zeitgeistNounDeCal.getTime());
			hashSet.add(zeitgeistNounDe);
		 */
		Date october4 = new GregorianCalendar(2003, Calendar.OCTOBER, 4).getTime();
		Date october6 = new GregorianCalendar(2003, Calendar.OCTOBER, 6).getTime();
		NodeQueryBean query = new NodeQueryBean();
		query.setText("Zeitgeist");
		query.setNodeType("noun");
		query.setLocale(Locale.GERMAN);
		query.setAfterDate(october4);
		query.setBeforeDate(october6);
		Set foundNodes = vocabSet.findNodes(query);
		Assert.assertEquals(1, foundNodes.size());
		Iterator it = foundNodes.iterator();
		while (it.hasNext()) {
			VocabNode foundNode = (VocabNode) it.next();
			Assert.assertEquals("Zeitgeist", foundNode.getText());
			Assert.assertEquals("noun", foundNode.getNodeType());
			Assert.assertEquals(Locale.GERMAN, foundNode.getLocale());
			Assert.assertTrue(october4.before(foundNode.getDateCreated()));
			Assert.assertTrue(october6.after(foundNode.getDateCreated()));
		}
	}

}