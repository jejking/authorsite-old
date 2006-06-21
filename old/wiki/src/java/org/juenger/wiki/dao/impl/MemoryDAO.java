package org.juenger.wiki.dao.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.juenger.wiki.WikiException;
import org.juenger.wiki.dao.AttachmentResolverDAO;
import org.juenger.wiki.dao.WikiItemDAO;
import org.juenger.wiki.item.Attachment;
import org.juenger.wiki.item.Page;
import org.juenger.wiki.item.VersionedName;
import org.juenger.wiki.service.PageServiceEventListener;

/**
 * Memory based implementation for use in testing. Note, this
 * class offers no persistence and everything is held in 
 * memory structures.
 * 
 * @author jejking
 */
public final class MemoryDAO implements WikiItemDAO, AttachmentResolverDAO, PageServiceEventListener {

    /*
     * Unmodifiable constants to avoid *too* much unnecessary instantiation of empty collections
     * when we really don't need to.
     */
    private static final Set<Attachment> EMPTY_ATTACHMENT_SET = Collections.unmodifiableSet(new HashSet<Attachment>());
    private static final List<Page> EMPTY_PAGE_LIST = Collections.unmodifiableList(new ArrayList<Page>());
    
    private static final Logger LOGGER = Logger.getLogger(MemoryDAO.class);
    
    /*
     * Properties to be set by IoC container or the like.
     */
    
    private String pageNameMapFileName;
    private String attachmentMapFileName;
    
	/*
	 * Note - these two data structures have been designed at least
	 * with half an eye to ease of serialization and deserialization.
     * 
     * - on serialising, we serialise the pageNameMap, 
     *   then the attachmentMap with a custom serialisation format
     * - then we can read them in and check them for integrity
	 */
	
	/**
	 * This map is used to resolve pages by name. We can
	 * then navigate inside the list which is resolved to the
	 * name to find a particular version by directly accessing
     * the required version.
	 */
	private PageNameMap pageNameMap;
	
	/**
	 * This more complex map is used for memory based attachment
	 * resolution. 
     * 
     * <p>Attachments belong to pages. Exactly which page version has 
     * which version of an attachment is defined in the set
     * of versioned names held in the page instance. A particular
     * version of an attachment may be associated with many versions
     * of a page over time. However, we do not want to hold multiple
     * references to the actual attachment instance.</p>
     * 
     * <p>The data structure is initially keyed on the page name as is the
     * {@link MemoryDAO#pageNameMap}.</p>
     * 
     * <p>For each logical named page, we have a further map. This map 
     * is structure like the <code>pageNameMap</code>. Each attachment
     * on a page is named (the key on the map), and versioned (the location
     * in the list of versions). This structure should allow us to retrieve 
     * actual attachments rapidly and to manage the lifecycle of attachments
     * on pages correctly.</p>
	 */
	private AttachmentMap attachmentMap;
    
    /**
     * Default constructor. Initialises the internal data structures.
     */
    public MemoryDAO() {
		pageNameMap = new PageNameMap();
		attachmentMap = new AttachmentMap();
	}
    
     public synchronized void setAttachmentMapFileName(
            String attachmentNameMapFileName) {
        this.attachmentMapFileName = attachmentNameMapFileName;
    }

    public synchronized void setPageNameMapFileName(String pageNameMapFileName) {
        this.pageNameMapFileName = pageNameMapFileName;
    }

