/**
 * This file is part of the authorsite application.
 *
 * The authorsite application is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * The authorsite application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the authorsite application; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 */

package org.authorsite.dao.bib;

import java.util.Date;
import java.util.List;
import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.bib.AbstractWork;
import org.authorsite.domain.bib.WorkProducerType;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author jking
 */
public interface GenericWorkDao {
    
    public int countWorks() throws DataAccessException;
    
    public AbstractWork findWorkById(long id) throws DataAccessException;
    
    public List<AbstractWork> findAllWorks() throws DataAccessException;
    
    public List<AbstractWork> findAllWorks(int pageNumber, int pageSize) throws DataAccessException;
    
    public List<AbstractWork> findWorkByTitle(String title) throws DataAccessException;
    
    public List<AbstractWork> findWorkByTitleWildcard(String title) throws DataAccessException;
    
    public List<AbstractWork> findWorksWithProducer(AbstractHuman producer) throws DataAccessException;
    
    public List<AbstractWork> findWorksWithProducerOfType(AbstractHuman producer, WorkProducerType workProducerType) throws DataAccessException;
    
    public List<AbstractWork> findWorksBeforeDate(Date date) throws DataAccessException;
    
    public List<AbstractWork> findWorksAfterDate(Date date) throws DataAccessException;
    
    public List<AbstractWork> findWorksBetweenDates(Date startDate, Date endDate) throws DataAccessException;
    
}
