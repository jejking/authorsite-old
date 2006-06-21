/*
 * TextContentImpl.java, created on 07-Mar-2004 at 13:33:24
 * 
 * Copyright John King, 2004.
 *
 *  TextContentImpl.java is part of authorsite.org's MailArchive program.
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
package org.authorsite.mailarchive.model.impl;

import java.util.*;

import org.apache.commons.lang.builder.*;
import org.apache.log4j.*;
import org.authorsite.mailarchive.model.*;

/**
 * Plain java implementation of <code>TextContent</code> interface
 * 
 * @hibernate.class table="TextContent" proxy="org.authorsite.mailarchive.model.TextContent"
 * @hibernate.cache usage="nonstrict-read-write"
 * @author jejking
 * @version $Revision: 1.15 $
 */
public final class TextContentImpl implements TextContent, Versionable, Comparable {

	private int size;
	private String fileName;
	private String disposition;
	private String description;
	private Integer ID;
	private Integer version;
	private String content;
	private String mimeType;
	private Set languages;
	private TextContentRole role;

	private static Logger logger = Logger.getLogger(TextContentImpl.class);

	/**
	 * Default constructor
	 *
	 */
	public TextContentImpl() {
		languages = new HashSet();
		logger.debug("Created new TextContentImpl using default constructor");
	}

	/**
	 * @hibernate.id column = "TextContentID" generator-class="native"
	 * @see org.authorsite.mailarchive.model.Identifiable#getID()
	 */
	public Integer getID() {
		return ID;
	}

	/**
	 * @see org.authorsite.mailarchive.model.Identifiable#setID(int)
	 */
	public void setID(Integer newID) {
		ID = newID;
	}

	/**
	 * @hibernate.version
	 * @see org.authorsite.mailarchive.model.impl.Versionable#getVersion()
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * 
	 * @see org.authorsite.mailarchive.model.impl.Versionable#setVersion(java.lang.Integer)
	 */
	public void setVersion(Integer newVersion) {
		version = newVersion;
	}

	/**
	 * @hibernate.property type="text" column="content" not-null="true"
	 * @see org.authorsite.mailarchive.domain.TextContent#getContent()
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @see org.authorsite.mailarchive.domain.TextContent#setContent(java.lang.String)
	 */
	public void setContent(String newContent) {
		content = newContent;
	}

	/**
	 * @hibernate.property type="string" column="MimeType"
	 * @see org.authorsite.mailarchive.domain.Content#getMimeType()
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * @see org.authorsite.mailarchive.domain.Content#setMimeType(java.lang.String)
	 */
	public void setMimeType(String newMimeType) {
		mimeType = newMimeType;
	}

	/**
	 * @hibernate.set table="TextContent_Language" cascade="none"
	 * @hibernate.collection-key column="TextContentID"
	 * @hibernate.collection-many-to-many class="org.authorsite.mailarchive.model.Language" column="LanguageID"
         * @hibernate.collection-cache usage="nonstrict-read-write"
	 *
	 * @see org.authorsite.mailarchive.domain.Content#getLanguages()
	 * @return Set of <code>Language</code> instances
	 */
	public Set getLanguages() {
		return languages;
	}

	/**
	 * @see org.authorsite.mailarchive.domain.Content#setLanguages(java.util.Set)
	 * @param Set of <code>Language</code> instances
	 * @throws IllegalArgumentException if newLanguages is null or contains a reference to an
	 * object which is not an instance of <code>Language</code>
	 */
	public void setLanguages(Set newLanguages) {
		if (newLanguages == null) {
			logger.warn("Set newLanguages was null, TextContentImpl " + getID());
			throw new IllegalArgumentException("Set newLanguages was null");
		}
		else {
			Iterator it = newLanguages.iterator();
			while (it.hasNext()) {
				if (it.next() instanceof Language) {
					continue;
				}
				else {
					logger.warn("Set newLanguages contained reference to an object which is not an isntance of Language, TextContentImpl " + getID());
					throw new IllegalArgumentException("Set newLanguages contained reference to an object which is not an instance of Language");
				}
			}
			languages = newLanguages;
		}
	}

	/**
	 * @hibernate.property name="role" type="org.authorsite.mailarchive.model.impl.hibernate.TextContentRoleType"
	 * @hibernate.column name="role" length="12" not-null="true"
	 * @see org.authorsite.mailarchive.domain.TextContent#getRole()
	 */
	public TextContentRole getRole() {
		return role;
	}

	/**
	 * @see org.authorsite.mailarchive.domain.TextContent#setRole(java.lang.String)
	 */
	public void setRole(TextContentRole newRole) {
	    role = newRole;
	}

	/**
	 * @hibernate.property name="description" type="text" length="200" column="description"
	 * @see org.authorsite.mailarchive.model.MessageContent#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @see org.authorsite.mailarchive.model.MessageContent#setDescription(java.lang.String)
	 */
	public void setDescription(String newDescription) {
		description = newDescription;
	}

	/**
	 * @hibernate.property name="disposition" type="text" length="50" column="disposition"
	 * @see org.authorsite.mailarchive.model.MessageContent#getDisposition()
	 */
	public String getDisposition() {
		return disposition;
	}

	/**
	 * @see org.authorsite.mailarchive.model.MessageContent#setDisposition(java.lang.String)
	 */
	public void setDisposition(String newDisposition) {
		disposition = newDisposition;
	}

	/**
	 * @hibernate.property name="fileName" type="text" length="200" column="fileName"
	 * @see org.authorsite.mailarchive.model.MessageContent#getFileName()
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @see org.authorsite.mailarchive.model.MessageContent#setFileName(java.lang.String)
	 */
	public void setFileName(String newFileName) {
		fileName = newFileName;
	}

	/**
	 * @hibernate.property name="size" type="integer"
	 * @see org.authorsite.mailarchive.model.MessageContent#getSize()
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @see org.authorsite.mailarchive.model.MessageContent#setSize(int)
	 */
	public void setSize(int newSize) {
		size = newSize;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof TextContentImpl) {
			TextContentImpl input = (TextContentImpl) o;
			return new EqualsBuilder().append(content, input.content).append(role, input.role).isEquals();
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(content).append(role).toHashCode();
	}

	public String toString() {
		return content.substring(0, 200);
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		TextContentImpl input = (TextContentImpl) o;
		return new CompareToBuilder().append(content, input.content).append(role, input.role).toComparison();
	}

}