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
import org.authorsite.domain.bib.Book;
import org.authorsite.domain.bib.WorkDates;

public class BookHandler implements RISEntryHandler {

    public AbstractWork buildWorkFromEntry(RISEntry entry) throws RISException {

	// authors
	SortedSet<Individual> authoritativeAuthors = HandlerHelper
		.getAuthoritativeIndividuals(entry, "A1");

	// editors
	SortedSet<Individual> authoritativeEditors = HandlerHelper
		.getAuthoritativeIndividuals(entry, "E1");

	// year
	WorkDates year = HandlerHelper.extractYear(entry.getValues("Y1"));

	// title
	String title = HandlerHelper.getFirstString(entry.getValues("T1"));

	// publisher name
	String publisherName = HandlerHelper.getFirstString(entry
		.getValues("PB"));

	// place
	String placeName = HandlerHelper.getFirstString(entry.getValues("CY"));

	// we assume here that the publisher is a collective body
	Collective publisher = new Collective(publisherName, placeName);

	Book book = new Book();
	book.setTitle(title);
	book.setWorkDates(year);
	book.setPublisher(publisher);
	book.addAuthors(authoritativeAuthors);
	book.addEditors(authoritativeEditors);

	return book;

    }

}
