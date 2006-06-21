/*
 * juenger wiki - a JSP Wiki clone
 * 
 * Copyright (C) 2006 John King 
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.juenger.wiki.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.juenger.wiki.WikiException;
import org.juenger.wiki.dao.WikiItemDAO;
import org.juenger.wiki.item.Attachment;
import org.juenger.wiki.item.Page;
import org.juenger.wiki.item.VersionedName;
import org.juenger.wiki.service.PageLock;
import org.juenger.wiki.service.PageService;
import org.juenger.wiki.service.MimeTypeService;
import org.juenger.wiki.service.PageEventListener;
import org.juenger.wiki.service.PageServiceEventListener;

/**
 * <p>It is derived in the large part from the JSPWiki APIs but significantly
 * simplified to make it easier to understand and use. It is quite beyond
 * me why the JSP WikiEngine and its collection of assorted managers should
 * be so horribly entangled and complex.</p>
 * 
 * <p>It is envisaged that implementations will be singletons managed by 
 * the Spring bean container.</p>
 * 
 * <p>Particular attention must be paid to thread safety without compromising speed.
 * This should be made easier if we keep Pages and their Attachments immutable
 * once constructed from their persistent store.</p>
 * 
 * <p>In contrast to the WikiEngine, this model envisages that additional managers 
 * which react to events will be decoupled by implementing the PageEventListener
 * interface.</p>
 * 
 * @author jejking
 */
public final class DefaultPageService implements PageService {

    private static PageService INSTANCE = new DefaultPageService();
    private WikiItemDAO wikiItemDao;
    private MimeTypeService mimeTypeService;
    
    private boolean initialized = false;
    
    
    private DefaultPageService() {
        super();
    }
    
    public void setWikiItemDAO(WikiItemDAO wikiItemDAO) {
        this.wikiItemDao = wikiItemDAO;
        if (mimeTypeService != null) {
            this.initialized = true;
        }
    }
    
    public void setMimeTypeService(MimeTypeService mimeTypeService) {
        this.mimeTypeService = mimeTypeService;
        if (wikiItemDao != null) {
            this.initialized = true;
        }
    }
    
    public static PageService getInstance() {
        return DefaultPageService.INSTANCE;
    }
    
    /*
     * GETTERS
     */
	
    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#getPage(java.lang.String)
     */
    public Page getPage(String name) {
        if (!initialized) {
            throw new IllegalStateException("not initialized");
        }
        return wikiItemDao.getPage(name);
    }
    
    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#getAllPages()
     */
    public List<Page> getAllPages() {
        if (!initialized) {
            throw new IllegalStateException("not initialized");
        }
        
        return wikiItemDao.getAllPages();
    }

    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#getAllVersions(org.juenger.wiki.Page)
     */
    public List<Page> getAllVersions(Page page) {
        if (!initialized) {
            throw new IllegalStateException("not initialized");
        }
        return wikiItemDao.getAllVersions(page);
    }

    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#getAllVersions(java.lang.String)
     */
    public List<Page> getAllVersions(String pageName) {
        if (!initialized) {
            throw new IllegalStateException("not initialized");
        }
        return wikiItemDao.getAllVersions(pageName);
    }
    

    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#getPageVersion(org.juenger.wiki.Page, int)
     */
    public Page getPageVersion(Page page, int version) {
        if (!initialized) {
            throw new IllegalStateException("not initialized");
        }
        return wikiItemDao.getPageVersion(page, version);
    }
    
    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#getPageVersion(org.juenger.wiki.VersionedName)
     */
    public Page getPageVersion(VersionedName versionedName) {
        if (!initialized) {
            throw new IllegalStateException("not initialized");
        }
        return wikiItemDao.getPageVersion(versionedName);
    }
    
    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#getLatestVersion(org.juenger.wiki.Page)
     */
    public Page getLatestVersion(Page page) {
        if (!initialized) {
            throw new IllegalStateException("not initialized");
        }
        return wikiItemDao.getLatestVersion(page);
    }
    
