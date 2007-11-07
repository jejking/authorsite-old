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

import java.util.SortedSet;

import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.AbstractWork;
import org.authorsite.domain.bib.Thesis;
import org.authorsite.domain.bib.WorkDates;


public class ThesisHandler implements RISEntryHandler {

    
    public AbstractWork buildWorkFromEntry(RISEntry entry) throws RISException {

	SortedSet<Individual> authoritativeAuthors = HandlerHelper.getAuthoritativeIndividuals(entry, "A1");
        
        // extract year
        WorkDates year = HandlerHelper.extractYear(entry.getValues("Y1"));
        
        // extract title
        String title = HandlerHelper.getFirstString(entry.getValues("T1"));
        
        // extract awarding body
        Collective awardingBody = new Collective();
        awardingBody.setName(HandlerHelper.getFirstString(entry.getValues("PB")));
        awardingBody.setPlace(HandlerHelper.getFirstString(entry.getValues("CY")));
        
        // extract degree
        String degree = HandlerHelper.getFirstString(entry.getValues("M1"));
        
        // assemble the thesis
        Thesis thesis = new Thesis();
        thesis.setTitle(title);
        thesis.setAuthor(authoritativeAuthors.first());
        thesis.setAwardingBody(awardingBody);
        thesis.setDegree(degree);
        thesis.setWorkDates(year);
        
        return thesis;

    }

}
