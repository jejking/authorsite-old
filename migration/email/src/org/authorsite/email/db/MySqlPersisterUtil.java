package org.authorsite.email.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.authorsite.email.AbstractEmailPart;
import org.authorsite.email.BinaryMessagePart;
import org.authorsite.email.EmailAddressing;
import org.authorsite.email.EmailFolder;
import org.authorsite.email.EmailMessage;
import org.authorsite.email.MessagePartContainer;
import org.authorsite.email.TextMessagePart;

public class MySqlPersisterUtil {

	private static final String SELECT_FOLDER_ID = "SELECT id FROM folders WHERE path = ?";
	
	private static final String INSERT_FOLDER = "INSERT INTO folders " +
			"(created_at, updated_at, parent_id, name, path) " +
			"VALUES " +
			"(NOW(), NOW(), ?, ?, ?);";
	
	private static final String SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID();";
	
	private static final String INSERT_MESSAGE_CORE = 
		"INSERT INTO parts " +
		"(created_at, updated_at, type, subject, sentDate, receivedDate, inReplyTo, msgReferences, msgId, textContent, folder_id, folder_position) " +
		"VALUES " +
		"(NOW(), NOW(), 'Message', ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	private static final String INSERT_ADDRESSING = 
		"INSERT INTO addressings " +
		"(created_at, updated_at, addressingType, address, personal, part_id) " +
		"VALUES " +
		"(NOW(), NOW(), ?, ?, ?, ?);";
    
    private static final String INSERT_MULTIPART =
        "INSERT INTO parts " +
        "(created_at, updated_at, type, parent_id, multipartOrder) " +
        "VALUES " +
        "(NOW(), NOW(), 'MimeMultipart', ?, ?)";
    
    private static final String INSERT_TEXT_PART =
        "INSERT INTO parts " +
        "(created_at, updated_at, type, parent_id, textContent, mimeType, fileName, description, disposition) " +
        "VALUES " +
        "(NOW(), NOW(), 'MimeBodyPart', ?, ?, ?, ?, ?, ?);";
    
    private static final String INSERT_BINARY_PART = 
        "INSERT INTO parts " +
        "(created_at, updated_at, type, parent_id, binaryContent, mimeType, fileName, description, disposition) " +
        "VALUES " +
        "(NOW(), NOW(), 'MimeBodyPart', ?, ?, ?, ?, ?, ?);";
    
	
	public void persistFolder(EmailFolder folder, Connection con) throws Exception {
	
		assert folder != null;
		assert con != null;
		
		if ( folder.getId() > 0 ) {
			throw new IllegalStateException("Folder already has db id: " + folder.getId());
		}
		
		// does the folder already exist in the db, if not create it and its hierarchy
		if ( findFolder(folder, con) == -1 ) {
			createFolderHierarchyInDb(folder, con);
		}
		
		// persist all its messages
		int pos = 0;
		for ( EmailMessage message : folder.getChildMessages() ) {
			pos++;
			persistMessage(message, pos, con);
		}
		
		// we do not anticipate child folders at this stage given our input...
		
	}

	protected void persistMessage(EmailMessage message, int pos, Connection con) throws SQLException {
		// persist message skeleton first
		persistMessageCore(message, pos, con);
		for (EmailAddressing addressing: message.getEmailAddressingContainer().getAddressings()) {
			persistMessageAddressing(addressing, message, con);
		}
        if ( message.getMultipartContainer() != null ) {
            this.persistMultipartContainerAsChildOfMessage(message, message.getMultipartContainer(), con);
        }
	}
	
	protected long persistMultipartContainerAsChildOfMessage(EmailMessage message, MessagePartContainer multipartContainer, Connection con) throws SQLException {
	    PreparedStatement insertMultipartContainerPs = con.prepareStatement(MySqlPersisterUtil.INSERT_MULTIPART);
        insertMultipartContainerPs.setLong(1, message.getId());
        insertMultipartContainerPs.setLong(2, 1);
        insertMultipartContainerPs.executeUpdate();
        insertMultipartContainerPs.close();
        long id = this.getLatestId(con);
        multipartContainer.setId(id);
        
        persistMultipartChildren(multipartContainer, con);
        
        return id;
    }

    private void persistMultipartChildren(MessagePartContainer multipartContainer, Connection con) throws SQLException {
        int i = 0;
        for ( AbstractEmailPart part : multipartContainer.getChildren() ) {
            i++;
            if ( part instanceof TextMessagePart ) {
                persistTextMessagePart(multipartContainer, (TextMessagePart) part, i, con);
            }
            if ( part instanceof BinaryMessagePart ) {
                persistBinaryMessagePart(multipartContainer, (BinaryMessagePart) part, i, con);
            }
            if ( part instanceof MessagePartContainer ) {
                persistMultipartContainerAsChildOfMultipart(multipartContainer, (MessagePartContainer) part, i, con);
            }
        }
    }
    
    protected long persistMultipartContainerAsChildOfMultipart(MessagePartContainer multipartContainer, MessagePartContainer childContainer, int i, Connection con) throws SQLException {
        PreparedStatement insertMultipartContainerPs = con.prepareStatement(MySqlPersisterUtil.INSERT_MULTIPART);
        insertMultipartContainerPs.setLong(1, multipartContainer.getId());
        insertMultipartContainerPs.setLong(2, 1);
        insertMultipartContainerPs.executeUpdate();
        insertMultipartContainerPs.close();
        long id = this.getLatestId(con);
        childContainer.setId(id);
        
        persistMultipartChildren(childContainer, con);
        
        return id;
    }

  

