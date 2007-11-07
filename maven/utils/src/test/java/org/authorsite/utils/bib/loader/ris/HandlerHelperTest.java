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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SortedSet;

import junit.framework.TestCase;

import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.WorkDates;


public class HandlerHelperTest extends TestCase {

    public void testGetIndividualsOne() {
        String authorEntryValue = "King, John";
        SortedSet<Individual> authors = HandlerHelper.getIndividuals(authorEntryValue);
        assertEquals(1, authors.size());
        Individual i = new Individual();
        i.setName("King");
        i.setGivenNames("John");
        assertTrue(authors.contains(i));
        
        String s2 = "King, John, The one writing the code";
        SortedSet<Individual> authors2 = HandlerHelper.getIndividuals(s2);
        assertEquals(1, authors2.size());
        Individual i2 = new Individual();
        i2.setName("King");
        i2.setGivenNames("John");
        i2.setNameQualification("The one writing the code");
        assertTrue(authors2.contains(i2));
        
        String s3 = "King";
        SortedSet<Individual> authors3 = HandlerHelper.getIndividuals(s3);
        assertEquals(1, authors3.size());
        Individual i3 = new Individual();
        i3.setName("King");
        assertTrue(authors3.contains(i3));
    }
    
    public void testGetIndividualsTwo() {
        String s1 = "King, John; Bar, Foo";
        SortedSet<Individual> a1 = HandlerHelper.getIndividuals(s1);
        assertEquals(2, a1.size());
        
        Individual i1 = new Individual();
        i1.setName("King");
        i1.setGivenNames("John");
        
        Individual i2 = new Individual();
        i2.setName("Bar");
        i2.setGivenNames("Foo");
        
        assertTrue(a1.contains(i1));
        assertTrue(a1.contains(i2));
    }
    
    
    public void testGetYear() throws Exception {
        List<String> strings = new ArrayList<String>();
        strings.add("1973");
        GregorianCalendar gc = new GregorianCalendar( 1973, Calendar.JANUARY, 1 );
        assertEquals(gc.getTime(), HandlerHelper.extractYear(strings).getDate());
        
        List<String> strings2 = new ArrayList<String>();
        strings2.add("1973-75");
        WorkDates years = new WorkDates(1973, 1975);
        assertEquals(years, HandlerHelper.extractYear(strings2));
        
        List<String> strings3 = new ArrayList<String>();
        strings3.add("1973-1975");
        assertEquals(years, HandlerHelper.extractYear(strings3));
    }
    
}
