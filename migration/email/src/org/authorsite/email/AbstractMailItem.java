package org.authorsite.email;

import org.authorsite.email.db.EmailVisitor;

public abstract class AbstractMailItem {

	private long id;
	
	public AbstractMailItem() {
		super();
	}
	
	public AbstractMailItem(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public abstract void acceptEmailVisitor(EmailVisitor visitor);
}
