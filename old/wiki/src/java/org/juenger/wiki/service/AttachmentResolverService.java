package org.juenger.wiki.service;

import java.io.InputStream;
import java.util.Set;

import org.juenger.wiki.WikiException;
import org.juenger.wiki.dao.AttachmentResolverDAO;
import org.juenger.wiki.item.Attachment;
import org.juenger.wiki.item.Page;
import org.juenger.wiki.item.VersionedName;

public interface AttachmentResolverService {

    /**
     * Injects attachment dao. (For use in testing or by IoC container
     * only.
     * 
     * @param dao
     */
    public void setAttachmentDAO(AttachmentResolverDAO dao);

    public Attachment resolveAttachment(Page page, String attachmentName)
            throws WikiException;
    
    public Attachment resolveAttachment(String pageName, VersionedName attachmentVersionedName) 
        throws WikiException;

    public InputStream getAttachmentContentAsStream(Page page,
            VersionedName versionedName);

    public InputStream getAttachmentContentAsStream(Attachment attachment);

    public Set<Attachment> resolveAttachments(Page page) throws WikiException;

}