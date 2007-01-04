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
	result = PRIME * result
		+ ((description == null) ? 0 : description.hashCode());
	result = PRIME * result
		+ ((disposition == null) ? 0 : disposition.hashCode());
	result = PRIME * result
		+ ((fileName == null) ? 0 : fileName.hashCode());
	result = PRIME * result
		+ ((mimeType == null) ? 0 : mimeType.hashCode());
	result = PRIME * result + size;
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

}
