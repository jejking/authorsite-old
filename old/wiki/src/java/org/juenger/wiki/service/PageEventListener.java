package org.juenger.wiki.service;

import org.juenger.wiki.item.Attachment;
import org.juenger.wiki.item.Page;

public interface PageEventListener {

	public void pageViewed(Page pageViewed);
	
	public void pageAdded(Page newPage);
	
	public void pageUpdated(Page updatedPage);
	
	public void pageDeleted(Page deletedPage);
	
	public void attachmentAdded(Page page, Attachment newAttachment);
	
	public void attachmentUpdated(Page page, Attachment updatedAttachment);
	
	public void attachmentDeleted(Page page, Attachment deletedAttachment);
	
}
