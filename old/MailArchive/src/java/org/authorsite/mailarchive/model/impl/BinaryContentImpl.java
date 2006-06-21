/*
 * BinaryContentImpl.java, created on 07-Mar-2004 at 13:32:10
 * 
 * Copyright John King, 2004.
 *
 *  BinaryContentImpl.java is part of authorsite.org's MailArchive program.
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
 * Plain Java implementation of the <code>BinaryContent</code> interface.
 * 
 * @hibernate.class table="BinaryContent" proxy="org.authorsite.mailarchive.model.BinaryContent"
 * @hibernate.cache usage="nonstrict-read-write"
 * @author jejking
 * @version $Revision: 1.16 $
 */
public final class BinaryContentImpl implements BinaryContent, Versionable, Comparable {

	private int size;
	private String fileName;
	private String disposition;
	private String description;
	private Integer ID;
	private Integer version;
	private int hashCode;
	private byte[] content;
	private String mimeType;
	private Set languages;

	private static Logger logger = Logger.getLogger(BinaryContentImpl.class);

	/**
	 * Default constructor.
	 * 
	 */
	public BinaryContentImpl() {
		languages = new HashSet();
		logger.debug("New BinaryContentImpl created using default constructor");
	}

	/**
	 * @hibernate.id column = "BinaryContentID" generator-class="native"
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
	 * @see org.authorsite.mailarchive.model.impl.Versionable#setVersion(java.lang.Integer)
	 */
	public void setVersion(Integer newVersion) {
		version = newVersion;
	}

	/**
	 * @hibernate.property type="binary" column="content"
	 * @see org.authorsite.mailarchive.model.BinaryContent#getContent()
	 * @return clone of the internal byte array
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * @see org.authorsite.mailarchive.model.BinaryContent#setContent(byte[])
	 * @param a byte array representing the new content. This will be cloned for use by the instance
	 */
	public void setContent(byte[] newContent) {
		content = newContent;
		if (hashCode != 0) { // i.e. it's been calculated and cached
			hashCode = 0; // set it to 0. No need to calculate it again until it's requested
		}
	}

	/**
	 * @hibernate.property type="string" length="100"
	 * @see org.authorsite.mailarchive.model.Content#getMimeType()
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * @see org.authorsite.mailarchive.model.Content#setMimeType(java.lang.String)
	 */
	public void setMimeType(String newMimeType) {
		mimeType = newMimeType;
	}

	/**
	 * @hibernate.set table="BinaryContent_Language" cascade="none"
	 * @hibernate.collection-key column="BinaryContentID"
	 * @hibernate.collection-many-to-many class="org.authorsite.mailarchive.model.Language" column="LanguageID"
         * @hibernate.collection-cache usage="nonstrict-read-write"
	 *
	 * @see org.authorsite.mailarchive.model.Content#getLanguages()
	 * @return a Set of <code>Language</code> instances representing the instance's languages
	 */
	public Set getLanguages() {
		return languages;
	}

	/**
	 * @see org.authorsite.mailarchive.model.Content#setLanguages(Set)
	 * @see org.authorsite.mailarchive.model.Language
	 * @param Set of instances of <code>Language</code>
	 * @throws IllegalArgumentException if the Set contains anything other than Language implementations
	 * @throws NullPointerException if the Set is null
	 */
	public void setLanguages(Set newLanguages) {
		if (newLanguages == null) {
			logger.warn("Set newLanguages was null on BinaryContentImpl " + getID());
			throw new NullPointerException("Set newLanguages was null");
		}
		else {
			Iterator it = newLanguages.iterator();
			while (it.hasNext()) {
				if (it.next() instanceof Language) {
					continue;
				}
				else {
					logger.warn("Set contained a reference to an object which is not an instance of Language on BinaryContentImpl " + getID());
					throw new IllegalArgumentException("Set contained objects which are not instances of Language");
				}
			}
			languages = newLanguages;
		}
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
		if (o instanceof BinaryContentImpl) {
			BinaryContentImpl input = (BinaryContentImpl) o;
			return new EqualsBuilder().append(content, input.content).isEquals();
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		if (content == null) {
			return 0;
		}
		if (hashCode == 0) {
			hashCode = new HashCodeBuilder().append(content).toHashCode();
			logger.debug("Calculated new hash code for BinaryContentImpl " + ID + ":  " + hashCode);
		}
		return hashCode;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("BinaryContentImpl:");
		sb.append("HashCode:\t");
		sb.append(hashCode());
		sb.append("\n");
		sb.append("mimeType:\t");
		sb.append(getMimeType());
		sb.append("\n");
		sb.append("languages: \t");
		Iterator it = languages.iterator();
		while (it.hasNext()) {
			sb.append(it.next());
			sb.append(" ");
		}
		sb.append("\n");
		return sb.toString();
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		BinaryContentImpl input = (BinaryContentImpl) o;
		return new CompareToBuilder().append(content, input.content).toComparison();
	}

}