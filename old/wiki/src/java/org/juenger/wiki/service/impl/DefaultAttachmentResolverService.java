/**
 * 
 */
package org.juenger.wiki.service.impl;

import java.io.InputStream;
import java.util.Set;

import org.juenger.wiki.WikiException;
import org.juenger.wiki.dao.AttachmentResolverDAO;
import org.juenger.wiki.item.Attachment;
import org.juenger.wiki.item.Page;
import org.juenger.wiki.item.VersionedName;
import org.juenger.wiki.service.AttachmentResolverService;

/**
 * Offers services to {@link Page} instances to load
 * the actual attachments from the {@link VersionedName}s
 * they use. Furthermore, also facilitates a lazy loading
 * service for {@link Attachment} instances so that they
 * do not have to hold onto references to their binary
 * content.
 * 
 * @author jejking
 */
public class DefaultAttachmentResolverService implements AttachmentResolverService {

	private static AttachmentResolverService instance;
	private boolean initialized = false;
	
	private AttachmentResolverDAO dao;
	
	/**
	 * Private default constructor. We only want one 
	 * instance of the object.
	 */
	private DefaultAttachmentResolverService() {
		super();
	}
	
	/**
	 * Static factory method.
	 * 
	 * <p>The idea is that although Spring eschews singletons, the 
	 * instance can nevertheless be set up through Dependency injection
	 * before other classes get to use the instance. This should
	 * remove dependencies on Spring from our domain objects.
	 * 
	 * @return the instance.
	 */
	public static AttachmentResolverService getInstance() {
		return instance;
	}
	
	/* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IAttachmentResolverService#setAttachmentDAO(org.juenger.wiki.dao.AttachmentResolverDAO)
     */
	public void setAttachmentDAO(AttachmentResolverDAO dao) {
		this.dao = dao;
		initialized = true;
	}
	
	/* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IAttachmentResolverService#resolveAttachment(org.juenger.wiki.Page, org.juenger.wiki.VersionedName)
     */
	public Attachment resolveAttachment(Page page, String attachmentName) throws WikiException {
		if (!initialized) {
			throw new IllegalStateException("not initialized");
		}
		return dao.getAttachment(page, attachmentName);
	}
	
	/* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IAttachmentResolverService#getAttachmentContentAsStream(org.juenger.wiki.Page, org.juenger.wiki.VersionedName)
     */
	public InputStream getAttachmentContentAsStream(Page page, VersionedName versionedName) {
		if (!initialized) {
			throw new IllegalStateException("not initialized");
		}
		return dao.getAttachmentContentAsStream(page, versionedName);
	}
	
	/* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IAttachmentResolverService#getAttachmentContentAsStream(org.juenger.wiki.Attachment)
     */
	public InputStream getAttachmentContentAsStream(Attachment attachment) {
		if (!initialized) {
			throw new IllegalStateException("not initialized");
		}
		return dao.getAttachmentContentAsStream(attachment);
	}

	/* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IAttachmentResolverService#resolveAttachments(org.juenger.wiki.Page)
     */
	public Set<Attachment> resolveAttachments(Page page) throws WikiException {
		if (!initialized) {
			throw new IllegalStateException("not initialised");
		}
		return dao.getAttachments(page);
	}

    public Attachment resolveAttachment(String pageName, VersionedName attachmentVersionedName) throws WikiException {
        if (! initialized) {
            throw new IllegalStateException("not initialised");
        }
        return dao.getAttachment(pageName, attachmentVersionedName);
    }
	
}
