/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.authorsite.maildb;

import java.io.File;
import java.lang.Class;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jejking
 */
public class MboxLoader {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"application-context.xml"});
        MboxLoaderService service = context.getBean(MboxLoaderService.class);
        service.loadMboxesFromDir(args[0]);
    }

}
