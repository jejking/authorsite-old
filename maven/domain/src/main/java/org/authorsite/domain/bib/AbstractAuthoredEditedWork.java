package org.authorsite.domain.bib;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.authorsite.domain.AbstractHuman;

/**
 * Abstract class defining properties to describe works which have a 
 * set of authors and a set of editors.
 * 
 * @author jejking
 *
 */
public abstract class AbstractAuthoredEditedWork extends AbstractWork {

    protected Set<AbstractHuman> authors = new HashSet<AbstractHuman>();
    protected Set<AbstractHuman> editors = new HashSet<AbstractHuman>();

    /**
     * Default constructor.
     */
    public AbstractAuthoredEditedWork() {
	super();
    }

    /**
     * Adds an author to the set of authors.
     * 
     * @param author May not be <code>null</code>.
     * @return true if added, false if already there.
     */
    public boolean addAuthor(AbstractHuman author) {
        if ( author == null ) {
           throw new IllegalArgumentException("author parameter is null"); 
        }
        return this.authors.add(author);
    }

    /**
     * Adds a collection of authors to the set of authors.
     * 
     * @param authors may not be <code>null</code>.
     * @return true if added, false if not.
     */
    public boolean addAuthors(Collection<? extends AbstractHuman> authors) {
        if ( authors == null ) {
            throw new IllegalArgumentException("collection of authors is null");
        }
        return this.authors.addAll(authors);
    }

    /**
     * Removes an author.
     * 
     * @param authorToRemove
     * @return true if removed, false if was not in set
     */
    public boolean removeAuthor(AbstractHuman authorToRemove) {
        if ( authorToRemove != null ) {
            return this.authors.remove( authorToRemove );
        }
        else {
            return false;
        }
    }

    /**
     * Adds an editor to the set of editors.
     * 
     * @param editor
     * @return true if added, false if not.
     * @throws IllegalArgumentException if parameter is <code>null</code>
     */
    public boolean addEditor(AbstractHuman editor) {
        if ( editor == null ) {
            throw new IllegalArgumentException("editor parameter is null");
        }
        return this.editors.add(editor);
    }

    /**
     * Adds a collection of editors to the editors set.
     * 
     * @param editors may not be <code>null</code>
     * @return true if added, false if not
     * @throws IllegalArgumentException if parameter is <code>null</code>
     */
    public boolean addEditors(Collection<? extends AbstractHuman> editors) {
        if ( editors == null ) {
            throw new IllegalArgumentException("Editors parameter is null");
        }
        return this.editors.addAll(editors);
    }

    /**
     * Removes editor from the editors set.
     * 
     * @param editor
     * @return true if removed, false if not.
     */
    public boolean removeEditor(AbstractHuman editor) {
        if ( editor != null ) {
            return this.editors.remove(editor);
        }
        else {
            return false;
        }
    }

    /**
     * Gets authors.
     * 
     * @return set of authors
     */
    public Set<AbstractHuman> getAuthors() {
        return authors;
    }

    /**
     * Gets editors.
     * 
     * @return set of editors
     */
    public Set<AbstractHuman> getEditors() {
        return editors;
    }

}