/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.authorsite.simplemailarchive.loader;

import java.io.File;
import java.util.List;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

import org.authorsite.simplemailarchive.domain.SimpleMailMessage;
import org.authorsite.simplemailarchive.domain.SimpleMailMessageFactory;

/**
 *
 * @author jejking
 */
public class MboxLoaderService {

    public void loadMboxesFromDir(String dirName) throws Exception {

        Properties properties = new Properties();
        // add optional configuration properties..

        Session session = Session.getDefaultInstance(properties);

        SimpleMailMessageFactory factory = new SimpleMailMessageFactory();


        File dir = new File(dirName);
        for (File mbox : dir.listFiles()) {
            System.out.println("Loading messages from: " + mbox);
            System.out.println("=============================================");
            Store store = session.getStore(new URLName("mstor:/" + mbox.getAbsolutePath()));
            store.connect();
            Folder theFolder = store.getDefaultFolder();
            theFolder.open(Folder.READ_ONLY);

            List<SimpleMailMessage> folder = factory.buildSimpleMailMessages(theFolder);
            theFolder.close(false);

            store.close();

        }

    }
}
