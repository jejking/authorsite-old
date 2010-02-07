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
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;

/**
 *
 * @author jejking
 */
public class MboxLoaderService {

    private SimpleMailMessageDao simpleMailMessageDao;

    public void setSimpleMailMessageDao(SimpleMailMessageDao dao) {
        this.simpleMailMessageDao = dao;
    }

    public void loadMboxesFromDir(String dirName) throws Exception {

        this.simpleMailMessageDao.deleteAll();

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
            for (SimpleMailMessage message : folder) {
            try {
                this.simpleMailMessageDao.insertSimpleMailMessage(message);
            } catch (DuplicateKeyException dke) {
                // ignore
            } catch (DataAccessException dae) {
                System.err.println(dae);
                System.err.println(message);
            }
        }
        }
        
        

    }
}
