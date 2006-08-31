package org.authorsite.email.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.authorsite.email.EmailFolder;
import org.authorsite.email.EmailMessage;

import junit.framework.TestCase;

public class MySqlPersisterUtilTest extends TestCase {

	Connection con;
	
	private EmailFolder createABCFolders() {
		EmailFolder C = new EmailFolder("C");
		EmailFolder B = new EmailFolder("B");
		C.setParent(B);
		EmailFolder A = new EmailFolder("A");
		B.setParent(A);
		A.setParent(EmailFolder.ROOT);
		return C;
	}
	
	private EmailMessage createTestPlainTextMessage() {
		EmailMessage m = new EmailMessage();
		m.setParent(createABCFolders());
		m.setContent("this is the content");
		m.setMsgId("test1");
		m.setInReplyTo("test0");
		m.setSubject("testing is the subject");
		m.setSentDate(new Date(1157020091283L));
		m.setReceivedDate(new Date(1157020091283L));
		List<String> msgReferences = new ArrayList<String>();
		msgReferences.add("test0");
		m.setMsgReferences(msgReferences);
		return m;
		// TODO addresses...
	}	
	
	@Override
	protected void setUp() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		
		String url = "jdbc:mysql://localhost:3306/authorsite?characterEncoding=utf8";
		String username = "test";
		String password = "test";
		
		con = DriverManager.getConnection(url, username, password);
		
		PreparedStatement insertTestFolderA = con.prepareStatement("INSERT INTO folders " +
			"(created_at, updated_at, parent_id, name, path) " +
			"VALUES " +
			"(NOW(), NOW(), ?, ?, ?);");
		insertTestFolderA.setLong(1, 1);
		insertTestFolderA.setString(2, "A");
		insertTestFolderA.setString(3, "/A");
		insertTestFolderA.executeUpdate();
		
	}

	@Override
	protected void tearDown() throws Exception {
		PreparedStatement delFoldersPs = con.prepareStatement("DELETE FROM folders WHERE id > 1;");
		delFoldersPs.executeUpdate();
		delFoldersPs.close();
		
		PreparedStatement delPartsPs = con.prepareStatement("DELETE FROM parts");
		delPartsPs.executeUpdate();
		delFoldersPs.close();
		con.close();
	}

	public void testFindFolder() throws Exception {
		MySqlPersisterUtil util = new MySqlPersisterUtil();
		EmailFolder folderA = new EmailFolder("A");
		folderA.setParent(EmailFolder.ROOT);
		assertTrue(util.findFolder(folderA, con) > 1);
	}
	
	public void testConvertParentsToList() {
		EmailFolder C = createABCFolders();
		
		List<EmailFolder> hierarchyList = new MySqlPersisterUtil().convertParentsToList(C);
		assertTrue(hierarchyList.size() == 4);
		assertSame(hierarchyList.get(0), EmailFolder.ROOT);
		assertEquals("A", hierarchyList.get(1).getName());
		assertEquals("B", hierarchyList.get(2).getName());
		assertEquals("C", hierarchyList.get(3).getName());
	}

	public void testCreateSingleFolderInDb() throws Exception {
		EmailFolder C = createABCFolders();
		
		MySqlPersisterUtil util = new MySqlPersisterUtil();
		long id = util.createSingleFolderInDb(C, con);
		
		assertEquals(id, util.findFolder(C, con));
	}
	
	public void testCreateFolderHierarchyInDb() throws Exception {
		EmailFolder C = createABCFolders();
		MySqlPersisterUtil util = new MySqlPersisterUtil();
		
		// note, / and A are there already, B + C must be created...
		
		util.createFolderHierarchyInDb(C, con);
		
		assertTrue(util.findFolder(C, con) > -1);
		assertTrue(util.findFolder(C.getParent(), con) > -1); 
	}
	
	public void testPersistMessageCore() throws Exception {
		EmailFolder C = createABCFolders();
		C.setId(66); // any old number
		EmailMessage m = createTestPlainTextMessage();
		m.setParent(C);
		
		MySqlPersisterUtil util = new MySqlPersisterUtil();
		util.persistMessageCore(m, 1, con);
		
		long id = m.getId();
		assertTrue( id > 0 );
		
		/*
		 * id                  INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at		    DATETIME,
    updated_at		    DATETIME,
    lock_version	    INTEGER NOT NULL DEFAULT 0,
    type                ENUM (  'Message',
                                'MimeMultipart',
                                'MimeBodyPart' ) NOT NULL,
    subject             VARCHAR(255),
    sentDate            DATETIME,
    receivedDate        DATETIME,
    inReplyTo           VARCHAR(255),
    msgReferences       VARCHAR (255),
    msgId               VARCHAR(255),
    textContent         TEXT,
    binaryContent       BLOB,
    mimeType            VARCHAR(255),
    parent_id           INTEGER NOT NULL REFERENCES parts(id),
    multipartOrder      INTEGER,
    folder_id           INTEGER REFERENCES folders(id),
    folder_position     INTEGER
		 */
		PreparedStatement lookup = con.prepareStatement(
				"SELECT type, subject, sentDate, " +
				"receivedDate, inReplyTo, msgReferences, " +
				"msgId, textContent, folder_id, folder_position " +
				"FROM " +
				"parts " +
				"WHERE " +
				"id = ?");
		lookup.setLong(1, id);
		ResultSet rs = lookup.executeQuery();
		rs.next();
		assertEquals("Message", rs.getString(1));
		assertEquals("testing is the subject", rs.getString(2));
		// dates are a bit iffy at this level..
//		assertEquals(new java.sql.Date(1157020091283L), rs.getDate(3));
//		assertEquals(new java.sql.Date(1157020091283L), rs.getDate(4));
		assertEquals("test0", rs.getString(5));
		assertEquals("test0", rs.getString(6));
		assertEquals("test1", rs.getString(7));
		assertEquals("this is the content", rs.getString(8));
		assertEquals(66, rs.getLong(9));
		assertEquals(1, rs.getInt(10));
		rs.close();
		lookup.close();
	}


}
