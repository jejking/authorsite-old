/*
 * AbstractPersistenceTest.java, created on 09-Aug-2004 at 20:52:00
 * 
 * Copyright John King, 2004.
 *
 *  AbstractPersistenceTest.java is part of authorsite.org's MailArchive program.
 *
 *  VocabManager is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  VocabManager is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with VocabManager; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package org.authorsite.mailarchive.model.impl;

import org.hibernate.*;
import junit.framework.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.3 $
 */
public abstract class AbstractPersistenceTestCase extends TestCase {

    protected SessionFactory sessionFactory;
    
    public AbstractPersistenceTestCase(String name) throws Exception {
        super(name);
        sessionFactory = HibernateSessionFactory.getSessionFactory();
    }
}
