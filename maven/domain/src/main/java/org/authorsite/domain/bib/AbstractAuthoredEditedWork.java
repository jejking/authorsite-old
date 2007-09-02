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
package org.authorsite.domain.bib;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Transient;

import org.authorsite.domain.AbstractHuman;

/**
 * Abstract class defining properties to describe works which have a set of
 * authors and a set of editors.
 * 
 * @author jejking
 * 
 */
public abstract class AbstractAuthoredEditedWork extends AbstractWork {

   /**
         * Default constructor.
         */
    public AbstractAuthoredEditedWork() {
	super();
    }

    /**
         * Adds an author to the set of authors.
         * 
         * @param author
         *                May not be <code>null</code>.
         * @return true if added, false if already there.
         */
    public boolean addAuthor(AbstractHuman author) {
	if (author == null) {
	    throw new IllegalArgumentException("author parameter is null");
	}
	return this.workProducers.add(new Author(this, author));
    }

    /**
         * Adds a collection of authors to the set of authors.
         * 
         * @param authors
         *                may not be <code>null</code>.
         * @return true if added, false if not.
         */
    public boolean addAuthors(Collection<? extends AbstractHuman> authors) {
	if (authors == null) {
	    throw new IllegalArgumentException("collection of authors is null");
	}
        boolean addedAll = true;
        for (AbstractHuman author : authors ) {
            if ( ! this.addAuthor(author)) {
                addedAll = false;
            }
        }
	return addedAll;
    }

    /**
         * Removes an author.
         * 
         * @param authorToRemove
         * @return true if removed, false if was not in set
         */
    public boolean removeAuthor(AbstractHuman authorToRemove) {
	if (authorToRemove != null) {
	    return this.workProducers.remove(new Author(this, authorToRemove) );
	} else {
	    return false;
	}
    }

    /**
         * Adds an editor to the set of editors.
         * 
         * @param editor
         * @return true if added, false if not.
         * @throws IllegalArgumentException
         *                 if parameter is <code>null</code>
         */
    public boolean addEditor(AbstractHuman editor) {
	if (editor == null) {
	    throw new IllegalArgumentException("editor parameter is null");
	}
	return this.workProducers.add(new Editor(this, editor));
    }

    /**
         * Adds a collection of editors to the editors set.
         * 
         * @param editors
         *                may not be <code>null</code>
         * @return true if added, false if not
         * @throws IllegalArgumentException
         *                 if parameter is <code>null</code>
         */
    public boolean addEditors(Collection<? extends AbstractHuman> editors) {
	if (editors == null) {
	    throw new IllegalArgumentException("Editors parameter is null");
	}
	boolean addedAll = true;
        for (AbstractHuman editor : editors) {
            if (! this.addEditor(editor)) {
                addedAll = false;
            }
        }
        return addedAll;
    }

    /**
         * Removes editor from the editors set.
         * 
         * @param editor
         * @return true if removed, false if not.
         */
    public boolean removeEditor(AbstractHuman editor) {
	if (editor != null) {
	    return this.workProducers.remove(new Editor(this, editor));
	} else {
	    return false;
	}
    }

    /**
     * Gets authors.
     * 
     * @return set of authors
     */
    @Transient
    public Set<Author> getAuthors() {
	Set<WorkProducer> authorWorkProducers = super.getWorkProducersOfType(Author.AUTHOR);
        Set<Author> authors = new HashSet<Author>();
        for (WorkProducer workProducer : authorWorkProducers ) {
            if (workProducer instanceof Author) {
                authors.add((Author) workProducer);
            }
        }
        return authors;
    }
    
    
    /**
     * Gets editors.
     * 
     * @return set of editors
     */
    @Transient
    public Set<Editor> getEditors() {
	Set<WorkProducer> editorWorkProducers = super.getWorkProducersOfType(Editor.EDITOR);
        Set<Editor> editors = new HashSet<Editor>();
        for (WorkProducer workProducer : editorWorkProducers ) {
            if (workProducer instanceof Editor) {
                editors.add((Editor) workProducer);
            }
        }
        return editors;
    }
    
    
}