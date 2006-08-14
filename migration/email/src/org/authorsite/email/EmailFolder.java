package org.authorsite.email;

import java.util.ArrayList;
import java.util.List;

import org.authorsite.email.db.EmailVisitor;

public final class EmailFolder extends AbstractMailItem {

	public static final EmailFolder ROOT = new EmailFolder("/");
	
	static {
		ROOT.setId(0);
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

	public String toString() {
		return "Folder: name = " + name + ", Parent: " + parent + ", ID: " + super.getId();
	}
	
	@Override
	public void acceptEmailVisitor(EmailVisitor visitor) {
		visitor.visit(this);
	}

}
