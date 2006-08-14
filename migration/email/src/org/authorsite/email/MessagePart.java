package org.authorsite.email;

public abstract class MessagePart extends AbstractEmailPart {
	
	private MessagePartContainer parent;
	protected String mimeType;
	protected String description;
	protected String disposition;
	protected String fileName;
	protected int size;
	
	
	public MessagePart() {
		super();
	}
	
	public MessagePart(String mimeType) {
		super();
		this.mimeType = mimeType;
	}
	
		public String getMimeType() {
		return mimeType;
	}
	
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	public MessagePartContainer getParent() {
		return parent;
	}
	
	public void setParent(MessagePartContainer parent) {
		this.parent = parent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisposition() {
		return disposition;
	}

	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((description == null) ? 0 : description.hashCode());
		result = PRIME * result + ((disposition == null) ? 0 : disposition.hashCode());
		result = PRIME * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = PRIME * result + ((mimeType == null) ? 0 : mimeType.hashCode());
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
		final MessagePart other = (MessagePart) obj;
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