    public synchronized void pageServiceStarted() {
        // load up pageNameMap and attachmentMap from files, if set
        if (pageNameMapFileName != null) {
            File pageNameMapFile = new File(pageNameMapFileName);
            // sanity checks on the file
            if (pageNameMapFile.exists()) {
                // ok, it exists, can we read it in
                if (pageNameMapFile.canRead()) {
                    try {
                        FileInputStream pnmFileInputStream = new FileInputStream(pageNameMapFile);
                        BufferedInputStream bufferedPnmFileInputStream = new BufferedInputStream(pnmFileInputStream);
                        ObjectInputStream pnmObjectInputStream = new ObjectInputStream(bufferedPnmFileInputStream);
                        pageNameMap  = (PageNameMap) pnmObjectInputStream.readObject();
                        pnmObjectInputStream.close();
                        LOGGER.info("loaded page name map from file " + pageNameMapFileName);
                    }
                    catch (Exception e) {
                        pageNameMap = new PageNameMap();
                        LOGGER.fatal("could not read in pageNameMapFile " + pageNameMapFileName + ", using new empty map", e);
                    }
                }
                else {
                    LOGGER.warn("pageNameMapFileName property set to " + pageNameMapFileName + " but file cannot be read, using new empty map");
                }
            }
            else {
                LOGGER.warn("pageNameMapFileName property set to " + pageNameMapFileName + " but this does not exist, using new empty map");
            }
        }
        else {
            LOGGER.info("no pageNameMapFileName property set, using new empty map");
        }
        if (attachmentMapFileName != null) {
            File attmtMapFile = new File(attachmentMapFileName);
            if (attmtMapFile.exists()) {
                if (attmtMapFile.canRead()) {
                    try {
                        FileInputStream attmtMapFileInputStream = new FileInputStream(attachmentMapFileName);
                        BufferedInputStream bufferedAttmtMapFileInputStream = new BufferedInputStream(attmtMapFileInputStream);
                        ObjectInputStream attmtMapObjectInputStream = new ObjectInputStream(bufferedAttmtMapFileInputStream);
                        attachmentMap = (AttachmentMap) attmtMapObjectInputStream.readObject();
                        attmtMapObjectInputStream.close();
                        LOGGER.info("loaded attachment map from file " + attachmentMapFileName);
                    }
                    catch (Exception e) {
                        attachmentMap = new AttachmentMap();
                        LOGGER.fatal("could not read in attachmentMapFile " + attachmentMapFileName + ", using new empty map", e);
                    }
                }
                else {
                    LOGGER.warn("attachmentFileName property set to " + attachmentMapFileName + " but file cannot be read, using new empty map");
                }
            }
            else {
                LOGGER.warn("attachmentFileName property set to " + attachmentMapFileName + " but this does not exist, using new empty map");
            }
        }
        else {
            LOGGER.info("no attachmentMapFileName property set, using new empty map");
        }
        try {
            this.validateObject();
            LOGGER.info("page name map and attachment map validated, DAO ready for use");
        }
        catch (InvalidObjectException ioe) {
            // reset both maps to empty
            pageNameMap = new PageNameMap();
            attachmentMap = new AttachmentMap();
            LOGGER.fatal("data mismatch between page name map and attachment map", ioe);
        }
    }

