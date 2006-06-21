package org.juenger.wiki.dao;

import java.util.List;

import org.juenger.wiki.WikiException;
import org.juenger.wiki.item.Attachment;
import org.juenger.wiki.item.Page;
import org.juenger.wiki.item.VersionedName;

/**
 * Performs all access operations regarding pages and attachments, including
 * loading and persistence.
 * 
 * @author jking
 */
public interface WikiItemDAO {
    
    /**
     * Retrieves the latest version of the named page if it exists,
     * else <code>null</null>.
     * 
     * @param name
     * @return latest version of page 
     */
    public Page getPage(String name);
    
    /**
     * Returns latest versions of all available pages. If none are 
     * available, returns an empty list. The list will be sorted
     * by page name.
     * 
     * @return list of all pages in the latest version
     */
    public List<Page> getAllPages();

    /**
     * Returns all versions of the page. If there are no previous or subsequent
     * versions,then an empty list is returned. The list will be sorted by version
     * number, starting with the most recent.
     * 
     * <p>Note, it is not envisaged that previous versions be cached aggressively.</p>
     * 
     * @param page
     * @return list of page versions
     */
    public List<Page> getAllVersions(Page page);

    /**
     * Returns all versions of the named page. If none are available because the
     * page does not exist, returns an empty list. The list will be sorted by
     * version number, starting with the most recent.
     * 
     * @param pageName
     * @return version history of named page 
     */
    public List<Page> getAllVersions(String pageName);

    /**
     * Returns the specified version of the page named. If the page or the
     * specified version cannot be found, returns <code>null</null>.
     * 
     * @param page
     * @param version
     * @return specific version of the page
     */
    public Page getPageVersion(Page page, int version);
    
    /**
     * Returns the page named in the version specified. If the page
     * or the specified version cannot be found, returns <code>null</null>.
     * 
     * @param versionedName
     * @return page
     */
    public Page getPageVersion(VersionedName versionedName);
    
    /**
     * Returns the latest version of the given page.
     * 
     * @param page
     * @return latest version
     */
    public Page getLatestVersion(Page page);
    
    /*
     * PAGE AND ATTACHMENT LIFECYCLE METHODS
     */
    
    /**
     * Adds the page to the persistent store.
     * 
     * @param page
     * @throws WikiException if a page or pages with the 
     *  supplied name already exists, if the version number
     *  of the page is not 0.  
     */
    public void addNewPage(Page page) throws WikiException;
    
    /**
     * Deletes the page in its entirety, including all versions and all
     * attachments and their own version history.
     * 
     * @param pageToDelete
     * @throws WikiException if the page cannot be located in the persistent
     * store
     */
    public void deletePage(Page pageToDelete) throws WikiException;
    
    /**
     * Deletes the page in its entirety, including all versions and all
     * attachments and their own version history.
     * 
     * @param pageName
     * @throws WikiException if there is no such named page in the 
     * persistent store
     */
    public void deletePage(String pageName) throws WikiException;

    /**
     * Persists the new version of the page. The version must be one
     * above the current highest version in the store. It must have
     * new text.
     * 
     * @param newVersion
     * @throws WikiException if the version number is not one above
     * the current most recent version, if a page with the same name
     * is not in the persistent store.
     */
    public void addUpdatedPageWithNewText(Page newVersion) throws WikiException;

    /**
     * Persists the new version of the page along with the new attachment.
     * The version must be one above the current highest version in the
     * store. The attachment must be new (Version 0) and an attachment with 
     * the same name must not already be associated with the page.
     * 
     * @param newVersion
     * @param attachment
     * @throws WikiException
     */
    public void addUpdatedPageWithNewAttachment(Page newVersion, Attachment attachment) throws WikiException;

    /**
     * Persists the new version of the page along with the new attachment
     * version. The version of the page must be one above the current highest
     * version in the store. Likewise, the version of the attachment must be
     * one above the current highest version in the store.
     * 
     * @param newVersion
     * @param newAttachment
     * @throws WikiException
     */
    public void addUpdatedPageWithUpdatedAttachment(Page newVersion, Attachment newAttachment) throws WikiException;

    /**
     * Persists the new version of the page without details of the deleted 
     * attachment. The version of the page must be one above the current 
     * highest version in the store. The attachment to be deleted must
     * be the latest version of the named attachment and must have
     * been attached to the current highest version of the page.  
     * 
     * @param page
     * @param attachmentToDelete
     * @throws WikiException 
     */
    public void addUpdatedPageWithAttachmentRemoved(Page page, Attachment attachmentToDelete) throws WikiException;

    /**
     * States whether the given page instance is the latest version
     * of the page.
     * 
     * @param page
     * @return true if page is the latest version, else false.
     * @throws WikiException if there is no such page in the store
     */
    public boolean isLatestPageVersion(Page page) throws WikiException;

    /**
     * States whether a page with the corresponding name exists
     * in the persistent store.
     * 
     * @param pageName
     * @return true if it exists, else false
     */
    public boolean pageExists(String pageName);

    /**
     * States whether a particular version of the page exists in
     * the persistent store.
     * 
     * @param versionedName
     * @return true if the page version exists, else false
     */
    public boolean pageVersionExists(VersionedName versionedName);
   
}
