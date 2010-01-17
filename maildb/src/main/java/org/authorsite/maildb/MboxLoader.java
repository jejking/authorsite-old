/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.authorsite.maildb;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

/**
 *
 * @author jejking
 */
public class MboxLoader {

    public static void main(String[] args) throws Exception {

        Properties properties = new Properties();
        // add optional configuration properties..

        Session session = Session.getDefaultInstance(properties);

        SimpleMailMessageFactory factory = new SimpleMailMessageFactory();

        List<SimpleMailMessage> allMessages = new LinkedList<SimpleMailMessage>();

        File dir = new File(args[0]);
        for (File mbox : dir.listFiles()) {
            Store store = session.getStore(new URLName("mstor:/" + mbox.getAbsolutePath()));
            store.connect();
            Folder theFolder = store.getDefaultFolder();
            theFolder.open(Folder.READ_ONLY);

            List<SimpleMailMessage> folder = factory.buildSimpleMailMessages(theFolder);
            theFolder.close(false);
            allMessages.addAll(folder);
            store.close();
        }
        System.out.println("Got " + allMessages.size() + " messages");
    }

}
