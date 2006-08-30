package org.authorsite.email.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.authorsite.email.EmailFolder;

import junit.framework.TestCase;

public class MySqlPersisterUtilTest extends TestCase {

	Connection con;
	
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
		con.close();
	}

	public void testFindFolder() throws Exception {
		MySqlPersisterUtil util = new MySqlPersisterUtil();
		EmailFolder folderA = new EmailFolder("A");
		folderA.setParent(EmailFolder.ROOT);
		assertTrue(util.findFolder(folderA, con) > 1);
	}
	
}
