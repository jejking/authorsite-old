package org.juenger.wiki.dao.impl;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.juenger.wiki.WikiException;
import org.juenger.wiki.dao.impl.MemoryDAO;
import org.juenger.wiki.item.Attachment;
import org.juenger.wiki.item.Page;
import org.juenger.wiki.item.VersionedName;

import junit.framework.TestCase;

public class MemoryDAOTest extends TestCase {

    public static void main(String[] args) {
    }
    
    public void testGetPageOneVersion() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page newPage = new Page("testAuthor", "testName", "testText");
        dao.addNewPage(newPage);
        
        Page retrievedPage = dao.getPage("testName");
        assertNotNull(retrievedPage);
        assertEquals(newPage, retrievedPage);
        assertSame(newPage, retrievedPage);
    }
    
    public void testGetPageTwoVersions() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageVersionOne = new Page("testAuthor", "testName", "testText");
        dao.addNewPage(pageVersionOne);
        
        Page pageVersionTwo = new Page(pageVersionOne, "testEditor", "newText");
        dao.addUpdatedPageWithNewText(pageVersionTwo);
        
        Page retrievedPage = dao.getPage("testName"); // should get version two!
        assertNotNull(retrievedPage);
        assertEquals(pageVersionTwo, retrievedPage);
        assertSame(pageVersionTwo, retrievedPage);
    }
    
    public void testGetPageNoneExists() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page page = new Page("testAuthor", "testName", "testText");
        dao.addNewPage(page);
        
        Page retrievedPage = dao.getPage("notTestName");
        assertNull(retrievedPage);
    }
    
    public void testGetPageNullName() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page page = new Page("testAuthor", "testName", "testText");
        dao.addNewPage(page);
        
        try {
            Page retrievedPage = dao.getPage(null);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals(iae.getMessage(), "name parameter is null");
        }
    }
    
    public void testGetAllPagesTwoPagesOneVersionOfEach() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOne = new Page("testAuthor", "pageOne", "testText");
        Page pageTwo = new Page("testAuthor", "pageTwo", "testText");
        dao.addNewPage(pageOne);
        dao.addNewPage(pageTwo);
        
        List<Page> retrievedPages = dao.getAllPages();
        assertEquals(2, retrievedPages.size());
        
        boolean foundPageOne = false;
        boolean foundPageTwo = false;
        
        for (Page page : retrievedPages) {
            if (page.equals(pageOne)) {
                foundPageOne = true;
                continue;
            }
            if (page.equals(pageTwo)) {
                foundPageTwo = true;
            }
        }
        assertTrue(foundPageOne & foundPageTwo);
    }
    
    public void testGetAllPagesTwoPagesTwoVersionsOfEach() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        Page pageTwoVersionOne = new Page("testAuthor2", "pageTwo", "textOne");
        dao.addNewPage(pageTwoVersionOne);
        Page pageTwoVersionTwo = new Page(pageTwoVersionOne, "editor2", "textTwo");
        dao.addUpdatedPageWithNewText(pageTwoVersionTwo);
        
        List<Page> retrievedPages = dao.getAllPages();
        assertEquals(2, retrievedPages.size());
        
        boolean foundPageOneVersionTwo = false;
        boolean foundPageTwoVersionTwo = false;
        for (Page page : retrievedPages) {
            if (page.equals(pageOneVersionTwo)) {
                foundPageOneVersionTwo = true;
                continue;
            }
            if (page.equals(pageTwoVersionTwo)) {
                foundPageTwoVersionTwo = true;
            }
        }
        assertTrue(foundPageOneVersionTwo & foundPageTwoVersionTwo);
    }
    
    public void testGetAllPageNoPages() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        List<Page> pages = dao.getAllPages();
        assertTrue(pages.isEmpty());
    }
    
    public void testAllGetAllVersions() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        List<Page> allVersions = dao.getAllVersions(pageOneVersionOne);
        assertEquals(2, allVersions.size());
        
        boolean foundVersionOne = false;
        boolean foundVersionTwo = false;
        for (Page page: allVersions) {
            if (page.equals(pageOneVersionOne)) {
                foundVersionOne = true;
                continue;
            }
            if (page.equals(pageOneVersionTwo)) {
                foundVersionTwo = true;
            }
        }
        assertTrue(foundVersionOne & foundVersionTwo);
    }
    
    public void testGetAllVersionsNullParam() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        Page nullPage = null;
        
        try {
            dao.getAllVersions(nullPage);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals(iae.getMessage(), "page parameter is null");
        }
    }
    
    public void testGetAllVersionsNoneStored() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        Page pageTwo = new Page("testAuthor", "pageTwo", "text");
        
        List<Page> pages = dao.getAllVersions(pageTwo); // none stored
        assertTrue(pages.isEmpty());
    }
    
    public void testGetAllVersionsListUnmodifiable() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        Page pageTwo = new Page("testAuthor", "pageTwo", "text");
        
        List<Page> pages = dao.getAllVersions(pageOneVersionOne);
        try {
            pages.add(pageTwo);
            fail("expected unsupported operation exception");
        }
        catch (UnsupportedOperationException uoe) {
            assertTrue(true);
        }
    }
    
    public void testGetAllVersionsStringParam() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        List<Page> allVersions = dao.getAllVersions("pageOne");
        assertEquals(2, allVersions.size());
        
        boolean foundVersionOne = false;
        boolean foundVersionTwo = false;
        for (Page page: allVersions) {
            if (page.equals(pageOneVersionOne)) {
                foundVersionOne = true;
                continue;
            }
            if (page.equals(pageOneVersionTwo)) {
                foundVersionTwo = true;
            }
        }
        assertTrue(foundVersionOne & foundVersionTwo);
    }
    
    public void testGetAllVersionsStringParamNull() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        String nullString = null;
        try {
            List<Page> allVersions = dao.getAllVersions(nullString);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals(iae.getMessage(), "pageName parameter is null");
        }
    }
    
    public void testGetAllVersionsStringParamNoneStored() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        Page pageTwo = new Page("testAuthor", "pageTwo", "text");
        
        List<Page> pages = dao.getAllVersions("pageTwo"); // none stored
        assertTrue(pages.isEmpty());
    }
    
    public void testGetAllVersionsStringParamListUnmodifiable() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        Page pageTwo = new Page("testAuthor", "pageTwo", "text");
        
        List<Page> pages = dao.getAllVersions("pageOne");
        try {
            pages.add(pageTwo);
            fail("expected unsupported operation exception");
        }
        catch (UnsupportedOperationException uoe) {
            assertTrue(true);
        }
    }
    
    public void testGetAllVersionsSortOrder() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        List<Page> allVersions = dao.getAllVersions("pageOne");
        assertEquals(2, allVersions.size());
        
        assertEquals(pageOneVersionOne, allVersions.get(0));
        assertEquals(pageOneVersionTwo, allVersions.get(1));
    }
    
    public void testGetPageVersionPageInt() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        Page retrieved = dao.getPageVersion(pageOneVersionOne, 1);
        assertNotNull(retrieved);
        assertEquals(pageOneVersionTwo, retrieved);
    }
    
    public void testGetPageVersionVersionedName() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        Page retrieved = dao.getPageVersion(new VersionedName("pageOne", 1));
        assertNotNull(retrieved);
        assertEquals(pageOneVersionTwo, retrieved);
    }
    
    public void testGetPageVersionPageIntPageNull() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        try {
            Page retrieved = dao.getPageVersion(null, 1);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals(iae.getMessage(), "page parameter is null");
        }
    }
    
    public void testGetPageVersionPageIntIntLessThanZero() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        try {
            Page retrieved = dao.getPageVersion(pageOneVersionOne, -1);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals(iae.getMessage(), "version parameter is less than zero");
        }
    }
    
    public void testGetPageVersionVersionedNameNullParam() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        try {
            Page retrieved = dao.getPageVersion(null);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals(iae.getMessage(), "versionedName parameter is null");
        }
    }
    
    public void testGetPageVersionPageIntNoSuchPage() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        Page pageTwo = new Page("testAuthor", "pageTwo", "textOne");
        
        Page retrieved = dao.getPageVersion(pageTwo, 0);
        assertNull(retrieved);
    }
    
    public void testGetPageVersionVersionedNameNoSuchPage() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
                
        Page retrieved = dao.getPageVersion(new VersionedName("pageTwo", 0));
        assertNull(retrieved);
    }
    
    public void testGetPageVersionPageIntNoSuchVersion() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
               
        Page retrieved = dao.getPageVersion(pageOneVersionOne, 3);
        assertNull(retrieved);
    }
    
    public void testGetPageVersionVersionedNameNoSuchVersion() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        Page retrieved = dao.getPageVersion(new VersionedName("pageOne", 3));
        assertNull(retrieved);
    }

    public void testGetLatestVersionPage() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        Page retrieved = dao.getLatestVersion(pageOneVersionOne);
        assertNotNull(retrieved);
        assertEquals(pageOneVersionTwo, retrieved);
    }
    
    public void testGetLastVersionPageNullParam() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        try {
            Page retrieved = dao.getLatestVersion(null);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals(iae.getMessage(), "page parameter is null");
        }
    }
    
    public void testGetLatestVersionPageNoMatchingPages() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        Page pageTwo = new Page("testAuthor", "pageTwo", "textOne");
        
        Page retrieved = dao.getLatestVersion(pageTwo);
        assertNull(retrieved);
    }
    
    public void testDeletePagePage() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        Page pageTwoVersionOne = new Page("testAuthor2", "pageTwo", "textOne");
        dao.addNewPage(pageTwoVersionOne);
        Page pageTwoVersionTwo = new Page(pageTwoVersionOne, "editor2", "textTwo");
        dao.addUpdatedPageWithNewText(pageTwoVersionTwo);
        
        dao.deletePage(pageTwoVersionOne); // all page two is gone!
        
        List<Page> pages = dao.getAllPages();
        assertEquals(1, pages.size());
        assertEquals(pageOneVersionTwo, pages.get(0));
        
    }
    
    public void testDeletePagePageWithAttachments() throws Exception {
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", "version two");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        // now delete page one
        dao.deletePage("testOne");
        
        // interrogate the DAO's internal structures via reflection to
        // make sure the attachment was deleted as well
        
        Class daoClass = dao.getClass();
        Field attachmentMapField = daoClass.getDeclaredField("attachmentMap");
        attachmentMapField.setAccessible(true);
        Object attmtMapObj = attachmentMapField.get(dao);
        
        Class attmtMapClass = attmtMapObj.getClass();
        Field attmtMapMapField = attmtMapClass.getDeclaredField("attachmentMap");
        attmtMapMapField.setAccessible(true);
        Map<String, Map<String, List<Attachment>>> attmtMapMap = 
            (Map<String, Map<String, List<Attachment>>>) attmtMapMapField.get(attmtMapObj);
        assertNull(attmtMapMap.get("testOne"));
    }
    
    public void testDeletePagePageNullParam() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        Page pageTwoVersionOne = new Page("testAuthor2", "pageTwo", "textOne");
        dao.addNewPage(pageTwoVersionOne);
        Page pageTwoVersionTwo = new Page(pageTwoVersionOne, "editor2", "textTwo");
        dao.addUpdatedPageWithNewText(pageTwoVersionTwo);
        
        Page pageThree = null;
        try {
            dao.deletePage(pageThree);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals("pageToDelete parameter is null", iae.getMessage());
        }
    }
    
    public void testDeletePagePageNonExistent() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        Page pageTwoVersionOne = new Page("testAuthor2", "pageTwo", "textOne");
        
        try {
            dao.deletePage(pageTwoVersionOne);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testDeletePageString() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        Page pageTwoVersionOne = new Page("testAuthor2", "pageTwo", "textOne");
        dao.addNewPage(pageTwoVersionOne);
        Page pageTwoVersionTwo = new Page(pageTwoVersionOne, "editor2", "textTwo");
        dao.addUpdatedPageWithNewText(pageTwoVersionTwo);
        
        dao.deletePage("pageTwo"); // all page two is gone!
        
        List<Page> pages = dao.getAllPages();
        assertEquals(1, pages.size());
        assertEquals(pageOneVersionTwo, pages.get(0));
    }
    
    public void testDeletePageStringNull() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        Page pageTwoVersionOne = new Page("testAuthor2", "pageTwo", "textOne");
        dao.addNewPage(pageTwoVersionOne);
        Page pageTwoVersionTwo = new Page(pageTwoVersionOne, "editor2", "textTwo");
        dao.addUpdatedPageWithNewText(pageTwoVersionTwo);
        
        String pageThree = null;
        try {
            dao.deletePage(pageThree);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals("pageName parameter is null", iae.getMessage());
        }
    }
    
    public void testDeletePageStringNonExistent() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOneVersionOne = new Page("testAuthor", "pageOne", "textOne");
        dao.addNewPage(pageOneVersionOne);
        Page pageOneVersionTwo = new Page(pageOneVersionOne, "editorOne", "textTwo");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        try {
            dao.deletePage("pageTwo");
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertEquals("No page named pageTwo in the persistent store", we.getMessage());
        }
    }
    
    public void testUpdatePageWithNewAttachment() throws Exception {
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        Page retrieved = dao.getPage("testOne");
        assertNotNull(retrieved);
        assertEquals(pageOneVersionTwo, retrieved);
        
        // introspect the DAO to ensure the attachment was correctly added
        Class daoClass = dao.getClass();
        Field attachmentMapField = daoClass.getDeclaredField("attachmentMap");

        attachmentMapField.setAccessible(true);
        Object attmtMapObj = attachmentMapField.get(dao);
        
        Class attmtMapClass = attmtMapObj.getClass();
        Field attmtMapMapField = attmtMapClass.getDeclaredField("attachmentMap");
        attmtMapMapField.setAccessible(true);
        
        Map<String, Map<String, List<Attachment>>> attmtMapMap = 
            (Map<String, Map<String, List<Attachment>>>) attmtMapMapField.get(attmtMapObj);
        
        Map<String, List<Attachment>> pageAttmtMap = attmtMapMap.get("testOne");
        assertNotNull(pageAttmtMap);
        List<Attachment> attmtVersions = pageAttmtMap.get("attmtOne");
        assertNotNull(attmtVersions);
        assertEquals(1, attmtVersions.size());
        Attachment retrievedAttachment = attmtVersions.get(0);
        assertNotNull(retrievedAttachment);
    }
    
    public void testUpdatePageWithNewAttachmentNullPage() throws Exception {
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        
        try {
            dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, null);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals("attachment parameter is null", iae.getMessage());
        }
       
    }
    
    public void testUpdatePageWithNewAttachmentNullAttmt() throws Exception {
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        try {
            dao.addUpdatedPageWithNewAttachment(null, attmtOne);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals("newVersion parameter is null", iae.getMessage());
        }
    }
    
    public void testUpdatePageWithNewAttachmentPageNotExistent() throws Exception {
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        MemoryDAO dao = new MemoryDAO();
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        
        // page one was never added to the dao...
        try {
            dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
        
    }
    
    public void testUpdatePageWithNewAttachmentAttmtNotFirstVersion() throws Exception {
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Attachment attmtOneVersionTwo = new Attachment(attmtOne, "testEditor", new byte[]{4,5,6});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOneVersionTwo.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        
        try {
            dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOneVersionTwo);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
        
        
    }
    
    public void testUpdatePageWithNewAttachmentPageDoesNotListAttmt() throws Exception {
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        // note, here we don't add the name to the set of attachment versioned names
              
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        
        try {
            dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
            
    }
    
    public void testUpdatePageWithNewAttachmentPageVersionJumpedTooFar() throws Exception { 
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", "version two");
        // v2 not actually added to the dao
        
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "testEditor", attmtNames);
        
        try {
            dao.addUpdatedPageWithNewAttachment(pageOneVersionThree, attmtOne);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testUpdatePageWithNewAttachmentIdenticallyNamedAttachment() throws Exception {
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        Attachment anotherAttmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {4,5,6});
        attmtNames.add(anotherAttmtOne.getVersionedName());
        
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "editor", attmtNames);
        
        try {
            dao.addUpdatedPageWithNewAttachment(pageOneVersionThree, anotherAttmtOne);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testUpdatePageWithNewAttachmentVersion() throws Exception {
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
      
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        // now, new version of attachment
        Attachment attmtOneVersionTwo = new Attachment(attmtOne, "testEditor", new byte[] {4,5,6});
        attmtNames.remove(attmtOne.getVersionedName());
        attmtNames.add(attmtOneVersionTwo.getVersionedName());
        
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "testEditor", attmtNames);
        
        dao.addUpdatedPageWithUpdatedAttachment(pageOneVersionThree, attmtOneVersionTwo);
        
        Page retrievedPage = dao.getPage("testOne");
        assertNotNull(retrievedPage);
        assertEquals(pageOneVersionThree, retrievedPage);
        assertTrue(retrievedPage.getAttachmentVersionedNames().contains(attmtOneVersionTwo.getVersionedName()));
        assertEquals(1, retrievedPage.getAttachmentVersionedNames().size());

    }
    
    public void testUpdatePageWithNewAttachmentVersionNullPage() throws Exception {
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        // now, new version of attachment
        Attachment attmtOneVersionTwo = new Attachment(attmtOne, "testEditor", new byte[] {4,5,6});
        attmtNames.remove(attmtOne.getVersionedName());
        attmtNames.add(attmtOneVersionTwo.getVersionedName());
        
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "testEditor", attmtNames);
        
        try {
            dao.addUpdatedPageWithUpdatedAttachment(null, attmtOneVersionTwo);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals("newVersion parameter is null", iae.getMessage());
        }
    }
    
    public void testUpdatePageWithNewAttachmentVersionNullAttachment() throws Exception {
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        // now, new version of attachment
        Attachment attmtOneVersionTwo = new Attachment(attmtOne, "testEditor", new byte[] {4,5,6});
        attmtNames.remove(attmtOne.getVersionedName());
        attmtNames.add(attmtOneVersionTwo.getVersionedName());
        
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "testEditor", attmtNames);
        
        try {
            dao.addUpdatedPageWithUpdatedAttachment(pageOneVersionThree, null);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals("newAttachment parameter is null", iae.getMessage());
        }
    }
    
    public void testUpdatePageWithNewAttachmentVersionPageNotNextVersion() throws Exception {
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        // now, new version of attachment
        Attachment attmtOneVersionTwo = new Attachment(attmtOne, "testEditor", new byte[] {4,5,6});
        attmtNames.remove(attmtOne.getVersionedName());
        attmtNames.add(attmtOneVersionTwo.getVersionedName());
        
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "testEditor", attmtNames);
        
        Page pageOneVersionFour = new Page(pageOneVersionThree, "testEditor", "some new text");
        
        try {
            dao.addUpdatedPageWithUpdatedAttachment(pageOneVersionFour, attmtOneVersionTwo);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testUpdatePageWithNewAttachmentVersionAttmtNotNextVersion() throws Exception {
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        // now, new version of attachment
        Attachment attmtOneVersionTwo = new Attachment(attmtOne, "testEditor", new byte[] {4,5,6});
        
        // and another one
        Attachment attmtOneVersionThree = new Attachment(attmtOneVersionTwo, "testEditor", new byte[] {7,8,9});
        attmtNames.remove(attmtOne.getVersionedName());
        attmtNames.add(attmtOneVersionThree.getVersionedName());
        
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "testEditor", attmtNames);
        
        try {
            dao.addUpdatedPageWithUpdatedAttachment(pageOneVersionThree, attmtOneVersionTwo);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testUpdatePageWithNewAttachmentVersionPageDoesNotListAttmt() throws Exception {
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        // now, new version of attachment
        Attachment attmtOneVersionTwo = new Attachment(attmtOne, "testEditor", new byte[] {4,5,6});
        
        // attmtNames has old version of attachment versioned name in it
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "testEditor", attmtNames);
                
        try {
            dao.addUpdatedPageWithUpdatedAttachment(pageOneVersionThree, attmtOneVersionTwo);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testUpdatePageWithNewAttachmentVersionPageDoesNotExist() throws Exception {
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        // now, new version of attachment
        Attachment attmtOneVersionTwo = new Attachment(attmtOne, "testEditor", new byte[] {4,5,6});
        attmtNames.remove(attmtOne.getVersionedName());
        attmtNames.add(attmtOneVersionTwo.getVersionedName());
        
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "testEditor", attmtNames);
        
        Page pageTwo = new Page("testAuthor", "pageTwo", "testText");
        
        try {
            dao.addUpdatedPageWithUpdatedAttachment(pageTwo, attmtOneVersionTwo);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testUpdatePageWithNewAttachmentButOneOfSameNameAlreadyThere() throws Exception {
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        Attachment alsoAttmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {9,8,7});
        
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "editor", attmtNames);
        
        try {
            dao.addUpdatedPageWithNewAttachment(pageOneVersionThree, alsoAttmtOne);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testUpdatePageWithNewAttachmentPageHasMultipleVersionsOfAttachmentName() throws Exception {
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        attmtNames.add(new VersionedName("attmtOne", 2));
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        
        try {
            dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testAddUpdatePageWithNewAttachmentPagePageHasRefsToNonExistentAttachments() throws Exception {
        Page pageOne = new Page("author", "testOne", "testText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        attmtNames.add(new VersionedName("another attachment", 0));
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        
        try {
            dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testAddUpatePageWithNewAttachmentPageHasRemovedRefs() throws Exception {
        // for example if it had attm1, attm2.. but the new version only refers to attm1
        Page pageOne = new Page("author", "testOne", "testText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneV2 = new Page(pageOne, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneV2, attmtOne);
        
        attmtNames.remove(attmtOne.getVersionedName()); // oops, shouldn't be doing this
        Attachment attmtTwo = new Attachment("testAuthor", "attmtTwo", "application/octet-stream", new byte[] {4,5,6});
        attmtNames.add(attmtTwo.getVersionedName());
        
        Page pageOneV3 = new Page(pageOneV2, "editor", attmtNames);
        
        try {
            dao.addUpdatedPageWithNewAttachment(pageOneV3, attmtTwo);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    // generally, we need to ensure that the set of versioned names is identical
    // to that of the previous version, exception that this particular attachment
    // has been updated
    
    public void testUpdatePageWithNewAttachmentButSameOneAgain() throws Exception {
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "editor", attmtNames);
        
        try {
            dao.addUpdatedPageWithNewAttachment(pageOneVersionThree, attmtOne);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    /*
     * tests for add updated page with updated attachment
     * 
     * - ok data
     * - page null
     * - attachment null
     * - no such page
     * - page not proper next version
     * - attachment doesn't already exist
     * - attachment not proper next version
     * - page obj doesn't list the attachment
     * - previous page obj doesn't actually have a ref to the attachment name
     * - page has multiple refs to attachment with same name
     */
    
    public void testAddUpdatePageWithUpdatedAttachment() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        Attachment attmtOneVersionTwo = new Attachment(attmtOne, "testEditor", new byte[]{4,5,6});
        Set<VersionedName> attmtNames2 = new HashSet<VersionedName>();
        attmtNames2.add(attmtOneVersionTwo.getVersionedName());
        
        // creates new version of page with the updated attachment
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "editor", attmtNames2);
        dao.addUpdatedPageWithUpdatedAttachment(pageOneVersionThree, attmtOneVersionTwo);
        
        Page retrieved = dao.getPage("testOne");
        assertEquals(pageOneVersionThree, retrieved);
        
        assertEquals(1, retrieved.getAttachmentVersionedNames().size());
        assertTrue(retrieved.getAttachmentVersionedNames().contains(attmtOneVersionTwo.getVersionedName()));
        
        // interrogate the attachment map to make sure its state is correct
        Class daoClass = dao.getClass();
        Field attachmentMapField = daoClass.getDeclaredField("attachmentMap");
        attachmentMapField.setAccessible(true);
        attachmentMapField.setAccessible(true);
        Object attmtMapObj = attachmentMapField.get(dao);
        
        Class attmtMapClass = attmtMapObj.getClass();
        Field attmtMapMapField = attmtMapClass.getDeclaredField("attachmentMap");
        attmtMapMapField.setAccessible(true);
        Map<String, Map<String, List<Attachment>>> attmtMapMap = 
            (Map<String, Map<String, List<Attachment>>>) attmtMapMapField.get(attmtMapObj);
        assertNotNull(attmtMapMap);
        Map<String, List<Attachment>> pageAttmtMap = attmtMapMap.get("testOne");
        assertNotNull(pageAttmtMap);
        List<Attachment> pageAttmtVersions = pageAttmtMap.get("attmtOne");
        assertNotNull(pageAttmtVersions);
        assertEquals(2, pageAttmtVersions.size());
        assertEquals(attmtOne, pageAttmtVersions.get(0));
        assertEquals(attmtOneVersionTwo, pageAttmtVersions.get(1));
    }
    
    public void testAddUpdatePageWithUpdatedAttachmentPageNull() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        Attachment attmtOneVersionTwo = new Attachment(attmtOne, "testEditor", new byte[]{4,5,6});
        Set<VersionedName> attmtNames2 = new HashSet<VersionedName>();
        attmtNames2.add(attmtOneVersionTwo.getVersionedName());
        
        // creates new version of page with the updated attachment
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "editor", attmtNames2);
        try {
            dao.addUpdatedPageWithUpdatedAttachment(null, attmtOneVersionTwo);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertTrue(true);
        }
    }
    
    public void testAddUpdatePageWithUpdatedAttachmentAtachmentNull() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        Attachment attmtOneVersionTwo = new Attachment(attmtOne, "testEditor", new byte[]{4,5,6});
        Set<VersionedName> attmtNames2 = new HashSet<VersionedName>();
        attmtNames2.add(attmtOneVersionTwo.getVersionedName());
        
        // creates new version of page with the updated attachment
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "editor", attmtNames2);
        try {
            dao.addUpdatedPageWithUpdatedAttachment(pageOneVersionThree, null);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertTrue(true);
        }
    }
    
    public void testAddUpdatePageWithUpdatedAttachmentNoSuchPage() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        Attachment attmtOneVersionTwo = new Attachment(attmtOne, "testEditor", new byte[]{4,5,6});
        Set<VersionedName> attmtNames2 = new HashSet<VersionedName>();
        attmtNames2.add(attmtOneVersionTwo.getVersionedName());
        
        // creates new version of page with the updated attachment
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "editor", attmtNames2);
        
        Page pageTwo = new Page("testAuthor", "testTwo", "pageText");
        try {
            dao.addUpdatedPageWithUpdatedAttachment(pageTwo, attmtOneVersionTwo);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testAddUpdatePageWithUpdatedAttachmentPageWrongVersion() throws Exception {
        // ie page two in the DAO, so three expected, but we give it page four, or page two
        MemoryDAO dao = new MemoryDAO();
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        Attachment attmtOneVersionTwo = new Attachment(attmtOne, "testEditor", new byte[]{4,5,6});
        Set<VersionedName> attmtNames2 = new HashSet<VersionedName>();
        attmtNames2.add(attmtOneVersionTwo.getVersionedName());
        
        // creates new version of page with the updated attachment
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "editor", attmtNames2);
        
        Page pageOneVersionFour = new Page(pageOneVersionThree, "editor", attmtNames2);
        
        try {
            dao.addUpdatedPageWithUpdatedAttachment(pageOneVersionFour, attmtOneVersionTwo);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testAddUpdatePageWithUpdatedAttachmentAttachmentNotThereAtAll() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Attachment attmtOneVersionTwo = new Attachment(attmtOne, "editor", new byte[]{4,5,6});
        
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOneVersionTwo.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "editor", attmtNames);
        
        try {
            // tries to update an attachment, but we forgot to add the first version...
            dao.addUpdatedPageWithUpdatedAttachment(pageOneVersionTwo, attmtOneVersionTwo);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testAddUpdatePageWithUpdatedAttachmentAttachmentWrongVersion() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames  = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        Attachment attmtOneVersionTwo = new Attachment(attmtOne, "editor", new byte[]{4,5,6});
        
        Attachment attmtOneVersionThree = new Attachment(attmtOneVersionTwo, "editor", new byte[]{7,8,9});
        
        Set<VersionedName> attmtNames3 = new HashSet<VersionedName>();
        attmtNames3.add(attmtOneVersionTwo.getVersionedName());
        
        // skipped adding version 2 of the attachment
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "editor", attmtNames3);
        
        
        try {
            // tries to update an attachment, but we forgot to add the first version...
            dao.addUpdatedPageWithUpdatedAttachment(pageOneVersionThree, attmtOneVersionThree);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testAddUpdatePageWithUpdatedAttachmentPageDoesNotListAttachment() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames  = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        Attachment attmtOneVersionTwo = new Attachment(attmtOne, "editor", new byte[]{4,5,6});
        Set<VersionedName> attmtNames2 = new HashSet<VersionedName>();
        attmtNames2.add(new VersionedName("notRight", 0));
        
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "editor", attmtNames2);
        
        try {
            dao.addUpdatedPageWithUpdatedAttachment(pageOneVersionThree, attmtOneVersionTwo);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
        
    }
    
    public void testAddUpdatePageWithUpdatedAttachmentPageHasMultipleRefsToAttachmenName() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOne = new Page("testAuthor", "testOne", "pageText");
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames  = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        Attachment attmtOneVersionTwo = new Attachment(attmtOne, "editor", new byte[]{4,5,6});
        attmtNames.add(attmtOneVersionTwo.getVersionedName()); // two versions of name in the set!
        
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "editor", attmtNames);
        
        try {
            dao.addUpdatedPageWithUpdatedAttachment(pageOneVersionThree, attmtOneVersionTwo);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testAddUpdatePageWithUpdatedAttachmentPagePageHasRefsToNonExistentAttachments() throws Exception {
        Page pageOne = new Page("testAuthor", "testOne", "testText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames  = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        Attachment attmtOneVersionTwo = new Attachment(attmtOne, "editor", new byte[]{4,5,6});
        attmtNames.remove(attmtOne.getVersionedName());
        attmtNames.add(attmtOneVersionTwo.getVersionedName()); // two versions of name in the set!
        attmtNames.add(new VersionedName("nonExistent", 0));
        
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "editor", attmtNames);
        
        try {
            dao.addUpdatedPageWithUpdatedAttachment(pageOneVersionThree, attmtOneVersionTwo);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testAddUpatePageWithUpdatedAttachmentPageHasRemovedRefs() throws Exception {
        // for example if it had attm1, attm2.. but the new version only refers to attm1

        Page pageOne = new Page("author", "testOne", "testText");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneV2 = new Page(pageOne, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneV2, attmtOne);
        
        Attachment attmtTwo = new Attachment("testAuthor", "attmtTwo", "application/octet-stream", new byte[] {4,5,6});
        attmtNames.add(attmtTwo.getVersionedName());
        
        Page pageOneV3 = new Page(pageOneV2, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneV3, attmtTwo);
        
        attmtNames.remove(attmtOne.getVersionedName()); // oops, shouldn't be doing this
        Attachment attmtTwoV2 = new Attachment(attmtTwo, "editor", new byte[]{1,2,3});
        attmtNames.remove(attmtTwo.getVersionedName());
        attmtNames.add(attmtTwoV2.getVersionedName());
        
        Page pageOneV4 = new Page(pageOneV3, "editor", attmtNames); // but missing ref to attmtOne
        
        try {
            dao.addUpdatedPageWithUpdatedAttachment(pageOneV4, attmtTwoV2);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    // generally, we need to ensure that the set of versioned names is identical
    // to that of the previous version, exception that this particular attachment
    // has been updated
    
    public void testIsLatestPageVersion() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOne = new Page("testAuthor", "pageOne", "testText");
        dao.addNewPage(pageOne);
        
        Page pageOneVersionTwo = new Page(pageOne, "testEditor", "more text");
        dao.addUpdatedPageWithNewText(pageOneVersionTwo);
        
        assertTrue(dao.isLatestPageVersion(pageOneVersionTwo));
        assertFalse(dao.isLatestPageVersion(pageOne));
    }
    
    
    public void testIsLastesPageVersionNonExistentPage() throws Exception { 
        MemoryDAO dao = new MemoryDAO();
        Page notStored = new Page("testAuthor", "notStored", "test");
        
        try {
            dao.isLatestPageVersion(notStored);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }

    }
    
    public void testIsLastePageVersionNull() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        try {
            dao.isLatestPageVersion(null);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals("page parameter is null", iae.getMessage());
        }
    }
    
    public void testPageExists() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOne = new Page("author", "pageOne", "text1");
        dao.addNewPage(pageOne);
        Page pageOneV2 = new Page(pageOne, "editor", "text2");
        dao.addUpdatedPageWithNewText(pageOneV2);
        
        assertTrue(dao.pageExists("pageOne"));
        assertFalse(dao.pageExists("pageTwo"));
    }
    
    public void testPageExistsNullString() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        try {
           dao.pageExists(null);
           fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals("pageName parameter is null", iae.getMessage());
        }
    }
    
    public void testPageVersionExists() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOne = new Page("author", "pageOne", "text1");
        dao.addNewPage(pageOne);
        Page pageOneV2 = new Page(pageOne, "editor", "text2");
        dao.addUpdatedPageWithNewText(pageOneV2);
        
        assertTrue(dao.pageVersionExists(pageOne.getVersionedName()));
        assertTrue(dao.pageVersionExists(pageOneV2.getVersionedName()));
        assertFalse(dao.pageVersionExists(new VersionedName("pageOne", 3)));
        assertFalse(dao.pageVersionExists(new VersionedName("pageTwo", 0)));
    }
    
    public void testPageVersionExistsNullParam() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        try {
            dao.pageVersionExists(null);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals("versionedName parameter is null", iae.getMessage());
        }
    }
    
    public void testAddUpdatedPageWithAttachmentRemoved() throws Exception {
        Page pageOne = new Page("testAuthor", "pageOne", "text");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames  = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        Attachment attmtOneV2 = new Attachment(attmtOne, "editor", new byte[] {4,5,6});
        Set<VersionedName> attmtNames2 = new HashSet<VersionedName>();
        attmtNames2.add(attmtOneV2.getVersionedName());
        
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "editor", attmtNames2);
        dao.addUpdatedPageWithUpdatedAttachment(pageOneVersionThree, attmtOneV2);
        
        Set<VersionedName> attmtNames3 = new HashSet<VersionedName>();
        Page pageOneVersionFour = new Page(pageOneVersionThree, "editor", attmtNames3);
        dao.addUpdatedPageWithAttachmentRemoved(pageOneVersionFour, attmtOneV2);
        
        Page retrieved = dao.getPage("pageOne");
        assertEquals(pageOneVersionFour, retrieved);
        
        Page retrieved3 = dao.getPageVersion(new VersionedName("pageOne", 2));
        assertEquals(pageOneVersionThree, retrieved3);
        assertEquals(1, retrieved3.getAttachmentVersionedNames().size());
        assertEquals(attmtOneV2.getVersionedName(), retrieved3.getAttachmentVersionedNames().iterator().next());
    }
    
    public void testAddUpdatedPageWithAttachmentRemovedNullPage() throws Exception {
        Page pageOne = new Page("testAuthor", "pageOne", "text");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames  = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        Attachment attmtOneV2 = new Attachment(attmtOne, "editor", new byte[] {4,5,6});
        Set<VersionedName> attmtNames2 = new HashSet<VersionedName>();
        attmtNames2.add(attmtOneV2.getVersionedName());
        
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "editor", attmtNames2);
        dao.addUpdatedPageWithUpdatedAttachment(pageOneVersionThree, attmtOneV2);
        
        Set<VersionedName> attmtNames3 = new HashSet<VersionedName>();
        Page pageOneVersionFour = new Page(pageOneVersionThree, "editor", attmtNames3);
        try {
            dao.addUpdatedPageWithAttachmentRemoved(null, attmtOneV2);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals("newVersion parameter is null", iae.getMessage());
        }
    }
    
    public void testAddUpdatedPageWithAttachmentRemovedNullAttachment() throws Exception {
        Page pageOne = new Page("testAuthor", "pageOne", "text");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames  = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        Attachment attmtOneV2 = new Attachment(attmtOne, "editor", new byte[] {4,5,6});
        Set<VersionedName> attmtNames2 = new HashSet<VersionedName>();
        attmtNames2.add(attmtOneV2.getVersionedName());
        
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "editor", attmtNames2);
        dao.addUpdatedPageWithUpdatedAttachment(pageOneVersionThree, attmtOneV2);
        
        Set<VersionedName> attmtNames3 = new HashSet<VersionedName>();
        Page pageOneVersionFour = new Page(pageOneVersionThree, "editor", attmtNames3);
        try {
            dao.addUpdatedPageWithAttachmentRemoved(pageOneVersionFour, null);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals("attachmentToDelete parameter is null", iae.getMessage());
        }
    }
    
    public void testAddUpdatedPageWithAttachmentRemovedPageNotLatestVersion() throws Exception {
        Page pageOne = new Page("testAuthor", "pageOne", "text");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames  = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        Attachment attmtOneV2 = new Attachment(attmtOne, "editor", new byte[] {4,5,6});
        Set<VersionedName> attmtNames2 = new HashSet<VersionedName>();
        attmtNames2.add(attmtOneV2.getVersionedName());
        
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "editor", attmtNames2);
        dao.addUpdatedPageWithUpdatedAttachment(pageOneVersionThree, attmtOneV2);
        
        Set<VersionedName> attmtNames3 = new HashSet<VersionedName>();
        Page pageOneVersionFour = new Page(pageOneVersionThree, "editor", attmtNames3);
        try {
            dao.addUpdatedPageWithAttachmentRemoved(pageOne, attmtOneV2);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testAddUpdatedPageWithAttachmentRemovedAttachmentNotInPreviousVersion() throws Exception {
        Page pageOne = new Page("testAuthor", "pageOne", "text");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames  = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneV2 = new Page(pageOne, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneV2, attmtOne);
        
        Attachment attmtTwo = new Attachment("testAuthor", "attmtTwo" , "application/octet-stream", new byte[] {1,2,3});
        attmtNames.add(attmtTwo.getVersionedName());
        
        Page pageOneV3 = new Page(pageOneV2, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneV3, attmtTwo);
        
        Attachment attmtOneV2 = new Attachment(attmtOne, "editor", new byte[]{4,5,6});
        attmtNames.remove(attmtOne.getVersionedName());
        attmtNames.add(attmtOneV2.getVersionedName());
        
        Page pageOneV4 = new Page(pageOneV3, "editor", attmtNames);
        dao.addUpdatedPageWithUpdatedAttachment(pageOneV4, attmtOneV2);
        
        // bit of trickery here. create a new version of pageOne without attmtOne, but pass in version one of it
        attmtNames.remove(attmtOneV2.getVersionedName());
        Page pageOneV5 = new Page(pageOneV4, "editor", attmtNames);
        
        try {
            dao.addUpdatedPageWithAttachmentRemoved(pageOneV5, attmtOne);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testAddUpdatedPageWithAttachmentRemovedPageStillContainsRefToAtmt() throws Exception {
        Page pageOne = new Page("testAuthor", "pageOne", "text");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames  = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneVersionTwo = new Page(pageOne, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneVersionTwo, attmtOne);
        
        Attachment attmtOneV2 = new Attachment(attmtOne, "editor", new byte[] {4,5,6});
        Set<VersionedName> attmtNames2 = new HashSet<VersionedName>();
        attmtNames2.add(attmtOneV2.getVersionedName());
        
        Page pageOneVersionThree = new Page(pageOneVersionTwo, "editor", attmtNames2);
        dao.addUpdatedPageWithUpdatedAttachment(pageOneVersionThree, attmtOneV2);
        
        Page pageOneVersionFour = new Page(pageOneVersionThree, "editor", attmtNames2); // still has ref to attmtOneV2
        try {
            dao.addUpdatedPageWithAttachmentRemoved(pageOneVersionFour, attmtOneV2);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testAddUpdatedPageWithAttachmentRemovedPageMissingRefsFromPreviousVersion() throws Exception {
        Page pageOne = new Page("testAuthor", "pageOne", "text");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames  = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneV2 = new Page(pageOne, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneV2, attmtOne);
        
        Attachment attmtTwo = new Attachment("testAuthor", "attmtTwo" , "application/octet-stream", new byte[] {1,2,3});
        attmtNames.add(attmtTwo.getVersionedName());
        
        Page pageOneV3 = new Page(pageOneV2, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneV3, attmtTwo);
        
        Attachment attmtOneV2 = new Attachment(attmtOne, "editor", new byte[]{4,5,6});
        attmtNames.remove(attmtOne.getVersionedName());
        attmtNames.add(attmtOneV2.getVersionedName());
        
        Page pageOneV4 = new Page(pageOneV3, "editor", attmtNames);
        dao.addUpdatedPageWithUpdatedAttachment(pageOneV4, attmtOneV2);
        
        attmtNames.remove(attmtOneV2.getVersionedName());
        attmtNames.remove(attmtTwo.getVersionedName()); // now missing attmt2!
        Page pageOneV5 = new Page(pageOneV4, "editor", attmtNames);
        
        try {
            dao.addUpdatedPageWithAttachmentRemoved(pageOneV5, attmtOne);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }

    }
    
    public void testAddUpdatedPageWithAttachmentRemovedPageHasMoreRefsThanItShould() throws Exception {
        Page pageOne = new Page("testAuthor", "pageOne", "text");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames  = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneV2 = new Page(pageOne, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneV2, attmtOne);
        
        Attachment attmtTwo = new Attachment("testAuthor", "attmtTwo" , "application/octet-stream", new byte[] {1,2,3});
        attmtNames.add(attmtTwo.getVersionedName());
        
        Page pageOneV3 = new Page(pageOneV2, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneV3, attmtTwo);
        
        Attachment attmtOneV2 = new Attachment(attmtOne, "editor", new byte[]{4,5,6});
        attmtNames.remove(attmtOne.getVersionedName());
        attmtNames.add(attmtOneV2.getVersionedName());
        
        Page pageOneV4 = new Page(pageOneV3, "editor", attmtNames);
        dao.addUpdatedPageWithUpdatedAttachment(pageOneV4, attmtOneV2);
        
        attmtNames.remove(attmtOneV2.getVersionedName());
        attmtNames.add(new VersionedName("attmtThree", 0)); // ha, one extra!
        Page pageOneV5 = new Page(pageOneV4, "editor", attmtNames);
        
        try {
            dao.addUpdatedPageWithAttachmentRemoved(pageOneV5, attmtOneV2);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }

    }
    
    public void testResolveAttachmentPageVN() throws Exception {
        Page pageOne = new Page ("testAuthor", "pageOne", "test");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneV2 = new Page(pageOne, "testEditor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneV2, attmtOne);
        
        Attachment retrieved = dao.getAttachment(pageOneV2.getVersionedName().getName(), attmtOne.getVersionedName());
        assertEquals(attmtOne, retrieved);
        assertSame(attmtOne, retrieved);
    }
    
    public void testResolveAttachmentPageVNPageNull() throws Exception {
        Page pageOne = new Page ("testAuthor", "pageOne", "test");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneV2 = new Page(pageOne, "testEditor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneV2, attmtOne);
        
        try {
            Attachment retrieved = dao.getAttachment(null, attmtOne.getVersionedName());
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertTrue(true);
        }
    }
    
    public void testResolveAttachmentPageVNVNNull() throws Exception {
        Page pageOne = new Page ("testAuthor", "pageOne", "test");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneV2 = new Page(pageOne, "testEditor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneV2, attmtOne);
        VersionedName nullVN = null;
        
        try {
            Attachment retrieved = dao.getAttachment(pageOneV2.getVersionedName().getName(), nullVN);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertTrue(true);
        }
    }
    
    public void testResolveAttachmentPageVNNoSuchPage() throws Exception {
        Page pageOne = new Page ("testAuthor", "pageOne", "test");
        MemoryDAO dao = new MemoryDAO();
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("testAuthor", "attmtOne", "application/octet-stream", new byte[] {1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneV2 = new Page(pageOne, "testEditor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneV2, attmtOne);
        
        Page anotherPage = new Page("testAuthor", "anotherPage", "test");
        Page anotherPageV2 = new Page(anotherPage, "editor", attmtNames);
        
        try {
            Attachment retrieved = dao.getAttachment(anotherPageV2.getVersionedName().getName(), attmtOne.getVersionedName());
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
        
//        Page pageOneV3 = new Page(pageOneV2, "editor", "some new text");
//        try {
//            Attachment retrieved = dao.getAttachment(pageOneV3.getVersionedName().getName(), attmtOne.getVersionedName());
//            fail("expected wiki exception");
//        }
//        catch (WikiException we) {
//            assertTrue(true);
//        }
    }
    
    public void testResolveAttachmentPageVNVNNotInPage() throws Exception {
        try {
            MemoryDAO dao = new MemoryDAO();
            Page pageOne = new Page("test", "pageOne", "text");
            dao.addNewPage(pageOne);
            dao.getAttachment(pageOne.getVersionedName().getName(), new VersionedName("blah", 0));
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testResolveAttachments() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOne = new Page("author", "pageOne", "test");
        dao.addNewPage(pageOne);
        
        Attachment attmtOne = new Attachment("author", "attmtOne", "application/octet-stream", new byte[]{1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOne.getVersionedName());
        
        Page pageOneV2 = new Page(pageOne, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneV2, attmtOne);
        
        Attachment attmtOneV2 = new Attachment(attmtOne, "editor", new byte[]{4,5,6});
        attmtNames.remove(attmtOne.getVersionedName());
        attmtNames.add(attmtOneV2.getVersionedName());
        
        Page pageOneV3 = new Page(pageOneV2, "editor", attmtNames);
        dao.addUpdatedPageWithUpdatedAttachment(pageOneV3, attmtOneV2);
        
        Attachment attmtTwo = new Attachment("author", "attmtTwo", "application/octet-stream", new byte[]{7,8,9});
        attmtNames.add(attmtTwo.getVersionedName());
        
        Page pageOneV4 = new Page(pageOneV3, "editor", attmtNames);
        dao.addUpdatedPageWithNewAttachment(pageOneV4, attmtTwo);
        
        Set<Attachment> resolvedAttmts = dao.getAttachments(pageOneV4);
        assertEquals(2, resolvedAttmts.size());
        boolean foundAttmtOneV2 = false;
        boolean foundAttmtTwo = false;
        for (Attachment attmt : resolvedAttmts) {
            if (attmt.equals(attmtOneV2)) {
                foundAttmtOneV2 = true;
            }
            if (attmt.equals(attmtTwo)) {
                foundAttmtTwo = true;
            }
        }
        assertTrue(foundAttmtOneV2 & foundAttmtTwo);
    }
    
    public void testResolveAttachmentsPageHasNone() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOne = new Page("author", "pageOne", "text");
        dao.addNewPage(pageOne);
        Set<Attachment> attmts = dao.getAttachments(pageOne);
        assertNotNull(attmts);
        assertTrue(attmts.isEmpty());
    }
    
    public void testResolveAttachmentsPageNull() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page pageOne = new Page("author", "pageOne", "text");
        dao.addNewPage(pageOne);
        
        try {
            Set<Attachment> attmts = dao.getAttachments(null);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertTrue(true);
        }
    }
    
    public void testResolveAttachmentsPageNotInStore() throws Exception {
        MemoryDAO dao = new MemoryDAO();
        Page aPage = new Page("author", "aPage", "test");
        try {
            dao.getAttachments(aPage);
            fail("expected wiki exception");
        }
        catch (WikiException we) {
            assertTrue(true);
        }
    }
    
    public void testReadWriteToFile() throws Exception {
        MemoryDAO origDao = new MemoryDAO();
        origDao.setAttachmentMapFileName("attachmentMap.dat");
        origDao.setPageNameMapFileName("pageNameMap.dat");
        
        // page, one attachment of which two versions
        Page pageOneV0 = new Page("author", "pageOne", "test1");
        origDao.addNewPage(pageOneV0);
        Attachment attmtOneV0 = new Attachment("author", "attmtOne", "application/octet-stream", new byte[]{1,2,3});
        Set<VersionedName> attmtNames = new HashSet<VersionedName>();
        attmtNames.add(attmtOneV0.getVersionedName());
        Page pageOneV1 = new Page(pageOneV0, "editor", attmtNames);
        origDao.addUpdatedPageWithNewAttachment(pageOneV1, attmtOneV0);
        attmtNames.remove(attmtOneV0.getVersionedName());
        Attachment attmtOneV1 = new Attachment(attmtOneV0, "an editor", new byte[]{1,4,3});
        attmtNames.add(attmtOneV1.getVersionedName());
        Page pageOneV2 = new Page(pageOneV1, "editor" ,attmtNames);
        origDao.addUpdatedPageWithUpdatedAttachment(pageOneV2, attmtOneV1);
        
        // page, two attachments, each with one version
        Page pageTwoV0 = new Page("bob", "pageTwo", "test2");
        origDao.addNewPage(pageTwoV0);
        Attachment attmtTwoV0 = new Attachment("pete", "attmtTwo", "application/octet-stream", new byte[] {1,2,4});
        attmtNames.remove(attmtOneV1.getVersionedName());
        attmtNames.add(attmtTwoV0.getVersionedName());
        Page pageTwoV1 = new Page(pageTwoV0, "editor", attmtNames);
        origDao.addUpdatedPageWithNewAttachment(pageTwoV1, attmtTwoV0);
        Attachment attmtThree = new Attachment("foo", "attmtThree", "application/octet-stream", new byte[]{5,6,7});
        attmtNames.add(attmtThree.getVersionedName());
        Page pageTwoV2 = new Page(pageTwoV1, "editor", attmtNames);
        origDao.addUpdatedPageWithNewAttachment(pageTwoV2, attmtThree);
        
        origDao.pageServiceStopped(); // should now write out....
        
        MemoryDAO newDao = new MemoryDAO();
        newDao.setAttachmentMapFileName("attachmentMap.dat");
        newDao.setPageNameMapFileName("pageNameMap.dat");
        
        newDao.pageServiceStarted();  // should read in the files we just wrote
        
        
    }
    
    public void testReadNoFilesSet() throws Exception {
        fail("not implemented");
    }
    
    public void testReadFileNotReadable() throws Exception {
        fail("not implemented");
    }
    
    public void testReadFileNonExistent() throws Exception {
        fail("not implemented");
    }
    
    public void testWriteFileNonExistent() throws Exception {
        fail("not implemented");   
    }
    
    public void testWriteFileNonWriteable() throws Exception {
        fail("not implemented");
    }
    
    public void testReadWriteDataInconsistency() throws Exception {
        fail("not implemented");
    }
                                                                   
}