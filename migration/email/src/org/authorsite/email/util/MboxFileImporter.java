package org.authorsite.email.util;

import java.io.File;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

import org.authorsite.email.EmailFolder;
import org.authorsite.email.JavamailAdapter;

public class MboxFileImporter {

	public static final Pattern FILE_PATTERN = Pattern.compile("(\\p{Alnum}+-)*\\p{Alnum}+");
		
	
	public void processFile(String fileName) throws Exception {
		assert fileName != null;
		
		if (!isValidName(fileName)) {
			throw new IllegalArgumentException("illegal file pattern");
		}
		
		// import mbox using Adapter
		File file = new File (fileName);
		URLName mboxUrl = new URLName("mstor:" + file.getCanonicalPath());
		EmailFolder folder = getFolderFromMbox(mboxUrl);

		setupFolderParents(folder, fileName);
		
	}
	
	
	protected void setupFolderParents(EmailFolder folder, String fileName) {
		
		String[] pathComponents = fileName.split("-");
		
		// work out its parents and path from the file naming convention
		
		folder.setName(pathComponents[pathComponents.length - 1]); // last one
		if ( pathComponents.length == 1 ) {
			folder.setParent(EmailFolder.ROOT);
		}
		else {
			EmailFolder[] parents = new EmailFolder[pathComponents.length - 1] ;
			for (int i = 0; i < (pathComponents.length - 1); i++ ) {
				parents[i] = new EmailFolder(pathComponents[i]);
			}
			for (int j = pathComponents.length - 2; j > 0; j--) {
				parents[j].setParent(parents[j-1]);
			}
			parents[0].setParent(EmailFolder.ROOT);
			folder.setParent(parents[pathComponents.length-2]);
		}
	}


	protected boolean isValidName(String fileName) {
		Matcher m = FILE_PATTERN.matcher(fileName);
		return m.matches();
	}
	
	private EmailFolder getFolderFromMbox(URLName mboxUrl) throws Exception {
		Session session = Session.getDefaultInstance(new Properties());

        Store store = session.getStore(mboxUrl);
        store.connect();

        // read messages from Inbox..
        Folder inbox = store.getDefaultFolder();
        inbox.open(Folder.READ_ONLY);

        JavamailAdapter a = new JavamailAdapter();
        EmailFolder emailFolder = a.convertFolder(inbox);
        
        store.close();
        
        return emailFolder;
	}

}
