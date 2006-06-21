/*
 * juenger wiki - a JSP Wiki clone
 * 
 * Copyright (C) 2005 John King 
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.juenger.wiki.item;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * @author jking
 *
 */
/**
 * Represents a generic item managed by the wiki application. 
 * It encapsulates functionality shared between {@link Page} and
 * {@link Attachment}.
 * 
 * @author jking
 */
public abstract class AbstractWikiItem implements Serializable {

	private VersionedName versionedName;
	
	private String originalAuthor;
	private Date creationDate;
	private String versionEditor;
	private Date versionDate;
	
	/**
     * Returns the name and version of the item.
     * 
	 * @return name and version bean.
	 */
	public VersionedName getVersionedName() {
		return versionedName;
	}
	
	/**
     * Returns the original author. This property remains
     * unchanged in subsequent versions.
     * 
	 * @return the original author
	 */
	public String getOriginalAuthor() {
		return originalAuthor;
	}

	/**
     * Returns the name of the user who created 
     * this version.
     * 
	 * @return editor of this version
	 */
	public String getVersionEditor() {
		return versionEditor;
	}

	/**
     * Returns the date the first version was created.
     * 
	 * @return a copy of the creation date instance.
	 */
	public Date getCreationDate() {
		return (Date) creationDate.clone();
	}

	/**
     * Retursn the date this version was created.
     * 
	 * @return a copy of the version date instance.
	 */
	public Date getVersionDate() {
		return (Date) versionDate.clone();
	}

	/**
	 * @return size of the item - number of bytes for binary 
     *  and number of characters for textual content.
     *
	 */
	public abstract int getSize();
		
	
	/**
	 * Creates a new item from scratch using the given author 
	 * and item name.
	 * 
	 * <p>Sets the <code>originalAuthor</code> and <code>lastEditedBy</code>
	 * properties to the value of the <code>author</code> parameter. The 
	 * <code>createdDate</code> and <code>versionDate</code> properties
	 * are set to the current time and date. The <code>version</code> 
	 * property is set to 1.
	 * </p>
	 * 
	 * @param author
	 * @param name
	 */
	protected AbstractWikiItem(String author, String name) {
		if (author == null) {
			throw new IllegalArgumentException("author parameter is null");
		}
		if (name == null) {
			throw new IllegalArgumentException("name parameter is null");
		}
		author = author.trim();
		if (author.length() == 0) {
			throw new IllegalArgumentException("author parameter is empty string");
		}
		name = name.trim();
		if (name.length() == 0) {
			throw new IllegalArgumentException("name parameter is empty string");
		}
		this.originalAuthor = author;
		this.versionEditor = author;
		Date now = new Date();
		this.creationDate = now;
		this.versionDate = now;
		this.versionedName = new VersionedName(name, 0);
	}

    /**
     * Creates a new version of an item from the item passed in.
     * 
     * <p>It copies the following properties from the original item:</p>
     * <ul>
     * <li><code>creationDate</code>
     * <li><code>originalAuthor</code>
     * </ul>
     * 
     * <p>The version editor is set to the corresponding parameter. The
     * version date is set to the current time and the version property 
     * of the {@link VersionedName} is incremented by one in a new bean 
     * set on the new instance.</p>
     * 
     * @param originalItem
     * @param editor
     */
	protected AbstractWikiItem(AbstractWikiItem originalItem, String editor) {
		if (originalItem == null) {
			throw new IllegalArgumentException("original item is null");
		}
		if (editor == null) {
			throw new IllegalArgumentException("editedBy is null");
		}
		editor = editor.trim();
	    if (editor.length() == 0) {
	    	throw new IllegalArgumentException("editedBy is empty string");
	    }
	    Date now = new Date();
	    this.creationDate = originalItem.getCreationDate();
	    this.versionDate = now;
	    this.originalAuthor = originalItem.originalAuthor;
	    this.versionEditor = editor;
	    this.versionedName = new VersionedName(originalItem.versionedName.getName(), 
	    		originalItem.versionedName.getVersion() + 1);
	}
	
	/*
	 * SERIALIZATION CODE
	 */
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		stream.writeObject(versionedName);
		stream.writeObject(originalAuthor);
		stream.writeObject(creationDate);
		stream.writeObject(versionEditor);
		stream.writeObject(versionDate);
	}
	
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		VersionedName versionedName = (VersionedName) stream.readObject();
		String originalAuthor = (String) stream.readObject();
		Date creationDate = (Date) stream.readObject();
		String versionEditor = (String) stream.readObject();
		Date versionDate = (Date) stream.readObject();
		
		// check them all for not null and zero length strings
		if (versionedName == null) {
			throw new InvalidObjectException("versioned name field is null");
		}
		if (originalAuthor == null) {
			throw new InvalidObjectException("original author field is null");
		}
		else if (originalAuthor.length() == 0) {
			throw new InvalidObjectException("original author field is empty string");
		}
		if (creationDate == null) {
			throw new InvalidObjectException("creation date field is null");
		}
		if (versionEditor == null) {
			throw new InvalidObjectException("version editor field is null");
		}
		else if (versionEditor.length() == 0) {
			throw new InvalidObjectException("version editor field is empty string");
		}
		if (versionDate == null) {
			throw new InvalidObjectException("version date field is null");
		}
		if (versionDate.before(creationDate)) {
			throw new InvalidObjectException("version date is before creation date");
		}
		
		// so, invariants are probably ok... assemble object state
		this.versionedName = versionedName;
		this.originalAuthor = originalAuthor;
		// defensive copying of mutable objects
		this.creationDate = new Date (creationDate.getTime());
		this.versionDate = new Date(versionDate.getTime());
		
	}    

}
