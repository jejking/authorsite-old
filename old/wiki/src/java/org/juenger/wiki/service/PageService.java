package org.juenger.wiki.service;

import java.util.List;

import org.juenger.wiki.WikiException;
import org.juenger.wiki.item.Attachment;
import org.juenger.wiki.item.Page;
import org.juenger.wiki.item.VersionedName;

public interface PageService {

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
     * number, starting with the earliest.
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
     * version number, starting with the earliest.
     * 
     * @param pageName
     * @return version history of named page 
     */
    public List<Page> getAllVersions(String pageName);

    /**
     * Returns the specified version of the page. If the page or the
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
     * @return
     */
    public Page getPageVersion(VersionedName versionedName);

    /**
     * Returns the latest version of the given page. If there are no
     * versions of the page instance in the store, returns null.
     * 
     * @param page
     * @return latest version, or null.
     */
    public Page getLatestVersion(Page page);

    /**
     * Creates new page using the supplied parameters. Implementations
     * should notify their event listeners of the new page being
     * created.
     * 
     * @param author
     * @param pageName
     * @param text
     * @return new Page instance
     * @throws WikiException if Page with specified name already exists
     */
    public Page createNewPage(String author, String pageName, String text)
            throws WikiException;

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
     * Deletes the named page in its entirety, including all versions and all
     * attachments and their own version history.
     * 
     * @param pageName
     * @throws WikiException if there is no such named page in the 
     * persistent store
     */
    public void deletePage(String pageName) throws WikiException;

    /**
     * Creates new version of page with the updated text and writes the new
     * version to the persistent store.
     * @param existingPage
     * @param editedBy
     * @param newText
     * 
     * @return new page instance with updated text
     * @throws WikiException if the existingPage is not the latest version of
     * the page
     */
    public Page updatePageText(Page existingPage, String editedBy,
            String newText) throws WikiException;

    /**
     * Creates new attachment on the specified page and thus creates
     * a new version of the page itself. The <code>attachmentAuthor</code>
     * parameter is then used as the <code>lastEditedBy</code> property of
     * the new page version. The new page version is set as the <code>parent</code>
     * of the newly created attachment.
     * 
     * @param page
     * @param attachmentAuthor 
     * @param attachmentName 
     * @param mimeType 
     * @param contents 
     * @return new version of page with attachment added
     * @throws WikiException if the page parameter is not the latest version or if
     *      an attachment with the supplied name already exists
     */
    public Page createAttachmentOnPage(Page page, String attachmentAuthor,
            String attachmentName, String mimeType, byte[] contents)
            throws WikiException;

    /**
     * Creates new attachment on the specified page, attempting to
     * identify its <code>mime-type</code> property from the supplied
     * name. A new version of the page is created. 
     * 
     * @param page
     * @param attachmentAuthor
     * @param attachmentName
     * @param contents
     * @return new version of page with attachment added
     * @throws WikiException if the page parameter is not the latest version or if
     *      an attachment with the supplied name already exists, or if the mime-type
     *      cannot sucessfully be extracted from the <code>attachmentName</code> parameter.
     */
    public Page createAttachmentOnPage(Page page, String attachmentAuthor,
            String attachmentName, byte[] contents) throws WikiException;

    /**
     * Creates a new version of the attachment and attaches it to a new
     * version of the attachment's parent page.
     * 
     * @param page 
     * @param attachmentToUpdate 
     * @param editedBy
     * @param newContents 
     * @return new version of page with updated attachment
     * @throws WikiException if the page or attachment passed in is not the latest version,
     *      or if the attachment does not actually belong to the page
     */
    public Page updateAttachmentOnPage(Page page,
            Attachment attachmentToUpdate, String editedBy, byte[] newContents)
            throws WikiException;

    /**
     * Creates a new version of the Page from which the attachment has been
     * removed. Note that the previous version history of the attachment
     * remains - previous versions of the page retain their references
     * to the previous versions of the attachment until the page as a whole
     * is deleted.
     * 
     * @param page
     * @param attachmentToDelete
     * @param editedBy
     * @return new version of page without the attachment
     * @throws WikiException if the page is not the latest version, if the attachment
     *      is not the latest version, if the attachment does not actually belong to 
     *      the page in question
     */
    public Page deleteAttachment(Page page, String editedBy,
            Attachment attachmentToDelete) throws WikiException;

    /**
     * Locks the named page for the default period. If the page is already locked,
     * the lock object will contain the name of the current editor. If it 
     * is not, the a page lock will be created with the name of the 
     * editor supplied. 
     * 
     * @param editor
     * @param pageName
     * @return page lock
     */
    public PageLock lockPage(String editor, String pageName);

    /**
     * Locks the page for the specified number of seconds. If the page is 
     * already locked, the lock object will contain the name of the current
     * editor. If it is not, the a page lock will be created with the name of the 
     * editor supplied. 
     * 
     * @param editor
     * @param pageName
     * @param seconds
     * @return page lock
     * @throws WikiException if the named page does not exist
     */
    public PageLock lockPage(String editor, String pageName, int seconds)
            throws WikiException;

    /**
     * Removes lock on named page. If the page is not locked, does nothing.
     * 
     * @param editor
     * @param pageName
     * @throws WikiException if the named page does not exist
     */
    public void unlockPage(String editor, String pageName) throws WikiException;

    /**
     * Gets the lock information on a page. If page is not locked, returns
     * <code>null</code>.
     * 
     * @param pageName
     * @return page lock or null
     * @throws WikiException if named page does not exist
     */
    public PageLock getPageLock(String pageName) throws WikiException;

    /**
     * States whether the given page instance is the latest version
     * of the page.
     * 
     * @param page
     * @return true if page is the latest version, else false.
     * @throws WikiException if there is no such page in the store
     */
    public boolean isLatestVersion(Page page) throws WikiException;

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

    /**
     * @throws WikiException
     */
    public void start() throws WikiException;

    /**
     * @throws WikiException
     */
    public void stop() throws WikiException;

}