/*
 * juenger wiki - a JSP Wiki clone
 * 
 * Copyright (C) 2006 John King 
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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.juenger.wiki.WikiException;
import org.juenger.wiki.service.impl.DefaultAttachmentResolverService;

/**
 * Bean like representation of a wiki page. The class is designed to be immutable and
 * thus thread safe. It encapsulates basic metadata about the page and provides
 * its wiki text. Rendering of the wiki text is the responsibility of implementations
 * of {@link org.juenger.wiki.service.PageTranslationService} which is thus cleanly decoupled.
 * 
 * @author jejking
 */
public final class Page extends AbstractWikiItem implements Serializable, Comparable {

	/**
	 * Computed serial version UID (from Eclipse). 
	 */
	private static final long serialVersionUID = 8946589440119446747L;
	
	private static final StandardToStringStyle TO_STRING_STYLE = new StandardToStringStyle();
	static {
		TO_STRING_STYLE.setUseIdentityHashCode(false);
	}
	
	private String text;
	private Set<VersionedName> attachments;
	
	private transient int hashCode;
	private transient String toStringText;
	
	
	/**
	 * Constructs a page instance with the provided parameters.
	 * Also pre-calculates a value for {@link Object#hashCode()}
	 * and text for {@link Object#toString()}. The {@link Attachment}
	 * Map is set to an unmodifiable empty map.
	 * 
	 * @param author
	 * @param name
	 * @param text
	 */
	public Page(String author, String name, String text) {
		super(author, name);
		if (text == null) {
			throw new IllegalArgumentException("text parameter is null");
		}
		text = text.trim();
		if (text.length() == 0) { 
			throw new IllegalArgumentException("text parameter is empty string");
		}
		this.text = text;
		hashCode = calculateHashCode();
		attachments = Collections.unmodifiableSet(new HashSet<VersionedName>());
		toStringText = buildToStringText();
	}
	
	/**
	 * Contructs a new version of a page with a new map of attachments. This 
	 * map would normally result from a logical addition, removal or updating 
	 * of an attachment from a page - which results in a new Page version
	 * being created.
	 * 
	 * <p>Also pre-calculates a value for {@link Object#hashCode()}
	 * and text for {@link Object#toString()}. The reference to the
	 * map passed in is copied and set as an unmodifiable map as a field on
	 * the bean.</p>
	 * 
	 * 
	 * @param originalPage
	 * @param editedBy
	 * @param newAttachmentSet
	 */
	public Page(Page originalPage, String editedBy, Set<VersionedName> newAttachmentSet) {
		super(originalPage, editedBy);
		this.text = originalPage.text;
		if (newAttachmentSet == null) {
			throw new IllegalArgumentException("new attachment set is null");
		}
		// note, no check if the map is empty - this is quite legitimate if, say, a page had one attachment 
		// and it's now been deleted
		this.attachments = Collections.unmodifiableSet(new HashSet<VersionedName>(newAttachmentSet));
		hashCode = calculateHashCode();
		toStringText = buildToStringText();
	}
    
	/**
	 * Constructs a new version of a page using the updated text supplied.
	 * Note that the attachments map of the original page is copied into
	 * a new unmodifiable map member field on the bean.
	 * 
	 * <p>Also pre-calculates a value for {@link Object#hashCode()}
	 * and text for {@link Object#toString()}.</p> 
	 * 
	 * @param originalPage
	 * @param editedBy
	 * @param newText
	 */
    public Page(Page originalPage, String editedBy, String newText) {
        super(originalPage, editedBy);
        
        if (newText == null) {
            throw new IllegalArgumentException("new text is null");
        }
        newText = newText.trim();
        if (newText.length() == 0) {
            throw new IllegalArgumentException("new text is empty string");
        }
        if (originalPage.text.equals(newText)) {
            throw new IllegalArgumentException("new text is identical with old text");
        }
        this.text = newText;
        // copy the original attachment collection and make it unmodifiable
        this.attachments = Collections.unmodifiableSet(new HashSet<VersionedName>(originalPage.getAttachmentVersionedNames()));
        hashCode = calculateHashCode();
        toStringText = buildToStringText();
    }

