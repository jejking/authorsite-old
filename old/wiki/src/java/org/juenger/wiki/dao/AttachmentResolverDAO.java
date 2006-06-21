package org.juenger.wiki.dao;

import java.io.InputStream;
import java.util.Set;

import org.juenger.wiki.WikiException;
import org.juenger.wiki.item.Attachment;
import org.juenger.wiki.item.Page;
import org.juenger.wiki.item.VersionedName;

/**
 * Defines functionality for fetching attachments to a specified page.
 * 
 * @author jking
 */
public interface AttachmentResolverDAO {

    /**
     * Returns the latest attachment version on the page version corresponding
     * to the attachment name parameter.
     * 
     * @param page
     * @param attachmentName
     * @return Attachment
     * @throws WikiException if the page does not exist, the page does not an attachment
     * with the supplied name
     */
    public Attachment getAttachment(Page page, String attachmentName) throws WikiException;
    
	/**
     * Returns the specified version of the attachment on the named page.
     * 
	 * @param pageName
	 * @param versionedName
	 * @return Attachment
	 * @throws WikiException if the page does not exist, the specified attachment versioned name 
     * is not attached a version of the page in question
	 */
	public Attachment getAttachment(String pageName, VersionedName versionedName) throws WikiException;

    /**
     * Returns the content of the latest version of the named attachment
     * as a stream. 
     * 
     * @param page
     * @param attachmentName
     * @return content as stream
     */
    public InputStream getAttachmentContentAsStream(Page page, String attachmentName);
    
	/**
     * Returns the content of the specified version of the named 
     * attachment as a stream. 
     * 
	 * @param page
	 * @param versionedName
	 * @return content as stream
	 */
	public InputStream getAttachmentContentAsStream(Page page, VersionedName versionedName);
    

	/**
     * Returns the content of the specified attached as a stream. This is 
     * particulary useful when dealing with a lazy attachment which
     * has only loaded its metadata and not its content into RAM.
     * 
	 * @param attachment
	 * @return content as stream
	 */
	public InputStream getAttachmentContentAsStream(Attachment attachment);

	/**
     * Returns a set of the latest attachment versions for the page.
     * 
	 * @param page
	 * @return set of a attachments
	 * @throws WikiException if the page does not exist in the store
	 */
	public Set<Attachment> getAttachments(Page page) throws WikiException;

}
