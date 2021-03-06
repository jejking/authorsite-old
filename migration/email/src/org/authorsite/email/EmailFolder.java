package org.authorsite.email;

import java.util.ArrayList;
import java.util.List;

public class EmailFolder extends AbstractMailItem {

	private static final class RootFolder extends EmailFolder {

		@Override
		public String getName() {
			return ("/");
		}

		@Override
		public EmailFolder getParent() {
			return null;
		}

		@Override
		public String getPath() {
			return "/";
		}

		@Override
		public void setName(String name) {
			// do nothing
		}

		@Override
		public void setParent(EmailFolder parent) {
			// do nothing
		}

		@Override
		public String toString() {
			return "Root Folder";
		}

	}
	
	public static final EmailFolder ROOT = new RootFolder();
	
	static {
		ROOT.setId(1);
	}
	
	private String name;
	private EmailFolder parent;
	private List<EmailFolder> childFolders = new ArrayList<EmailFolder>();
	private List<EmailMessage> childMessages = new ArrayList<EmailMessage>();
	
	public EmailFolder() {
		super();
	}
	
	public EmailFolder(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addEmailFolder(EmailFolder folder) {
		assert folder != null;
		if ( !childFolders.contains(folder)) {
			childFolders.add(folder);
		}
	}
	
	public void addEmailMessage(EmailMessage message) {
		assert message != null;
		if ( ! childMessages.contains(message)) {
			childMessages.add(message);
		}
	}

	public List<EmailFolder> getChildFolders() {
		return childFolders;
	}

	public List<EmailMessage> getChildMessages() {
		return childMessages;
	}

	public EmailFolder getParent() {
		return parent;
	}

	public void setParent(EmailFolder parent) {
		this.parent = parent;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result + ((parent == null) ? 0 : parent.hashCode());
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
		final EmailFolder other = (EmailFolder) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}

	public String getPath() {
		StringBuilder buffer = new StringBuilder();
		if ( parent == null) {
			parent = EmailFolder.ROOT;
		}
		buffer.insert(0, this.parent.getPath());
		if (parent != EmailFolder.ROOT) {
			buffer.append("/");
		}
		buffer.append(this.name);
		return buffer.toString();
	}
	
	public String toString() {
		return "Folder: name = " + name + ", Parent: " + parent + ", ID: " + super.getId();
	}
	
}
