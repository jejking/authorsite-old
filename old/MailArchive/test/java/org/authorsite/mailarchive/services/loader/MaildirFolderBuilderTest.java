/*
 * MaildirFolderBuilderTest.java, created on 08-Nov-2004 at 23:19:44
 * 
 * Copyright John King, 2004.
 *
 *  MaildirFolderBuilderTest.java is part of authorsite.org's MailArchive program.
 *
 *  MailArchive is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  MailArchive is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with VocabManager; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package org.authorsite.mailarchive.services.loader;

import java.io.*;
import java.util.*;

import javax.mail.*;

import junit.framework.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class MaildirFolderBuilderTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(MaildirFolderBuilderTest.class);
    }

    public final void testGetFolder() throws Exception {
        String mailDirFilePath = System.getProperty("java.io.tmpdir") + File.separator + "maildir";
        System.out.println(mailDirFilePath);
        Properties props = new Properties();
        props.put("mailDirFilePath", mailDirFilePath);
        FolderBuilder mailDirFolderBuilder = new MaildirFolderBuilder();
        mailDirFolderBuilder.setConnectionProperties(props);
        Folder testing = mailDirFolderBuilder.getFolder();
        
        Assert.assertEquals(18, testing.getMessageCount());
        
        mailDirFolderBuilder.clearUp();
    }

}
