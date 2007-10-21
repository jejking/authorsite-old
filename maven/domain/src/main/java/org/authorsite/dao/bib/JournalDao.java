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
import org.authorsite.domain.bib.Journal;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author jejking
 */
public interface JournalDao {

    public int countJournals() throws DataAccessException;
    
    public Journal findById(long id) throws DataAccessException;
    
    public void deleteJournal(Journal journal) throws DataAccessException;
    
    public void saveJournal(Journal journal) throws DataAccessException;
    
    public Journal updateJournal(Journal journal) throws DataAccessException;
    
    public List<Journal> findAllJournals() throws DataAccessException;
    
    public List<Journal> findAllJournals(int pageNumber, int pageSize) throws DataAccessException;
    
    public List<Journal> findJournalsByTitle(String title) throws DataAccessException;
    
    public List<Journal> findJournalsByTitleWildcard(String titleWildcard) throws DataAccessException;
    
    public List<Journal> findJournalsAfterDate(Date date) throws DataAccessException;
    
    public List<Journal> findJournalsBeforeDate(Date date) throws DataAccessException;
    
    public List<Journal> findJournalsBetweenDates(Date startDate, Date endDate) throws DataAccessException;
    
    public List<Journal> findJournalsWithPublisher(AbstractHuman publisher) throws DataAccessException;
    
}