    public synchronized void pageServiceStopped() {
        if (pageNameMapFileName != null) {
            File pageNameMapFile = new File(pageNameMapFileName);
            if (!pageNameMapFile.exists()) {
                try {
                    pageNameMapFile.createNewFile();
                } catch (IOException ioe) {
                    LOGGER.fatal("pageNameFile " + pageNameMapFileName + " does not exist and cannot be created. Map will not be persisted", ioe);
                }
            }
            //  check can write
            if (pageNameMapFile.canWrite()) {
                try {
                    FileOutputStream pnmFileOutputStream = new FileOutputStream(pageNameMapFile);
                    BufferedOutputStream bufferedPnmOutputStream = new BufferedOutputStream(pnmFileOutputStream);
                    ObjectOutputStream pnmObjectOutputStream = new ObjectOutputStream(bufferedPnmOutputStream);
                    pnmObjectOutputStream.writeObject(pageNameMap);
                    pnmObjectOutputStream.close();
                    LOGGER.info("wrote page name map successfully to " + pageNameMapFileName);
                }
                catch (Exception e) {
                    LOGGER.info("could not write page name map successfully", e);
                }
            }
            else {
                LOGGER.fatal("pageNameFile " + pageNameMapFileName + "cannot be written to. Map will not be persisted");
            }
        }
        else {
            LOGGER.info("no pageNameMapFileName property set, map will not be persisted");
        }

        if (attachmentMapFileName != null) {
            File attmtMapFile = new File(attachmentMapFileName);
            if (!attmtMapFile.exists()) {
                try {
                    attmtMapFile.createNewFile();
                }
                catch (IOException ioe) {
                    LOGGER.fatal("attachmentMapFile " + attachmentMapFileName + " does not exist and cannot be created. Map will not be persisted", ioe);
                }
            }
            if (attmtMapFile.canWrite()) {
                try {
                    FileOutputStream attmtMapFileOutputStream = new FileOutputStream(attmtMapFile);
                    BufferedOutputStream bufferedAttmtMapOutputStream = new BufferedOutputStream(attmtMapFileOutputStream);
                    ObjectOutputStream attmtMapObjectOutputStream = new ObjectOutputStream(bufferedAttmtMapOutputStream);
                    attmtMapObjectOutputStream.writeObject(attachmentMap);
                    attmtMapObjectOutputStream.close();
                    LOGGER.info("wrote attachment map successfully to " + attachmentMapFileName);
                }
                catch (Exception e) {
                    LOGGER.fatal("could not write attachment map successfully", e);
                }
            }
            else {
                LOGGER.fatal("attachmentMapFile " + attachmentMapFileName + " cannot be written to. Map will not be persisted");
            }
        }
        else {
            LOGGER.info("no attachmentMapFileName property set, map will not be persisted");
        }
    }
    
    
    /**
     * Retrieves the latest version of the named page if it exists,
     * else <code>null</null>.
     * 
     * @param name
     * @return latest version of page 
     */
    public Page getPage(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name parameter is null");
        }
        return pageNameMap.getPage(name);
    }

    /**
     * Returns list of latest page instances.
     * 
     * @return unmodifiable list of latest {@link Page} instances, 
     *  or unmodifiable empty list if there are no pages in the store
     * @see WikiItemDAO#getAllPages()
     */
    public List<Page> getAllPages() {
        if (pageNameMap.isEmpty()) {
            return MemoryDAO.EMPTY_PAGE_LIST;
        }
        return pageNameMap.getLatestPages();
    }

    /**
     * Returns all versions of the page. If there are no previous or subsequent
     * versions,then an empty list is returned. The list will be sorted by version
     * number, starting with the earliest.
     * 
     * <p>Note, it is not envisaged that previous versions be cached aggressively.</p>
     * 
     * @param page
     * @return unmodifiable list of page versions, or unmodifiable empty list
     */
    public List<Page> getAllVersions(Page page) {
        checkNotNull(page, "page");
        return this.getAllVersions(page.getVersionedName().getName());
    }

    /**
     * Returns all versions of the named page. If there are no versions
     * of the page (i.e. the page does not exist in the store), 
     * then an unmodifiable empty list is returned.
     * 
     * @param pageName name of page to get the version history of
     * @return list of page versions
     */
    public List<Page> getAllVersions(String pageName) {
        checkNotNull(pageName, "pageName");
        List<Page> pageVersions = pageNameMap.getPageVersionsList(pageName);
        if (pageVersions == null) {
            return MemoryDAO.EMPTY_PAGE_LIST;
        }
        return Collections.unmodifiableList(pageVersions);
    }

    /**
     * Returns the specified version of the page. If the page or the
     * specified version cannot be found, returns <code>null</null>.
     * 
     * @param page
     * @param version
     * @return specific version of the page, or null if not available
     */
    public Page getPageVersion(Page page, int version) {
        checkNotNull(page, "page");
        checkVersionPlausible(version);
        return pageNameMap.getPageVersion(page, version);
    }

    /**
     * @see WikiItemDAO#getPageVersion(VersionedName)
     */
    public Page getPageVersion(VersionedName versionedName) {
        checkNotNull(versionedName, "versionedName");
        return pageNameMap.getPageVersion(versionedName);
    }

    public Page getLatestVersion(Page page) {
        checkNotNull(page, "page");
        return this.getPage(page.getVersionedName().getName());
    }

    public synchronized void addNewPage(Page page) throws WikiException {
        checkNotNull(page, "page");
        checkIsFirstVersion(page);
        pageNameMap.addNewPage(page);
    }


    /**
     * Deletes the page in its entirety, including all versions and all
     * attachments and their own version history.
     * 
     * @param pageToDelete
     * @throws WikiException if the page cannot be located in the persistent
     * store
     */
    public synchronized void deletePage(Page pageToDelete) throws WikiException {
        checkNotNull(pageToDelete, "pageToDelete");
        checkPageVersionExists(pageToDelete);
        this.deletePage(pageToDelete.getVersionedName().getName());
    }

    

    public synchronized void deletePage(String pageName) throws WikiException {
        checkNotNull(pageName, "pageName");
        pageNameMap.deletePage(pageName);
        attachmentMap.remove(pageName);
    }

    /**
     *  
     * 
     * @throws WikiException if the existingPage is not the latest version of
     * the page
     */
    public synchronized void addUpdatedPageWithNewText(Page newVersion) throws WikiException {
        checkNotNull(newVersion, "newVersion");
        checkPageExists(newVersion.getVersionedName().getName());
        checkPageIsNextVersion(newVersion);
        checkIdenticalAttachmentSets(newVersion, this.getLatestVersion(newVersion));
   
        pageNameMap.addNewPageVersion(newVersion);
    }

    /**
     * Stores the new version of the page and the new attachment along with it.
     * 
     * @param newVersion the new version of the page
     * @param attachment the brand new attachment
     */
    public synchronized void addUpdatedPageWithNewAttachment(Page newVersion, Attachment attachment) throws WikiException {
        checkNotNull(newVersion, "newVersion");
        checkNotNull(attachment, "attachment");
        checkIsFirstVersion(attachment);
        checkPageExists(newVersion.getVersionedName().getName());
        checkPageIsNextVersion(newVersion);
        checkNewPageHasNewAttachmentRef(newVersion, attachment);
        checkPageDoesNotAlreadyHaveAttachment(newVersion, attachment);

        attachmentMap.addNewAttachmentForPage(newVersion, attachment);
        
        // finally, update the list of page versions
        pageNameMap.addNewPageVersion(newVersion);
        
    }

    
    public synchronized void addUpdatedPageWithUpdatedAttachment(Page newVersion, Attachment newAttachment) throws WikiException {
        checkNotNull(newVersion, "newVersion");
        checkNotNull(newAttachment, "newAttachment");
        checkPageExists(newVersion.getVersionedName().getName());
        checkPageIsNextVersion(newVersion);
        checkPageHasUpdatedAttachmentRef(newVersion, newAttachment);
        checkPageHasAttachment(newVersion, newAttachment);
        checkAttachmentIsNextVersion(newVersion, newAttachment);        
        
        // update the logical page's attachments
        attachmentMap.addNewAttachmentVersion(newVersion, newAttachment);
        
        // update the page map
        pageNameMap.addNewPageVersion(newVersion);
    }
    
    public synchronized void addUpdatedPageWithAttachmentRemoved(Page newVersion, Attachment attachmentToDelete) throws WikiException {
        checkNotNull(newVersion, "newVersion");
        checkNotNull(attachmentToDelete, "attachmentToDelete");
        checkPageExists(newVersion.getVersionedName().getName());
        checkPageIsNextVersion(newVersion);
        checkPageDoesNotHaveAttachmentToBeDeleted(newVersion, attachmentToDelete);
      
        pageNameMap.addNewPageVersion(newVersion);
    }

    public boolean isLatestPageVersion(Page page) throws WikiException {
        checkNotNull(page, "page");
        return pageNameMap.isLatestPageVersion(page);
    }

    public boolean pageExists(String pageName) {
        checkNotNull(pageName, "pageName");
        List<Page> pageVersionList = pageNameMap.getPageVersionsList(pageName);
        if (pageVersionList != null) {
            return true;
        }
        return false;
    }

    public boolean pageVersionExists(VersionedName versionedName) {
        checkNotNull(versionedName, "versionedName");
        List<Page> pageVersionList = pageNameMap.getPageVersionsList(versionedName.getName());
        if (pageVersionList == null) {
            return false;
        }
        if (versionedName.getVersion() > (pageVersionList.size() - 1) ) {
            return false;
        }
        return true;
    }

    public Attachment getAttachment(Page page, String attachmentName) throws WikiException {
        checkNotNull(page, "page");
        checkNotNull(attachmentName, "attachmentName");
        checkPageExists(page.getVersionedName().getName());
        checkPageHasRefToNamedAttachment(page, attachmentName);
        checkPageHasAttachment(page, attachmentName);
        return attachmentMap.getLatestAttachment(page, attachmentName);
    }

    public Attachment getAttachment(String pageName, VersionedName versionedName) throws WikiException {
        checkNotNull(pageName, "page");
        checkNotNull(versionedName, "versionedName");
        checkPageExists(pageName);
        
        Attachment attmt = attachmentMap.getAttachment(pageName, versionedName);
        if (attmt == null) {
            throw new WikiException("no such attachment");
        }
        return attmt;
    }
    

    public Set<Attachment> getAttachments(Page page) throws WikiException {
        checkNotNull(page, "page");
        checkPageVersionExists(page);
        // if there are no attachments on the page instance, return empty set - don't even bother looking!
        if (page.getAttachmentVersionedNames().isEmpty()) {
            return MemoryDAO.EMPTY_ATTACHMENT_SET;
        }
        return attachmentMap.getAttachments(page);
    }


    public InputStream getAttachmentContentAsStream(Attachment attachment) {
        throw new UnsupportedOperationException("need to implement lazy attachments first...");       
    }
    
    
    public InputStream getAttachmentContentAsStream(Page page, VersionedName versionedName) {
        throw new UnsupportedOperationException("need to implement lazy attachments first...");
    }

    
    public InputStream getAttachmentContentAsStream(Page page, String attachmentName) {
        throw new UnsupportedOperationException("need to implement lazy attachments first...");
    }
    
	/*
     * PRIVATE HELPER METHODS 
	 */
    
    private void checkNotNull(Object param, String paramName) {
        if (param == null) {
            throw new IllegalArgumentException(paramName + " parameter is null");
        }
    }
    
    private void checkPageExists(String pageName) throws WikiException {
        if (!this.pageExists(pageName)) {
            throw new WikiException("page named " + pageName + " does not exist in the store");
        }
    }
    
    private void checkVersionPlausible(int version) throws IllegalArgumentException {
        if (version < 0) {
            throw new IllegalArgumentException("version parameter is less than zero");
        }
    }
    
    private void checkIsFirstVersion(Page page) {
        if (page.getVersionedName().getVersion() != 0) {
            throw new IllegalArgumentException("page version is " + page.getVersionedName().getVersion() 
                    + ". It must be 0");
        }
    }
    private void checkIsFirstVersion(Attachment attachment) throws WikiException {
        if (attachment.getVersionedName().getVersion() != 0) {
            throw new WikiException("attachment version is " + attachment.getVersionedName().getVersion()
                    + ". It must be 0");
        }
    }
    
    private void checkPageVersionExists(Page page) throws WikiException {
        if (!this.pageVersionExists(page.getVersionedName())) {
            throw new WikiException("Page " + page + " does not exist in the store");
        }
    }
    
    private void checkPageIsNextVersion(Page newVersion) throws WikiException {
        Page latestVersionWeHave = pageNameMap.getPage(newVersion.getVersionedName().getName());
        if (!latestVersionWeHave.getVersionedName().isNextVersion(newVersion.getVersionedName())) {
            throw new WikiException("newVersion is version " + newVersion.getVersionedName().getVersion() 
                    + ". The latest version we have is " + latestVersionWeHave.getVersionedName().getVersion() 
                    + ". The new version is thus not the correct next version");
        }
    }
    
    private void checkIdenticalAttachmentSets(Page newVersion, Page latestVersion) throws WikiException {
        
        HashSet<VersionedName> latestAttmts = new HashSet<VersionedName>(latestVersion.getAttachmentVersionedNames());
        Set<VersionedName> newVersionAttmts = newVersion.getAttachmentVersionedNames();
        
        /*
         * Check size. If different, then obviously not same. We need to do this check
         * in addition to the set check in case the new set contains all the members of the
         * previous one *and* some new ones. THe set based check alone won't catch that
         * scenario.
         */
        if (latestAttmts.size() != newVersionAttmts.size()) {
            throw new WikiException("newVersion has an attachment set which is different in size from the latest version we have");
        }
        
        /* 
         * Comparison based on sets. They should be identical in content.
         * So, if we remove the content of one set from the other, we are left
         * with an empty set. 
         */
        latestAttmts.removeAll(newVersion.getAttachmentVersionedNames());
        if (! latestAttmts.isEmpty()) {
            throw new WikiException("newVersion has a different set of attachments from the latest version we have");
        }
    }
    
    private void checkNewPageHasNewAttachmentRef(Page newVersion, Attachment attachment) throws WikiException {
        Page latestVersion = this.getLatestVersion(newVersion);
        Set<VersionedName> latestVNames = new HashSet<VersionedName>(latestVersion.getAttachmentVersionedNames());
        Set<VersionedName> newVNames = new HashSet<VersionedName>(newVersion.getAttachmentVersionedNames());
        newVNames.removeAll(latestVNames); // should now be of size one
        if (newVNames.size() != 1) {
            throw new WikiException("set of versioned names in new version is not not one larger than the latest version we have");
        }
        if (! newVNames.iterator().next().equals(attachment.getVersionedName())) {
            throw new WikiException("set of versioned names in new version does not contain the new attachment's versioned name");
        }
        
        latestVNames.removeAll(new HashSet<VersionedName>(newVersion.getAttachmentVersionedNames()));
        if (!latestVNames.isEmpty()) {
            throw new WikiException("set of versioned names in new version is missing attachments we have in latest version");
        }
    }
    
    private void checkPageHasUpdatedAttachmentRef(Page newVersion, Attachment newAttachment) throws WikiException {
        Page latestVersion = this.getLatestVersion(newVersion);
        Set<VersionedName> latestVNames = new HashSet<VersionedName>(latestVersion.getAttachmentVersionedNames());
        Set<VersionedName> newVNames = new HashSet<VersionedName>(newVersion.getAttachmentVersionedNames());
        newVNames.removeAll(latestVNames); // should now be of size one, the new version being there
        if (newVNames.size() != 1) {
            throw new WikiException("set of versioned names in new version is not not one larger than the latest version we have");
        }
        if (! newVNames.iterator().next().equals(newAttachment.getVersionedName())) {
            throw new WikiException("set of versioned names in new version does not contain the new attachment's versioned name");
        }
        
        latestVNames.removeAll(new HashSet<VersionedName>(newVersion.getAttachmentVersionedNames()));
        if (latestVNames.size() != 1 ) {
            throw new WikiException("set of versioned names in new version is missing attachments we have in latest version");
        }
        
        // the set based check doesn't catch the case if the previous version of the attachment is still there
        if (newVersion.getAttachmentVersionedNames().contains(new VersionedName(newAttachment.getVersionedName().getName(),
                newAttachment.getVersionedName().getVersion() - 1))) {
            throw new WikiException("new version still contains reference to the old attachment version");
        }
        
    }
    
    private void checkPageDoesNotHaveAttachmentToBeDeleted(Page newVersion, Attachment attachmentToDelete) throws WikiException {
        
        Page latestVersionWeHave = this.getLatestVersion(newVersion);
        //      did the latest version we have actually have the attachment we're deleting
        if (!latestVersionWeHave.getAttachmentVersionedNames().contains(attachmentToDelete.getVersionedName())) {
            throw new WikiException("the latest version we have of the page does not contain the attachment, it thus can't be deleted");
        }
        
        // check the sets. we expect there to be only one item different between latest version and the new list
        // and for the that one to be the item we are deleting...
        
        Set<VersionedName> latestVNames = new HashSet<VersionedName>(latestVersionWeHave.getAttachmentVersionedNames());
        Set<VersionedName> newVNames = new HashSet<VersionedName>(newVersion.getAttachmentVersionedNames());
        
        latestVNames.removeAll(newVNames);
        if (latestVNames.size() != 1) {
            throw new WikiException("set of versioned names in new version is not not one smaller than the latest version we have");
        }
        if (! latestVNames.iterator().next().equals(attachmentToDelete.getVersionedName())) {
            throw new WikiException("the difference between the old set and the new set is not the attachment to delete");
        }
        newVNames.removeAll(latestVNames);
        if (!newVNames.isEmpty()) {
            throw new WikiException("new set of names has got an illegal extra attachment reference");
        }
        
        // the set based check doesn't catch the case if the previous version of the attachment is still there
        if (newVersion.getAttachmentVersionedNames().contains(new VersionedName(attachmentToDelete.getVersionedName().getName(),
                attachmentToDelete.getVersionedName().getVersion() - 1))) {
            throw new WikiException("new version still contains reference to the old attachment version");
        }
        
    }
    
    private void checkPageDoesNotAlreadyHaveAttachment(Page newVersion, Attachment attachment) throws WikiException {
        if (attachmentMap.pageHasAttachment(newVersion, attachment)) {
            throw new WikiException("Attachment with name " + attachment.getVersionedName().getName() 
                    + "already attached to page.");
        }
    }
    
    private void checkPageHasAttachment(Page page, Attachment attachment) throws WikiException {
        if (!attachmentMap.pageHasAttachment(page, attachment)) {
            throw new WikiException("Page " + page.getVersionedName().getName() + " has no attachments at all!");
        }
    }
    
    private void checkAttachmentIsNextVersion(Page newVersion, Attachment newAttachment) throws WikiException {
        Attachment attmt = attachmentMap.getLatestAttachment(newVersion, newAttachment.getVersionedName().getName());
        if (!attmt.getVersionedName().isNextVersion(newAttachment.getVersionedName())) {
            throw new WikiException("newAttachment is version " + newAttachment.getVersionedName().getVersion()
                    + ". The latest version we have is " + attmt.getVersionedName().getVersion()
                    + ". The new version is thus not the correct next version");
        }
    }

    private void checkPageHasRefToNamedAttachment(Page page, String attachmentName) throws WikiException {
        //      is the attachment name actually on the page?
        boolean foundAttachmentName = false;
        for (VersionedName vName : page.getAttachmentVersionedNames()) {
            if (vName.getName().equals(attachmentName)) {
                foundAttachmentName = true;
                break;
            }
        }
        if (!foundAttachmentName) {
            throw new WikiException("page " + page + " does not actually contain reference to an attachment called " + attachmentName);
        }
    }
    
    private void checkPageHasRefToAttachmentVersionedName(Page page, VersionedName versionedName) throws WikiException {
        if (!page.getAttachmentVersionedNames().contains(versionedName)) {
            throw new WikiException("page " + page + " does not actually contain reference to versioned name " + versionedName);
        }
        
    }
    
    private void checkPageHasAttachment(Page page, String attachmentName) throws WikiException {
        if (!attachmentMap.pageHasAttachment(page.getVersionedName().getName(), attachmentName)) {
            throw new WikiException("there is no attachment named " + attachmentName + " for the page " + page);
        }
    }
    
    private void validateObject() throws InvalidObjectException {
        if (MemoryDAO.this.pageNameMap == null) {
            throw new InvalidObjectException("pageNameMap is null!");
        }
        if (MemoryDAO.this.attachmentMap == null) {
            throw new InvalidObjectException("attachmentMap is null");
        }
        // we should now check everything's ok and the data structures are intact
        // - this involves walking the entire structure and checking everything
        
        
        for (Page page: pageNameMap.getLatestPages()) {
            List<Page> pageVersionsList = pageNameMap.getPageVersionsList( page.getVersionedName().getName());
            
            for (Page pageVersion : pageVersionsList) {
                // now check the attachments for each pageVersion are actually in the attachment map
                for (VersionedName vName : pageVersion.getAttachmentVersionedNames()) {
                    Attachment attmt = attachmentMap.getAttachment(pageVersion.getVersionedName().getName(), vName);
                    if (attmt == null) {
                        throw new InvalidObjectException("Missing attachment " + vName + " on page " + pageVersion);
                    }
                }
            }
        }
        
        // note, we *may* have superfluous objects lurking in the attachment map, but that doesn't
        // really matter, so we won't go looking for them...
    }

}