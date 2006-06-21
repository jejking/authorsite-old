/*
 * DataLoader.java, created on 07-Oct-2004 at 16:14:11
 * 
 * Copyright John King, 2004.
 *
 *  DataLoader.java is part of authorsite.org's MailArchive program.
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
package org.authorsite.mailarchive.services.storage;

import org.hibernate.*;

import org.apache.log4j.*;
import org.authorsite.mailarchive.model.impl.*;

/**
 * Runs some code to load up entities into the database so we can test things, notably Hibernate queries.
 * 
 * @author jejking
 * @version $Revision: 1.2 $
 */
public class DataLoadRunner {

    private static Logger logger = Logger.getLogger(DataLoadRunner.class);
    
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
        // each arg is assumed to be a string denoted a class name of a HibernateDataLoader to run
        for (int i = 0; i < args.length; i++) {
            try {
                HibernateDataLoader loader = (HibernateDataLoader) DataLoadRunner.class.getClassLoader().loadClass(args[i]).newInstance();
                loader.loadData(sessionFactory);
            }
            catch (Exception e) {
                logger.error(e);
            }
        }
    }
}
