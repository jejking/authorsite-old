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
import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.Thesis;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author jejking
 */
public interface ThesisDao {

    public int countTheses() throws DataAccessException;
    
    public Thesis findById(long id) throws DataAccessException;
    
    public void deleteThesis(Thesis Thesis) throws DataAccessException;
    
    public void saveThesis(Thesis Thesis) throws DataAccessException;
    
    public Thesis updateThesis(Thesis Thesis) throws DataAccessException;
    
    public List<Thesis> findAllTheses() throws DataAccessException;
    
    public List<Thesis> findAllTheses(int pageNumber, int pageSize) throws DataAccessException;
    
    public List<Thesis> findThesesByTitle(String title) throws DataAccessException;
    
    public List<Thesis> findThesesByTitleWildcard(String titleWildcard) throws DataAccessException;
    
    public List<Thesis> findThesesAfterDate(Date date) throws DataAccessException;
    
    public List<Thesis> findThesesBeforeDate(Date date) throws DataAccessException;
    
    public List<Thesis> findThesesBetweenDates(Date startDate, Date endDate) throws DataAccessException;
    
    public List<Thesis> findThesesWithAwardingBody(Collective awardingBody) throws DataAccessException;

    public List<Thesis> findThesesWithAuthor(Individual author) throws DataAccessException;
    
}
