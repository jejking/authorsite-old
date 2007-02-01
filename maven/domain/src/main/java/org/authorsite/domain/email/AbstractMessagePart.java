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
package org.authorsite.domain.email;

/**
 * Class grouping properties which can be shared between
 * text and binary message parts.
 * 
 * @author jejking
 *
 */
public abstract class AbstractMessagePart extends AbstractEmailPart {

    private MessagePartContainer parent;

    protected String mimeType;

    protected String description;

    protected String disposition;

    protected String fileName;

    protected int size;

    /**
     * Default constructor.
     */
    public AbstractMessagePart() {
	super();
    }

    /**
     * @return the description
     */
    public String getDescription() {
	return this.description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * @return the disposition
     */
    public String getDisposition() {
	return this.disposition;
    }

    /**
     * @param disposition the disposition to set
     */
    public void setDisposition(String disposition) {
	this.disposition = disposition;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
	return this.fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    /**
     * @return the mimeType
     */
    public String getMimeType() {
	return this.mimeType;
    }

    /**
     * @param mimeType the mimeType to set
     */
    public void setMimeType(String mimeType) {
	this.mimeType = mimeType;
    }

    /**
     * @return the parent
     */
    public MessagePartContainer getParent() {
	return this.parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(MessagePartContainer parent) {
	this.parent = parent;
    }

    /**
     * @return the size
     */
    public int getSize() {
	return this.size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
	this.size = size;
    }

    @Override
    public int hashCode() {
	final int PRIME = 31;
	int result = 1;
	result = PRIME * result + ((this.description == null) ? 0 : this.description.hashCode());
	result = PRIME * result + ((this.disposition == null) ? 0 : this.disposition.hashCode());
	result = PRIME * result + ((this.fileName == null) ? 0 : this.fileName.hashCode());
	result = PRIME * result + ((this.mimeType == null) ? 0 : this.mimeType.hashCode());
	result = PRIME * result + this.size;
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
	final AbstractMessagePart other = (AbstractMessagePart) obj;
	if (this.description == null) {
	    if (other.description != null)
		return false;
	} else if (!this.description.equals(other.description))
	    return false;
	if (this.disposition == null) {
	    if (other.disposition != null)
		return false;
	} else if (!this.disposition.equals(other.disposition))
	    return false;
	if (this.fileName == null) {
	    if (other.fileName != null)
		return false;
	} else if (!this.fileName.equals(other.fileName))
	    return false;
	if (this.mimeType == null) {
	    if (other.mimeType != null)
		return false;
	} else if (!this.mimeType.equals(other.mimeType))
	    return false;
	if (this.size != other.size)
	    return false;
	return true;
    }

}
