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

package org.authorsite.simplemailarchive.domain;

import java.util.Arrays;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.util.Assert;


/**
 * Represents a simple mail attachment.
 * @author jejking
 */
@Entity
public class SimpleAttachment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
	@Column(nullable = false)
    private String mimeType;
    
	@Column(nullable = true)
	private String description;
    
	@Column(nullable = true)
	private String disposition;
    
	@Column(nullable = false)
	private String fileName;
    
    @Column(nullable = false)
	private int size;
    
    @Lob()
    @Column(nullable = false)
    private byte[] contents;

    /**
     * Default constructor for benefit of JPA.
     */
    private SimpleAttachment() {
    }

    /**
     * Constructs using builder.
     * @param attachmentBuilder
     */
    private SimpleAttachment(AttachmentBuilder attachmentBuilder) {
		this.contents = attachmentBuilder.contents;
		this.size = attachmentBuilder.size;
		this.fileName = attachmentBuilder.fileName;
		this.mimeType = attachmentBuilder.mimeType;
		this.description = attachmentBuilder.description;
		this.disposition = attachmentBuilder.disposition;
	}

	/**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the mimeType
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * @return the description, may be <code>null</code>
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the disposition, may be <code>null</code>
     */
    public String getDisposition() {
        return disposition;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @return the contents
     */
    public byte[] getContents() {
        return Arrays.copyOf(this.contents, this.contents.length);
    }
    
    

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(contents);
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((disposition == null) ? 0 : disposition.hashCode());
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result
				+ ((mimeType == null) ? 0 : mimeType.hashCode());
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleAttachment other = (SimpleAttachment) obj;
		if (!Arrays.equals(contents, other.contents))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (disposition == null) {
			if (other.disposition != null)
				return false;
		} else if (!disposition.equals(other.disposition))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (mimeType == null) {
			if (other.mimeType != null)
				return false;
		} else if (!mimeType.equals(other.mimeType))
			return false;
		
		if (size != other.size)
			return false;
		return true;
	}

	@Override
    public String toString() {
    	StringBuilder builder = new StringBuilder();
    	builder.append("Attachment:");
    	builder.append(" id: " + id);
    	builder.append(", file-name: " + this.fileName);
    	builder.append(", mime-type: " + this.mimeType);
    	builder.append(", size: " + this.size);
    	if (this.description != null) {
    		builder.append(", description: " + this.description);
    	}
    	if (this.disposition != null) {
    		builder.append(", disposition: " + this.disposition);
    	}
    	return builder.toString();
    }
    
    /**
     * Builder for attachment objects.
     */
    public static class AttachmentBuilder {
    	
    	private final byte[] contents;
    	private final int size;
    	private final String fileName;
    	private final String mimeType;
    	private String description;
    	private String disposition;
    
    	/**
    	 * Constructs builder with all obligatory parameters for attachment.
    	 * @param theSimpleMailMessage owning message, may not be <code>null</code>
    	 * @param theContents contents, must be of length greater than 0
    	 * @param theFileName file name, may not be <code>null</code> or empty string
    	 * @param theMimeType may not be <code>null</code>, must be parsable as {@link MimeType}
    	 * @throws IllegalArgumentException if any of above conditions not met
    	 */
    	public AttachmentBuilder(byte[] theContents, String theFileName, String theMimeType) {
    		
    		Assert.notNull(theContents);
    		Assert.isTrue(theContents.length > 0);
    	    Assert.isTrue(theFileName != null && theFileName.trim().length() != 0);
    	    Assert.notNull(theMimeType);
    	    try {
    	    	@SuppressWarnings("unused")
				MimeType mimeType = new MimeType(theMimeType);
    	    } catch (MimeTypeParseException e) {
    	    	throw new IllegalArgumentException(e);
    	    }
    		
    	    this.contents = theContents;
    	    this.size = theContents.length;
    	    this.fileName = theFileName;
    	    this.mimeType = theMimeType;
    	}
    	
    	/**
    	 * Adds optional field description.
    	 * @param theDescription
    	 * @return this
    	 */
    	public AttachmentBuilder description(String theDescription) {
    		this.description = theDescription;
    		return this;
    	}
    	
    	/**
    	 * Adds optional field disposition.
    	 * @param theDisposition disposition
    	 * @return this
    	 */
    	public AttachmentBuilder disposition(String theDisposition) {
    		this.disposition = theDisposition;
    		return this;
    	}
    	
    	/**
    	 * Returns constructed object. This is effectively
    	 * immutable other than by reflection tricks.
    	 * @return
    	 */
    	public SimpleAttachment build() {
    		return new SimpleAttachment(this);
    	}
    }

}
