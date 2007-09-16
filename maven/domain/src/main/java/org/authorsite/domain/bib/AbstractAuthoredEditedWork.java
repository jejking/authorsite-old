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
     */
    public void addAuthor(AbstractHuman author) {
	if (author == null) {
	    throw new IllegalArgumentException("author parameter is null");
	}
	super.addWorkProducer(WorkProducerType.AUTHOR, author);
    }

    /**
     * Adds a collection of authors to the set of authors.
     * 
     * @param authors
     *                may not be <code>null</code>.
     */
    public void addAuthors(Collection<? extends AbstractHuman> authors) {
	if (authors == null) {
	    throw new IllegalArgumentException("collection of authors is null");
	}
	for (AbstractHuman author : authors) {
	    this.addAuthor(author);
	}
    }

    /**
     * Removes an author.
     * 
     * @param authorToRemove
     */
    public void removeAuthor(AbstractHuman authorToRemove) {
	super.removeWorkProducer(WorkProducerType.AUTHOR, authorToRemove);
    }

    /**
     * Adds an editor to the set of editors.
     * 
     * @param editor
     * @throws IllegalArgumentException
     *                 if parameter is <code>null</code>
     */
    public void addEditor(AbstractHuman editor) {
	if (editor == null) {
	    throw new IllegalArgumentException("editor parameter is null");
	}
	super.addWorkProducer(WorkProducerType.EDITOR, editor);
    }

    /**
     * Adds a collection of editors to the editors set.
     * 
     * @param editors
     *                may not be <code>null</code>
     * @throws IllegalArgumentException
     *                 if parameter is <code>null</code>
     */
    public void addEditors(Collection<? extends AbstractHuman> editors) {
	if (editors == null) {
	    throw new IllegalArgumentException("Editors parameter is null");
	}
	boolean addedAll = true;
	for (AbstractHuman editor : editors) {
	    this.addEditor(editor);
	}
    }

    /**
     * Removes editor from the editors set.
     * 
     * @param editor
     */
    public void removeEditor(AbstractHuman editor) {
	if (editor != null) {
	    super.removeWorkProducer(WorkProducerType.EDITOR, editor);
	} 
    }

    /**
     * Gets authors.
     * 
     * @return set of authors
     */
    @Transient
    public Set<AbstractHuman> getAuthors() {
	return super.getWorkProducersOfType(WorkProducerType.AUTHOR);
    }

    /**
     * Gets editors.
     * 
     * @return set of editors
     */
    @Transient
    public Set<AbstractHuman> getEditors() {
	return super.getWorkProducersOfType(WorkProducerType.EDITOR);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Author(s): ");
        for ( AbstractHuman human: this.getAuthors() ) {
            sb.append(human);
            sb.append(" ");
        }
        sb.append("Editor(s): ");
        for (AbstractHuman human : this.getEditors()) {
            sb.append(human);
            sb.append(" ");
        }
        return sb.toString();
    }
    
        
}