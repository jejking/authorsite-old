package org.authorsite.domain.email;

import java.util.ArrayList;
import java.util.List;

import org.authorsite.domain.AbstractEntry;

/**
 * Class representing a folder of email messages and further
 * sub-folders.
 * 
 * @author jejking
 *
 */
public class EmailFolder extends AbstractEntry {

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

    /**
     * The root email folder.
     */
    public static final EmailFolder ROOT = new RootFolder();

    static {
	ROOT.setId(-1);
    }

    private String name;

    private EmailFolder parent;

    private List<EmailFolder> childFolders = new ArrayList<EmailFolder>();

    private List<EmailMessage> childMessages = new ArrayList<EmailMessage>();

    /**
     * Default constructor.
     */
    public EmailFolder() {
	super();
    }

    /**
     * Constructs folder using the provided name.
     * 
     * @param name may not be <code>null</code>.
     * @throws IllegalArgumentException if name is <code>null</code>.
     */
    public EmailFolder(String name) {
	this.setName(name);
	this.name = name;
    }

    /**
     * Gets name.
     * 
     * @return name.
     */
    public String getName() {
	return name;
    }

    /**
     * Sets name.
     * 
     * @param name may not be <code>null</code>.
     * @throws IllegalArgumentException if name is <code>null</code>.
     */
    public void setName(String name) {
	if ( name == null ) {
	    throw new IllegalArgumentException("name cannot be null");
	}
	this.name = name;
    }

    /**
     * Adds child email folder.
     * 
     * @param folder may not be <code>null</code>
     * @throws IllegalArgumentException if name is <code>null</code>.
     */
    public void addEmailFolder(EmailFolder folder) {
	if ( folder == null ) {
	    throw new IllegalArgumentException("folder cannot be null");
	}
	if (!childFolders.contains(folder)) {
	    childFolders.add(folder);
	}
    }

    /**
     * Adds message to folder.
     * 
     * @param message may not be <code>null</code>.
     * @throws IllegalArgumentException if param is <code>null</code>.
     */
    public void addEmailMessage(EmailMessage message) {
	if ( message == null ) {
	    throw new IllegalArgumentException("message cannot be null");
	}
	if (!childMessages.contains(message)) {
	    childMessages.add(message);
	}
    }

    /**
     * Gets list of child folders.
     * 
     * @return list of child folders.
     */
    public List<EmailFolder> getChildFolders() {
	return childFolders;
    }

    /**
     * Return list of messages in the folder.
     * 
     * @return list of child messages.
     */
    public List<EmailMessage> getChildMessages() {
	return childMessages;
    }

    /**
     * Gets parent folder.
     * 
     * @return the folder the instance belongs to
     */
    public EmailFolder getParent() {
	return parent;
    }

    /**
     * Sets parent folder.
     * 
     * @param parent
     */
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

    /**
     * Constructs string representation of folder path using
     * '/' as delimiter. The method accesses the parent 
     * hierarchy to the <code>ROOT</code> folder.
     * 
     * @return path representation.
     */
    public String getPath() {
	StringBuilder buffer = new StringBuilder();
	if (parent == null) {
	    parent = EmailFolder.ROOT;
	}
	buffer.insert(0, this.parent.getPath());
	if (parent != EmailFolder.ROOT) {
	    buffer.append("/");
	}
	buffer.append(this.name);
	return buffer.toString();
    }

    @Override
    public String toString() {
	return "Folder: name = " + name + ", Parent: " + parent + ", ID: "
		+ super.getId();
    }

}
