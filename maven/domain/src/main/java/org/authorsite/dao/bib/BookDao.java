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
import org.authorsite.domain.bib.Book;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author jejking
 */
public interface BookDao {

    public int countBooks() throws DataAccessException;
    
    public Book findById(long id) throws DataAccessException;
    
    public void deleteBook(Book Book) throws DataAccessException;
    
    public void saveBook(Book Book) throws DataAccessException;
    
    public Book updateBook(Book Book) throws DataAccessException;
    
    public List<Book> findAllBooks() throws DataAccessException;
    
    public List<Book> findAllBooks(int pageNumber, int pageSize) throws DataAccessException;
    
    public List<Book> findBooksByTitle(String title) throws DataAccessException;
    
    public List<Book> findBooksByTitleWildcard(String titleWildcard) throws DataAccessException;
    
    public List<Book> findBooksAfterDate(Date date) throws DataAccessException;
    
    public List<Book> findBooksBeforeDate(Date date) throws DataAccessException;
    
    public List<Book> findBooksBetweenDates(Date startDate, Date endDate) throws DataAccessException;
    
    public List<Book> findBooksWithAuthor(AbstractHuman author) throws DataAccessException;
    
    public List<Book> findBooksWithEditor(AbstractHuman editor) throws DataAccessException;
    
    public List<Book> findBooksWithAuthorOrEditor(AbstractHuman human) throws DataAccessException;
    
    public List<Book> findBooksWithPublisher(AbstractHuman publisher) throws DataAccessException;

}