	/**
	 * Returns unmodifiable set of the attachment versioned names.
     * 
	 * @return map of attachment instances
	 */
    public Set<VersionedName> getAttachmentVersionedNames() {
		// this should already be unmodifiable from the constructors
		// so no danger of breaking immutability here
		return attachments;
	}
    
    public Set<Attachment> getAttachments() throws WikiException {
    	return DefaultAttachmentResolverService.getInstance().resolveAttachments(this);
    }

    /**
     * Returns the original wiki text.
     * 
     * @return wiki text
     */
	public String getText() {
		return text;
	}
 
	@Override
	public int getSize() {
		return text.length();
	}

	/**
	 * Checks for equality based on:
	 * <ol>
	 * <li>name</li>
	 * <li>version</li>
	 */		
    @Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (! (obj instanceof Page)) {
			return false;
		}
		Page rhs = (Page) obj;
		if (this.getVersionedName().equals( rhs.getVersionedName())) {
			return true;
		}
        return false;
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public String toString() {
		return toStringText;
	}
	
	/**
	 * Defines the natural ordering for the object. This is 
	 * consistent with <code>equals()</code> and sorts instances
	 * based on:
	 * 
	 * <ol>
	 * <li>name</li>
	 * <li>version</li>
	 * </ol>
	 * 
	 * @param o object to compare to
	 * @see Comparable#compareTo(T)
	 */
	public int compareTo(Object o) {
		
		if (o == null) {
			throw new NullPointerException("cannot compare to null");
		}
		
		if (o.equals(this)) {
			return 0;
		}
		
		if (! (o instanceof Page)) {
			throw new ClassCastException("cannot compare instance of " + o.getClass().getName() 
					+ " to Page");
		}
		// cast to Page
		Page rhs = (Page) o;
		return this.getVersionedName().compareTo(rhs.getVersionedName());
	}
    
    /**
     * Calculates hash code based on the {@link VersionedName} property.
     * @return hash code
     */
	private int calculateHashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(317, 31);
		return hashCodeBuilder.append(this.getVersionedName()).toHashCode();
	}
	
	private String buildToStringText() {
		ToStringBuilder toStringBuilder = new ToStringBuilder(this, Page.TO_STRING_STYLE);
		return toStringBuilder.append("Name", this.getVersionedName().getName())
					  .append("Version", this.getVersionedName().getVersion())
                      .append("Created", this.getCreationDate())
                      .append("Created by", this.getOriginalAuthor())
                      .append("Last Updated", this.getVersionDate())
                      .append("Last Updated by", this.getVersionEditor())
                      .toString();
	}
	
	/*
	 * SERIALIZATION CODE
	 */
	
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		stream.writeObject(text);
		stream.writeInt(attachments.size());
		for (VersionedName name: attachments) {
			stream.writeObject(name);
		}
	}
	
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		String text = (String) stream.readObject();
		if (text == null) {
			throw new InvalidObjectException("text is null");
		}
		if (text.length() == 0) {
			throw new InvalidObjectException("text is empty string");
		}
		int setSize = stream.readInt();
		HashSet<VersionedName> attachmentVersionedNames = new HashSet<VersionedName>();
		for (int i = 0; i < setSize; i++) {
			VersionedName name = (VersionedName) stream.readObject();
			if (attachmentVersionedNames.add(name)) {
				continue;
			}
			else {
				throw new InvalidObjectException("Versioned name " + name + " already listed");
			}
		}
		// check we actually got the expected number of versioned names in
		if (setSize != attachmentVersionedNames.size()) {
			throw new InvalidObjectException("Expected " + setSize + " versioned names, but got " + attachmentVersionedNames.size());
		}
		
		// ok, state should now be ok, set up the object
		this.text = text;
		this.attachments = Collections.unmodifiableSet(attachmentVersionedNames);
		hashCode = calculateHashCode();
		toStringText = buildToStringText();
	}    

    
}