	protected long persistBinaryMessagePart(MessagePartContainer multipartContainer, BinaryMessagePart part, int i, Connection con) throws SQLException {
        PreparedStatement insertBinaryPartPs = con.prepareStatement(MySqlPersisterUtil.INSERT_BINARY_PART);
        insertBinaryPartPs.setLong(1, multipartContainer.getId());
        insertBinaryPartPs.setBytes(2, part.getContent());
        insertBinaryPartPs.setString(3, part.getMimeType());
        insertBinaryPartPs.setString(4, part.getFileName());
        insertBinaryPartPs.setString(5, part.getDescription());
        insertBinaryPartPs.setString(6, part.getDisposition());
        insertBinaryPartPs.executeUpdate();
        insertBinaryPartPs.close();
        
        long id = this.getLatestId(con);
        part.setId(id);
        return id;
    }

    protected long persistTextMessagePart(MessagePartContainer multipartContainer, TextMessagePart part, int i, Connection con) throws SQLException {
        PreparedStatement insertTextPartPs = con.prepareStatement(MySqlPersisterUtil.INSERT_TEXT_PART);
        insertTextPartPs.setLong(1, multipartContainer.getId());
        insertTextPartPs.setString(2, part.getContent());
        insertTextPartPs.setString(3, part.getMimeType());
        insertTextPartPs.setString(4, part.getFileName());
        insertTextPartPs.setString(5, part.getDescription());
        insertTextPartPs.setString(6, part.getDisposition());
        insertTextPartPs.executeUpdate();
        insertTextPartPs.close();
        long id = this.getLatestId(con);
        part.setId(id);
        return id;
    }

    protected long persistMessageAddressing(EmailAddressing addressing, EmailMessage message, Connection con) throws SQLException {
		PreparedStatement insertAddressingPs = con.prepareStatement(MySqlPersisterUtil.INSERT_ADDRESSING);
		insertAddressingPs.setString(1, addressing.getType().toString());
		insertAddressingPs.setString(2, addressing.getEmailAddress());
		insertAddressingPs.setString(3, addressing.getPersonalName());
		insertAddressingPs.setLong(4, message.getId());
		insertAddressingPs.executeUpdate();
		
		return this.getLatestId(con);
	}

	protected void persistMessageCore(EmailMessage message, int pos, Connection con) throws SQLException {
		PreparedStatement insertMessageCorePs = con.prepareStatement(MySqlPersisterUtil.INSERT_MESSAGE_CORE);
		insertMessageCorePs.setString(1, message.getSubject());
		if( message.getSentDate() != null ) {
			insertMessageCorePs.setDate(2, new java.sql.Date(message.getSentDate().getTime()));
		}
		else {
			insertMessageCorePs.setDate(2, null);
		}
		if ( message.getReceivedDate() != null ) {
			insertMessageCorePs.setDate(3, new java.sql.Date(message.getReceivedDate().getTime()));
		}
		else {
			insertMessageCorePs.setDate(3, null);
		}
		insertMessageCorePs.setString(4, message.getInReplyTo());
		if ( message.getMsgReferences() != null && !(message.getMsgReferences().isEmpty())) {
			StringBuilder b = new StringBuilder();
			for ( String msgRef : message.getMsgReferences() ) {
				b.append(msgRef);
				b.append(" ");
			}
			insertMessageCorePs.setString(5, b.toString().trim());
		}
		else {
			insertMessageCorePs.setString(5, null);
		}
		insertMessageCorePs.setString(6, message.getMsgId());
		insertMessageCorePs.setString(7, message.getContent());
		insertMessageCorePs.setLong(8, message.getParent().getId());
		insertMessageCorePs.setInt(9, pos);
		
    		insertMessageCorePs.executeUpdate();
		
	    long id = this.getLatestId(con);
	    message.setId(id);
	    
	    insertMessageCorePs.close();
	    
	}

	protected void createFolderHierarchyInDb(EmailFolder folder, Connection con) throws SQLException {
		// we will put the parents into a list, then check each element exists
		List<EmailFolder> pathList = convertParentsToList(folder);
		for (EmailFolder pathFolder : pathList ) {
			long folderIdInDB = findFolder(pathFolder, con);
			if ( folderIdInDB == -1) {
				pathFolder.setId(createSingleFolderInDb(pathFolder, con));
			}
			else {
				pathFolder.setId( folderIdInDB);
			}
		}
	}
	
	

	protected long createSingleFolderInDb(EmailFolder pathFolder, Connection con) throws SQLException {
		
		if ( pathFolder == EmailFolder.ROOT ) {
			return EmailFolder.ROOT.getId();
		}
		
		PreparedStatement insertFolderPs = con.prepareStatement(MySqlPersisterUtil.INSERT_FOLDER);
		insertFolderPs.setLong(1, pathFolder.getParent().getId());
		insertFolderPs.setString(2, pathFolder.getName());
		insertFolderPs.setString(3, pathFolder.getPath());
		insertFolderPs.executeUpdate();
		
	    long id = getLatestId(con);
		return id;
	}

	

	protected List<EmailFolder> convertParentsToList(EmailFolder folder) {
		ArrayList<EmailFolder> hierarchy = new ArrayList<EmailFolder>();
		hierarchy.add(folder);
		EmailFolder parent = folder.getParent();
		hierarchy.add(0, parent);
		while (parent.getParent() != null) {
			parent = parent.getParent();
			hierarchy.add(0, parent);
		}
		return hierarchy;
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
	
	private long getLatestId(Connection con) throws SQLException {
		PreparedStatement getIdPs = con.prepareStatement(MySqlPersisterUtil.SELECT_LAST_INSERT_ID);
	    ResultSet rs = getIdPs.executeQuery();
	    rs.next();
	    long id = rs.getLong(1);
	    rs.close();
	    getIdPs.close();
		return id;
	}
}
