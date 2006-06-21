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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * An attachment wraps binary data which is associated with a {@link Page} object.
 * Like a page, it is versioned and instances are immutable and hence threadsafe.
 * 
 * @author jejking
 */
public final class Attachment extends AbstractWikiItem implements Serializable, Comparable {

    private static final Pattern mimeTypePattern = Pattern.compile("([\\p{Graph}&&[^()/<>@,;:\\\"/\\[\\]?=\\\\]])+/([\\p{Graph}&&[^()/<>@,;:\\\"/\\[\\]?=\\\\]])+");
    
	/**
	 * Computed serial version UID (from Eclipse). 
	 */
	private static final long serialVersionUID = -5166347366442222496L;
	private static final StandardToStringStyle TO_STRING_STYLE = new StandardToStringStyle();
	static {
		TO_STRING_STYLE.setUseIdentityHashCode(false);
	}
	
	private String mimeType;
	private byte[] content;
	private transient int hashCode;
	private transient String toStringText;
    		
    /**
     * Constructs a new Attachment using the supplied data. Note that the
     * content array is copied into the bean's internal reference in order
     * to preserve encapsulation.
     *  
     * @param author
     * @param name
     * @param mimeType
     * @param content
     * @see AbstractWikiItem#AbstractWikiItem(String, String)
     */
	public Attachment(String author, String name, String mimeType, byte[] content) {
		super(author, name);
		init(mimeType, content);
	}
	
    /**
     * Constructs a new Attachment version based on the supplied original attachment.
     * Note that the new content array is copied into the bean's internal reference 
     * in order to preserve encapsulation. 
     * 
     * <p>Note, the mime type property is copied from the original attachment. The
     * new content should match the mime type of the original attachment.</p>. 
     * 
     * @param originalAttachment
     * @param editedBy
     * @param newContent
     * @see AbstractWikiItem#AbstractWikiItem(String, String)
	 */
	public Attachment(Attachment originalAttachment, String editedBy, byte[] newContent) {
		super(originalAttachment, editedBy);
		init (originalAttachment.mimeType, newContent);
	}
	
	/**
	 * Helper method for constructors and {@link Attachment#readObject(ObjectInputStream)}.
	 * 
	 * @param mimeType
	 * @param content
	 */
	private void init(String mimeType, byte[] content) {
		if (mimeType == null) {
			throw new IllegalArgumentException("mimeType is null");
		}
		if (mimeType.trim().length() == 0) {
			throw new IllegalArgumentException("mimeType is empty string");
		}
		if (content == null) {
			throw new IllegalArgumentException("content is null");
		}
		if (content.length == 0) {
			throw new IllegalArgumentException("content is zero length byte array");
		}

        String trimmedMimeType = mimeType.trim();
        if (trimmedMimeType.length() == 0) {
            throw new IllegalArgumentException("mimeType is empty string");
        }
        Matcher m = Attachment.mimeTypePattern.matcher(trimmedMimeType);
        if (! m.matches()) {
            throw new IllegalArgumentException("mimeType does not match required pattern");
        }
        this.mimeType = trimmedMimeType;
        
        this.content = new byte[content.length];
		System.arraycopy(content, 0, this.content, 0, content.length);
		this.hashCode = calculateHashCode();
		this.toStringText = buildToStringText();
	}
	
    /**
     * Returns a stream from which the content can be read. We do not
     * return the content itself in order to preserve encapsulation.
     * 
     * @return stream
     * @throws IOException if a problem occurs return the content
     */
    public InputStream getContentAsStream() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(content);
        return inputStream;
    }
	
	/**
     * Returns the mime-type.
     * 
	 * @return mime-type
	 */
	public String getMimeType() {
		return mimeType;
	}

	@Override
	public int getSize() {
		return content.length;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (! (obj instanceof Attachment)) {
			return false;
		}
		Attachment rhs = (Attachment) obj;
		if (this.getVersionedName().equals(rhs.getVersionedName())) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.hashCode;
	}

	@Override
	public String toString() {
		return this.toStringText;
	}

	public int compareTo(Object o) {

		if (o == null) {
			throw new NullPointerException("cannot compare to null");
		}
		
		if (o.equals(this)) {
			return 0;
		}
		
		if (! (o instanceof Attachment)) {
			throw new ClassCastException("cannot compare instance of " + o.getClass().getName() 
					+ " to Attachment");
		}
		Attachment rhs = (Attachment) o;
		return this.getVersionedName().compareTo(rhs.getVersionedName());
		
	}
	
	private int calculateHashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(3187, 131);
		return hashCodeBuilder.append(this.getVersionedName().hashCode()).toHashCode();
	}

	private String buildToStringText() {
		ToStringBuilder toStringBuilder = new ToStringBuilder(this, Attachment.TO_STRING_STYLE);
		return toStringBuilder.append(this.getVersionedName()).toString();
	}

	
	/*
	 * SERIALIZATION CODE
	 */
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		stream.writeObject(mimeType);
		stream.writeObject(content);
	}
	
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		String mimeType = (String) stream.readObject();
		byte[] content = (byte[]) stream.readObject();
		
		try {
			init(mimeType, content);
		}
		catch (IllegalArgumentException iae) {
			// this is the appropriate exception if the stream
			// contains duff values
			throw new InvalidObjectException(iae.getMessage());
		}
	}    
}
