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

package org.authorsite.dao;

import java.util.List;

import org.authorsite.domain.Individual;
import org.springframework.dao.DataAccessException;

/**
 * Defines functionality to manipulate persistent {@link Individual} entities.
 * @author jejking
 */
public interface IndividualDao {

    /**
     * Loads single individual by primary key.
     * 
     * @param id
     * @return individual if found, else <code>null</code>
     * @throws DataAccessException
     */
    public Individual findById(long id) throws DataAccessException;

    /**
     * Saves <em>new</em> instance to the database.
     * 
     * @param i
     * @throws DataAccessException
     */
    public void save(Individual i) throws DataAccessException;

    /**
     * Updates the persistent storage of an <code>Individual</code> instance.
     * A reference to it must already exist in the database.
     * 
     * @param i
     * @return individual
     * @throws DataAccessException
     */
    public Individual update(Individual i) throws DataAccessException;

    /**
     * Permanently removes the individual from the database.
     * 
     * @param i
     * @throws DataAccessException
     */
    public void delete(Individual i) throws DataAccessException;

    /**
     * Counts the number of <code>Individual</code> instances in the database.
     * 
     * @return count of individuals
     * @throws DataAccessException
     */
    public int countIndividuals() throws DataAccessException;

    /**
     * Finds all individuals with the given name.
     * 
     * @param name
     * @return list of individuals with the given name, may be empty if none found
     * @throws DataAccessException
     */
    public List<Individual> findIndividualsByName(String name)
	    throws DataAccessException;

    /**
     * Finds all individuals whose name matches the wildcard.
     * 
     * @param name
     * @return list of individuals whose name matches the wildcard, may be empty if none found 
     * @throws DataAccessException
     */
    public List<Individual> findIndividualsByNameWildcard(String name)
	    throws DataAccessException;

    /**
     * Finds all individuals whose name and given names match the parameters.
     * 
     * @param name
     * @param givenNames
     * @return list of individuals whose name and given names match the parameters, may be empty if none found.
     * @throws DataAccessException
     */
    public List<Individual> findIndividualsByNameAndGivenNames(String name,
	    String givenNames) throws DataAccessException;

    /**
     * Finds all individuals whose name and given name match the wildcard parameters.
     * 
     * @param name
     * @param givenNames
     * @return list of individuals whose name and given names match the wildcard parameters, may be empty if none found.
     * @throws DataAccessException
     */
    public List<Individual> findIndividualsByNameAndGivenNamesWildcard(
	    String name, String givenNames) throws DataAccessException;
    
    /**
     * Returns all individuals known to the system.
     *
     * @return list of all individuals in the system
     */
    public List<Individual> findAllIndividuals() throws DataAccessException;
    
    /**
     * Returns "page" of list of all individuals known to the system.
     *
     * @return page of list of all individuals known to the system.
     */
    public List<Individual> findAllIndividuals(int pageNumber, int pageSize)
            throws DataAccessException;

}