    /*
     * PAGE AND ATTACHMENT LIFECYCLE METHODS
     */
    
    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#createNewPage(java.lang.String, java.lang.String, java.lang.String)
     */
    public Page createNewPage(String author, String pageName, String text) throws
        WikiException {
        if (!initialized) {
            throw new IllegalStateException("not initialized");
        }
        Page page = new Page(author, pageName, text);
        wikiItemDao.addNewPage(page);
        return page;
    }
    
    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#deletePage(org.juenger.wiki.Page)
     */
    public void deletePage(Page pageToDelete) throws WikiException {
        if (!initialized) {
            throw new IllegalStateException("not initialized");
        }
        wikiItemDao.deletePage(pageToDelete);
    }
    
    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#deletePage(java.lang.String)
     */
    public void deletePage(String pageName) throws WikiException {
        if (!initialized) {
            throw new IllegalStateException("not initialized");
        }
        wikiItemDao.deletePage(pageName);
    }

    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#updatePageText(org.juenger.wiki.Page, java.lang.String, java.lang.String)
     */
    public Page updatePageText(Page existingPage, String editedBy, String newText)
    	throws WikiException {
        if (!initialized) {
            throw new IllegalStateException("not initialized");
        }
        Page newVersion = new Page(existingPage, editedBy,  newText);
        wikiItemDao.addUpdatedPageWithNewText(newVersion);
        return newVersion;
    }
    
    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#createAttachmentOnPage(org.juenger.wiki.Page, java.lang.String, java.lang.String, java.lang.String, byte[])
     */
    public Page createAttachmentOnPage(Page page, String attachmentAuthor, 
    		String attachmentName, String mimeType, byte[] contents) throws WikiException {
        if (!initialized) {
            throw new IllegalStateException("not initialized");
        }
        
        Attachment attachment = new Attachment(attachmentAuthor, attachmentName, mimeType, contents);
        Set<VersionedName> newAttachmentNames = new HashSet<VersionedName>(page.getAttachmentVersionedNames());
        newAttachmentNames.add(attachment.getVersionedName());
        Page newVersion = new Page(page, attachmentAuthor, newAttachmentNames);
        wikiItemDao.addUpdatedPageWithNewAttachment(newVersion, attachment);
        return newVersion;
    }
    
    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#createAttachmentOnPage(org.juenger.wiki.Page, java.lang.String, java.lang.String, byte[])
     */
    public Page createAttachmentOnPage(Page page, String attachmentAuthor,
            String attachmentName, byte[] contents) throws WikiException {
        if (!initialized) {
            throw new IllegalStateException("not initialized");
        }
        String mimeType = mimeTypeService.getMimeType(attachmentName);
        Attachment attachment = new Attachment(attachmentAuthor, attachmentName, mimeType, contents);
        Set<VersionedName> newAttachmentNames = new HashSet<VersionedName>(page.getAttachmentVersionedNames());
        newAttachmentNames.add(attachment.getVersionedName());
        Page newVersion = new Page(page, attachmentAuthor, newAttachmentNames);
        wikiItemDao.addUpdatedPageWithNewAttachment(newVersion, attachment);
        return newVersion;
    }

    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#updateAttachmentOnPage(org.juenger.wiki.Page, org.juenger.wiki.Attachment, java.lang.String, byte[])
     */
    public Page updateAttachmentOnPage(Page page, Attachment attachmentToUpdate, 
            String editedBy, byte[] newContents) throws WikiException {
        if (!initialized) {
            throw new IllegalStateException("not initialized");
        }
        Attachment newAttachment = new Attachment(attachmentToUpdate, editedBy, newContents);
        Set<VersionedName> newAttachmentNames = new HashSet<VersionedName>(page.getAttachmentVersionedNames());
        newAttachmentNames.add(newAttachment.getVersionedName());
        Page newVersion = new Page(page, editedBy, newAttachmentNames);
        wikiItemDao.addUpdatedPageWithUpdatedAttachment(newVersion, newAttachment);
        return newVersion;
    }

    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#deleteAttachment(org.juenger.wiki.Page, java.lang.String, org.juenger.wiki.Attachment)
     */
    public Page deleteAttachment(Page page, String editedBy, Attachment attachmentToDelete) throws WikiException {
        if (!initialized) {
            throw new IllegalStateException("not initialized");
        }
        Set<VersionedName> newAttachmentNames = new HashSet<VersionedName>(page.getAttachmentVersionedNames());
        if (newAttachmentNames.remove(attachmentToDelete.getVersionedName())) {
            Page newVersion = new Page(page, editedBy, newAttachmentNames);
            wikiItemDao.addUpdatedPageWithAttachmentRemoved(page, attachmentToDelete);
            return newVersion;
        }
        else {
            throw new WikiException("page " + page + " does not actually contain the attachment " + attachmentToDelete);
        }
    }

    /*
     * PAGE LOCKING 
     */
    
    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#lockPage(java.lang.String, java.lang.String)
     */
    public PageLock lockPage(String editor, String pageName) {
        // TODO
        return null;
    }
    
    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#lockPage(java.lang.String, java.lang.String, int)
     */
    public PageLock lockPage(String editor, String pageName, int seconds) throws WikiException {
        // TODO
        return null;
    }

    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#unlockPage(java.lang.String, java.lang.String)
     */
    public void unlockPage(String editor, String pageName) throws WikiException {
        // TODO
    }
    
    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#getPageLock(java.lang.String)
     */
    public PageLock getPageLock(String pageName) throws WikiException {
        // TODO
        return null;
    }
    
    /*
     * PAGE INFORMATION
     */
    
    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#isLatestVersion(org.juenger.wiki.Page)
     */
    public boolean isLatestVersion(Page page) throws WikiException {
        return wikiItemDao.isLatestPageVersion(page);
    }
    
    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#pageExists(java.lang.String)
     */
    public boolean pageExists(String pageName) {
        return wikiItemDao.pageExists(pageName);
    }

    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#pageVersionExists(org.juenger.wiki.VersionedName)
     */
    public boolean pageVersionExists(VersionedName versionedName) {
        return wikiItemDao.pageVersionExists(versionedName);
    }
    
    /*
     * PAGE EVENT LISTENERS
     */
    
    /**
     * Registers the listener to receive notification of events.
     * 
     * @param listener
     */
    public void registerPageEventListener(PageEventListener listener) {
        //TODO
    }
    
    /**
     * Removes the listener.
     * 
     * @param listener
     */
    public void deregisterPageEventListener(PageEventListener listener) {
       // TODO
    }
    
    /**
     * Registers all the listeners to receive notification of events.
     * 
     * @param listeners
     */
    public void registerPageEventListeners(List<PageEventListener> listeners) {
        // TODO
    }
    
    
    /*
     * SERVICE LIFECYCLE 
     */
    
    
    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#start()
     */
    public void start() throws WikiException {
        // TODO
    }
    
    /* (non-Javadoc)
     * @see org.juenger.wiki.service.impl.IPageService#stop()
     */
    public void stop() throws WikiException {
        // TODO
    }
    
    public void registerPageServiceEventListener(PageServiceEventListener listener) {
        // TODO
    }
    
    public void registerPageServiceEventListeners(List<PageServiceEventListener> listeners) {
        // TODO
    }
    
    public void deregisterPageServiceEventListener(PageServiceEventListener listener) {
        // TODO
    }
    
}