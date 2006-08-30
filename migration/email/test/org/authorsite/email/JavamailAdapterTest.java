package org.authorsite.email;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;

import junit.framework.TestCase;

public class JavamailAdapterTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testConvertFolder() throws Exception {
        
        Session session = Session.getDefaultInstance(new Properties());

        Store store = session.getStore(new URLName("mstor:mboxes/oldTest1"));
        store.connect();

        // read messages from Inbox..
        Folder inbox = store.getDefaultFolder();
        inbox.open(Folder.READ_ONLY);

        JavamailAdapter a = new JavamailAdapter();
        a.convertFolder(inbox);
        
        store.close();
    }

    public void testConvertMessage() {
        fail("Not yet implemented");
    }

    public void testConvertEmailAddress() throws Exception {
        InternetAddress one = new InternetAddress();
        one.setAddress("test@test.com");
        one.setPersonal("Test Test");
        
        EmailAddressing oneA = new JavamailAdapter().convertEmailAddress(one);
        assertEquals(one.getAddress(), oneA.getEmailAddress());
        assertEquals(one.getPersonal(), oneA.getPersonalName());
    }

    public void testConvertMultipart() {
        fail("Not yet implemented");
    }

}
