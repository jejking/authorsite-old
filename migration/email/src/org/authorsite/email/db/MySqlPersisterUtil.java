package org.authorsite.email.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.authorsite.email.EmailFolder;

public class MySqlPersisterUtil {

	private static final String SELECT_FOLDER_ID = "SELECT id FROM folders WHERE path = ?";
	
	private static final String INSERT_FOLDER = "INSERT INTO folders " +
			"(created_at, updated_at, parent_id, name, path) " +
			"VALUES " +
			"(NOW(), NOW(), ?, ?, ?);";
	
	private static final String SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID();";
	
	public void persistFolder(EmailFolder folder, Connection con) throws Exception {
	
		assert folder != null;
		assert con != null;
		
		if ( folder.getId() > 0 ) {
			throw new IllegalStateException("Folder already has db id: " + folder.getId());
		}
		
		// does the folder already exist in the db?
		if ( findFolder(folder, con) > -1 ) {
			
		}
		
		// insert the folder data
		
		// retrieve its new db id
		
	}

	protected long findFolder(EmailFolder folder, Connection con) throws SQLException {
		
		PreparedStatement findFolderPs = con.prepareStatement(MySqlPersisterUtil.SELECT_FOLDER_ID);
		findFolderPs.setString(1, folder.getPath());
		ResultSet rs = findFolderPs.executeQuery();
		if ( rs.next() ) {
			return rs.getLong(1);
		}
		else {
			return -1;
		}
	}
	
}
