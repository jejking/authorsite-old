package org.juenger.wiki.dao.impl;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.juenger.wiki.WikiException;
import org.juenger.wiki.item.Attachment;
import org.juenger.wiki.item.Page;
import org.juenger.wiki.item.VersionedName;

/**
 * Complex collection class allowing the management of {@link org.juenger.wiki.item.Attachment}
 * and {@link org.juenger.wiki.item.Page} instances in memory.
 * 
 * <p>Additionally, the structure is fully serializable, allowing for it 
 * to be loaded at system startup time and written out at system 
 * shutdown time, providing a light-weight - but non-robust - persistence
 * mechanism.</p>
 * 
 * <p>Note that the entire structure will be held in memory for the
 * lifetime of a system.</p>
 *  
 * @author jking
 */
final class AttachmentMap implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Map<String, Map<String, List<Attachment>>> attachmentMap;
    
    /**
     * Default constructor.
     */
    public AttachmentMap() {
        attachmentMap = new HashMap<String, Map<String, List<Attachment>>>();
    }

    /**
     * Gets the attachments for a given page version.
     * 
     * @param page
     * @return set of the latest attachments for page
     * @throws WikiException
     * @see org.juenger.wiki.dao.AttachmentResolverDAO#getAttachments(Page)
     */
    public Set<Attachment> getAttachments(Page page) throws WikiException {
        assert page != null;
        //          get the attachment map
        Map<String, List<Attachment>> attmtMap = getPageAttachmentMap(page.getVersionedName().getName());
        if (attmtMap == null) {
            // we have a problem, page lists attachment v. names, but we have none..
            throw new WikiException("no attachments in the store for page " + page);
        }
        // now assemble set of the latest attachments 
        Set<Attachment> attachments = new HashSet<Attachment>();
        // iterate over the set of attachment version names and get the corresponding attachments
        for (VersionedName vName : page.getAttachmentVersionedNames()) {
            List<Attachment> attmtList = attmtMap.get(vName.getName());
            if (attmtList == null) {
                throw new WikiException("missing attachment list for attachment " + vName);
            }
            Attachment attmt = null;
            try {
                attmt = attmtList.get(vName.getVersion());
            }
            catch (IndexOutOfBoundsException ioobe) {
                throw new WikiException("missing attachment version for attachment " + vName);
            }
            attachments.add(attmt);
        }
        
        return attachments;
    }

    /**
     * 
     * 
     * @param pageName
     * @param attachmentVersionedName
     * @return
     */
    public Attachment getAttachment(String pageName, VersionedName attachmentVersionedName) {
        Map<String, List<Attachment>> pageAttmtMap = getPageAttachmentMap(pageName);
        if (pageAttmtMap == null) {
            return null;
        }
        List<Attachment> pageAttmtList = pageAttmtMap.get(attachmentVersionedName.getName());
        if (pageAttmtList == null) {
            return null;
        }
        Attachment attmt = null;
        try {
            attmt = pageAttmtList.get(attachmentVersionedName.getVersion());
        }
        catch (IndexOutOfBoundsException ioobe) {
            // do nothing, ref stays at null
        }
        return attmt;
    }

    public void addNewAttachmentVersion(Page newVersion, Attachment newAttachment) throws WikiException {
        addNewAttachmentVersion(newVersion.getVersionedName().getName(), newAttachment);
    }
    
    public void addNewAttachmentVersion(String pageName, Attachment newAttachment) throws WikiException {
        Map<String, List<Attachment>> pageAttmtMap = getPageAttachmentMap(pageName);
        if (pageAttmtMap == null) {
            throw new WikiException("no attachments exist for page " + pageName);
        }
        List<Attachment> pageAttmtList = pageAttmtMap.get(newAttachment.getVersionedName().getName());
        if (pageAttmtList == null) {
            throw new WikiException("attachment " + newAttachment.getVersionedName().getName() + " does not exist for page");
        }
        if (newAttachment.getVersionedName().getVersion() != pageAttmtList.size() ) {
            throw new WikiException("attachment has version " + newAttachment.getVersionedName().getVersion() 
                    + ". It should have " + pageAttmtList.size() + " to be the next version");
        }
        pageAttmtList.add(newAttachment);
    }

    public Attachment getLatestAttachment(Page page, String attachmentName) {
        return getLatestAttachment(page.getVersionedName().getName(), attachmentName);
    }

    public Attachment getLatestAttachment(String name, String attachmentName) {
        Map<String, List<Attachment>> pageAttmtMap = getPageAttachmentMap(name);
        if (pageAttmtMap == null) {
            return null;
        }
        List<Attachment> attmtList = pageAttmtMap.get(attachmentName);
        if (attmtList == null) {
            return null;
        }
        return attmtList.get(attmtList.size() - 1);
    }

    public void addNewAttachmentForPage(Page newVersion, Attachment attachment) {
        addNewAttachmentForPage(newVersion.getVersionedName().getName(), attachment);
    }
    
    public void addNewAttachmentForPage(String pageName, Attachment attachment) {
        Map<String, List<Attachment>> pageAttmtMap = getPageAttachmentMap(pageName);
        if (pageAttmtMap == null) {
            pageAttmtMap = new HashMap<String, List<Attachment>>();
        }
        
        new HashMap<String, List<Attachment>>();
        
        // list of attachment versions created. First attachment goes in at index 0.
        ArrayList<Attachment> attachmentVersionList = new ArrayList<Attachment>();
        attachmentVersionList.add(attachment);
        
        // add the new list of attachment versions to the map of attachments for the page
        pageAttmtMap.put(attachment.getVersionedName().getName(), attachmentVersionList);
        attachmentMap.put(pageName, pageAttmtMap);
    }

    public boolean pageHasAttachments(Page newVersion) {
        return pageHasAttachments(newVersion.getVersionedName().getName());
    }
    
    public boolean pageHasAttachments(String pageName) {
        return attachmentMap.containsKey(pageName);
    }
    
    public boolean pageHasAttachment(Page newVersion, Attachment attachment) {
        return pageHasAttachment(newVersion.getVersionedName().getName(),
                attachment.getVersionedName().getName());
    }
    
    public boolean pageHasAttachment(String pageName, String attachmentName) {
        Map<String, List<Attachment>> pageAttachmentMap = getPageAttachmentMap(pageName);
        if (pageAttachmentMap == null) {
            return false;
        }
        List<Attachment> attachmentList = pageAttachmentMap.get(attachmentName);
        if (attachmentList != null) {
            return true;
        }
        return false;
    }

    public void addNewPageAttachmentMap(String pageName, Map<String, List<Attachment>> newPageAttmtMap) {
        attachmentMap.put(pageName, newPageAttmtMap);
    }

    public Map<String, List<Attachment>> getPageAttachmentMap(String name) {
        return attachmentMap.get(name);
    }

    public void remove(String pageName) {
        attachmentMap.remove(pageName);
    }
    
    /*
     * SERIALIZATION CODE
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeInt(attachmentMap.size());
        for (String pageName : attachmentMap.keySet()) {
            stream.writeObject(pageName);
            Map<String, List<Attachment>> pageAttachmentMap = attachmentMap.get(pageName);
            stream.writeInt(pageAttachmentMap.size());
            for (String attachmentName : pageAttachmentMap.keySet()) {
                stream.writeObject(attachmentName);
                List<Attachment> attachmentVersions = pageAttachmentMap.get(attachmentName);
                stream.writeInt(attachmentVersions.size());
                for (Attachment attmt : attachmentVersions) {
                    stream.writeObject(attmt);
                }
            }
        }
        
        
    }
    
    private void readObject(ObjectInputStream stream) throws IOException, ClassCastException, ClassNotFoundException {
        attachmentMap = new HashMap<String, Map<String, List<Attachment>>>();
        stream.defaultReadObject();
        int pageMapSize = stream.readInt();
        for (int i = 0; i < pageMapSize; i++) {
            String pageName = (String) stream.readObject();
            Map<String, List<Attachment>> pageAttachmentMap = new HashMap<String, List<Attachment>>();
            int pageAttachmentMapSize = stream.readInt();
            // read in the string keyed map of lists of attmt versions for the page
            for (int j = 0; j < pageAttachmentMapSize; j++) {
                String attachmentName = (String) stream.readObject();
                int attachmentVersionListSize = stream.readInt();
                List<Attachment> attachmentVersionList = new ArrayList<Attachment>( 10 < attachmentVersionListSize ? 10 : attachmentVersionListSize);
                for (int k = 0; k < attachmentVersionListSize; k++) {
                    Attachment attmt = (Attachment) stream.readObject();
                    // this checks that the attachment slots into the right place in the array list so our fast access logic works 
                    if (attmt.getVersionedName().getVersion() != k) {
                        throw new InvalidObjectException("attachment " + attmt + " is in wrong place " + k + " in serialized stream");
                    }
                    attachmentVersionList.add(attmt);
                }
                pageAttachmentMap.put(attachmentName, attachmentVersionList);
            }
            attachmentMap.put(pageName, pageAttachmentMap);
        }
    }

    public int size() {
        return attachmentMap.size();
    }
}
