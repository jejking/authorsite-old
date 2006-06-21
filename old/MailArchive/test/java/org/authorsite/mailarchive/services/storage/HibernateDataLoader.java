/*
 * HibernateDataLoader.java, created on 07-Oct-2004 at 16:19:09
 * 
 * Copyright John King, 2004.
 *
 *  HibernateDataLoader.java is part of authorsite.org's MailArchive program.
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

/**
 * 
 * @author jejking
 * @version $Revision: 1.2 $
 */
public interface HibernateDataLoader {

    public void loadData(SessionFactory sessionFactory) throws HibernateException;
    
}
