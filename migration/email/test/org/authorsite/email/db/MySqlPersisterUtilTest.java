package org.authorsite.email.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.authorsite.email.BinaryMessagePart;
import org.authorsite.email.EmailAddressing;
import org.authorsite.email.EmailAddressingType;
import org.authorsite.email.EmailFolder;
import org.authorsite.email.EmailMessage;
import org.authorsite.email.MessagePartContainer;
import org.authorsite.email.TextMessagePart;

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
		
		EmailAddressing from = new EmailAddressing("from@test.com", EmailAddressingType.FROM);
		EmailAddressing to = new EmailAddressing("Mr To", "to@test.com", EmailAddressingType.TO);
		m.addEmailAddressing(from);
		m.addEmailAddressing(to);
		return m;
		
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
		
		PreparedStatement delAddressings = con.prepareStatement("DELETE FROM addressings");
		delAddressings.executeUpdate();
		delAddressings.close();
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
		// exact dates at the microsecond level are a bit iffy at this level..
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

	public void testPersistMessageAddressing() throws Exception {
		EmailMessage m = new EmailMessage();
		m.setId(66);
		
		EmailAddressing to = new EmailAddressing("FirstName LastName", "firstname.lastname@test.com", EmailAddressingType.TO);
		m.addEmailAddressing(to);
		
		MySqlPersisterUtil util = new MySqlPersisterUtil();
		long id = util.persistMessageAddressing(to, m, con);
		
		PreparedStatement lookUp = con.prepareStatement("SELECT addressingType, address, personal, part_id FROM addressings WHERE id = ?");
		lookUp.setLong(1, id);
		
		ResultSet rs = lookUp.executeQuery();
		rs.next();
		assertEquals("To", rs.getString(1));
		assertEquals("firstname.lastname@test.com", rs.getString(2));
		assertEquals("FirstName LastName", rs.getString(3));
		assertEquals(66, rs.getLong(4));
		rs.close();
		lookUp.close();
		
		// try with addresses with null (e.g. personal name)
		EmailAddressing from = new EmailAddressing("test@test.com", EmailAddressingType.FROM);
		m.addEmailAddressing(from);
		long id2 = util.persistMessageAddressing(from, m, con);
		PreparedStatement lookUp2 = con.prepareStatement("SELECT addressingType, address, personal, part_id FROM addressings WHERE id = ?");
		lookUp2.setLong(1, id2);
		ResultSet rs2 = lookUp2.executeQuery();
		
		rs2.next();
		assertEquals("From", rs2.getString(1));
		assertEquals("test@test.com", rs2.getString(2));
		assertNull(rs2.getString(3));
		assertEquals(66, rs2.getLong(4));
		
		rs2.close();
		lookUp2.close();
		
		
	}
	
	public void testPersistMessageSimpleContent() throws Exception {
		EmailFolder C = this.createABCFolders();
		EmailMessage m = this.createTestPlainTextMessage();
		m.setParent(C);
		
		MySqlPersisterUtil util = new MySqlPersisterUtil();
		util.persistMessage(m, 1, con);
		
		PreparedStatement countAddresses = con.prepareStatement("select count(*) from addressings");
		ResultSet rs = countAddresses.executeQuery();
		rs.next();
		assertEquals(2, rs.getInt(1));
		rs.close();
		countAddresses.close();
	}
	
	public void testPersistTextMessagePart() throws Exception {
		TextMessagePart p = new TextMessagePart();
		p.setContent("this is text");
		p.setMimeType("text/plain");
		p.setFileName("test.txt");
		p.setDescription("some blurb");
		p.setDisposition("attachment");
		
		MessagePartContainer container = new MessagePartContainer();
		container.setId(66);
		
		MySqlPersisterUtil util = new MySqlPersisterUtil();
		long partId = util.persistTextMessagePart(container, p, 1, con);
		
		PreparedStatement findTextPartPs = con.prepareStatement("SELECT type, parent_id, " +
				"textContent, mimeType, fileName, description, disposition " +
				"FROM parts where id = ?");
		findTextPartPs.setLong(1, partId);
		
		ResultSet rs = findTextPartPs.executeQuery();
		rs.next();
		assertEquals("MimeBodyPart", rs.getString(1));
		assertEquals(66, rs.getLong(2));
		assertEquals("this is text", rs.getString(3));
		assertEquals("text/plain", rs.getString(4));
		assertEquals("test.txt", rs.getString(5));
		assertEquals("some blurb", rs.getString(6));
		assertEquals("attachment", rs.getString(7));
		rs.close();
	}
	
	public void testPersistBinaryMessagePart() throws Exception {
		BinaryMessagePart part = new BinaryMessagePart();
		part.setContent("this is some text".getBytes("UTF-8"));
		part.setFileName("binary.bin");
		part.setMimeType("application/octet-stream");
		part.setDescription("some binary encoded text");
		part.setDisposition("attachment");
		
		MessagePartContainer container = new MessagePartContainer();
		container.setId(66);
		
		MySqlPersisterUtil util = new MySqlPersisterUtil();
		long id = util.persistBinaryMessagePart(container, part, 1, con);
		
		PreparedStatement findBinaryPartPs = con.prepareStatement("SELECT type, parent_id, " +
				"binaryContent, mimeType, fileName, description, disposition " +
				"FROM parts where id = ?");
		findBinaryPartPs.setLong(1, id);
		
		ResultSet rs = findBinaryPartPs.executeQuery();
		rs.next();
		assertEquals("MimeBodyPart", rs.getString(1));
		assertEquals(66, rs.getLong(2));
		byte[] bytes = rs.getBytes(3);
		assertEquals("this is some text", new String(bytes, "UTF-8"));
		assertEquals("application/octet-stream", rs.getString(4));
		assertEquals("binary.bin", rs.getString(5));
		assertEquals("some binary encoded text", rs.getString(6));
		assertEquals("attachment", rs.getString(7));
		rs.close();
	}
	
	

}
