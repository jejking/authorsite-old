package org.authorsite.email;

import java.util.ArrayList;
import java.util.List;

public class EmailFolder extends AbstractMailItem {

	private String name;
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
	
}
