package org.authorsite.email;

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
	
	
}
