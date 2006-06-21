package org.juenger.wiki.dao.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.juenger.wiki.WikiException;
import org.juenger.wiki.item.Page;
import org.juenger.wiki.item.VersionedName;

final class PageNameMap implements Serializable {
    
    /**
     * Computed serial version UID (from Eclipse). 
     */
    private static final long serialVersionUID = -7673435775699874334L;
    private Map<String, List<Page>> pageNameMap;
    
    public PageNameMap() {
        pageNameMap = new HashMap<String, List<Page>>();
    }
    
    public Page getPageVersion(VersionedName versionedName) {
        List <Page> pageVersions = getPageVersionsList(versionedName.getName());
        if (pageVersions == null) {
            return null;
        }
        Page pageToReturn = null;
        try {
            pageToReturn = pageVersions.get(versionedName.getVersion());
        }
        catch (IndexOutOfBoundsException ioobe) {
            // ignore the exception, we return null by default!
        }
        return pageToReturn;
    }

    public Page getPageVersion(Page page, int version) {
        List<Page> pageVersions = getPageVersionsList(page.getVersionedName().getName());
        if (pageVersions == null) {
            return null;
        }
        Page pageToReturn = null;
        try {
            pageToReturn = pageVersions.get(version);
        }
        catch (IndexOutOfBoundsException ioobe) {
            // ignore the exception, we return null by default!
        }
        return pageToReturn;
    }

    public List<Page> getPageVersionsList(String pageName) {
        return pageNameMap.get(pageName);
    }

    public boolean isLatestPageVersion(Page page) throws WikiException {
        //          can we find the list of versions corresponding to the page name?
        List<Page> pageVersionList = pageNameMap.get(page.getVersionedName().getName());
        if (pageVersionList == null) {
            throw new WikiException("no page named " + page.getVersionedName().getName() + " in the store");
        }
        if (pageVersionList.get(pageVersionList.size() - 1).equals(page)) {
            return true;
        }
        return false;
    }

    public void addNewPageVersion(Page newVersion) {
        List<Page> pageVersionsList = pageNameMap.get(newVersion.getVersionedName().getName());
        pageVersionsList.add(newVersion);
    }

    public Page getPage(String name) {
        List<Page> pageVersions = getPageVersionsList(name);
        if (pageVersions == null) {
            return null;
        }
        return pageVersions.get(pageVersions.size() - 1);
    }

    public void deletePage(String pageName) throws WikiException {
        List<Page> pageVersions = getPageVersionsList(pageName);
        if (pageVersions == null) {
            throw new WikiException("No page named " + pageName + " in the persistent store");
        }
        
        // the work, remove refs to page from both maps
        pageNameMap.remove(pageName);
        
        
    }

    public void addNewPage(Page page) throws WikiException {
        if (pageNameMap.containsKey(page.getVersionedName().getName()) == true) {
            throw new WikiException("Page with name " + page.getVersionedName().getName() + " already exists in the store");
        }
        List<Page> pageList = new ArrayList<Page>();
        pageList.add(0, page);
        pageNameMap.put(page.getVersionedName().getName(), pageList);
        
    }

    public List<Page> getLatestPages() {
        // assemble list of latest versions
        ArrayList<Page> latest = new ArrayList<Page>(pageNameMap.size());
        Iterator pageNameMapIt = pageNameMap.keySet().iterator();
        while (pageNameMapIt.hasNext()) {
            List<Page> pageVersionsList = pageNameMap.get(pageNameMapIt.next());
            latest.add(pageVersionsList.get(pageVersionsList.size() - 1));
        }
        return Collections.unmodifiableList(latest);
    }

    public boolean isEmpty() {
        return pageNameMap.isEmpty();
    }
    
    /*
     * SERIALIZATION CODE 
     */
    
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeInt(pageNameMap.size());
        for (String key : pageNameMap.keySet()) {
            stream.writeObject(key);
            List<Page> pageVersionsList = getPageVersionsList(key);
            stream.writeInt(pageVersionsList.size());
            for (Page page : pageVersionsList) {
                stream.writeObject(page);
            }
        }
    }
    
    private void readObject(ObjectInputStream stream) throws Exception {
        this.pageNameMap = new HashMap<String, List<Page>>();
        stream.defaultReadObject();
        int mapSize = stream.readInt();
        for (int i = 0; i < mapSize; i++) {
            String pageName = (String) stream.readObject();
            List<Page> pageVersionList = new ArrayList<Page>();
            int listSize = stream.readInt();
            for (int j = 0; j < listSize; j++) {
                Page pageVersion = (Page) stream.readObject();
                pageVersionList.add(pageVersion);
            }
            pageNameMap.put(pageName, pageVersionList);
        }
    }

    public int size() {
        return this.pageNameMap.size();
    }
}
