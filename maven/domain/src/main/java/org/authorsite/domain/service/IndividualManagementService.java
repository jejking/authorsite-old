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
package org.authorsite.domain.service;

import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.authorsite.dao.IndividualDao;
import org.authorsite.domain.Individual;
import org.springframework.dao.DataAccessException;

/**
 * Core interface for manipulating Individuals (and their ACLs) in the database.
 * 
 * @author jking
 */
public interface IndividualManagementService {

    /**
     * Counts the individuals in the database.
     * 
     * @return count of instances
     * @throws DataAccessException
     * @see org.authorsite.dao.IndividualDao#countIndividuals()
     */
    public int countIndividuals() throws DataAccessException;

    /**
     * Permanently deletes individual from the database.
     * 
     * @param i
     * @throws DataAccessException
     * @see org.authorsite.dao.IndividualDao#delete(org.authorsite.domain.Individual)
     * @see IndividualAclManager#deleteIndividualAcl(Individual)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ACL_INDIVIDUAL_ADMIN" })
    public void delete(Individual i) throws DataAccessException;

    /**
     * Finds individual by primary key.
     * 
     * @param id
     * @return individual or <code>null</code> if none found.
     * @throws DataAccessException
     * @see org.authorsite.dao.IndividualDao#findById(long)
     */
    public Individual findById(long id) throws DataAccessException;

    /**
     * Finds all individuals with the given name.
     * 
     * @param name
     * @return list of individuals with the given name, may be empty if none
     *         found.
     * @throws DataAccessException
     * @see org.authorsite.dao.IndividualDao#findIndividualsByName(java.lang.String)
     */
    public List<Individual> findIndividualsByName(String name) throws DataAccessException;

    /**
     * Finds all individuals whose name and given names match the
     * parameters.
     * 
     * @param name
     * @param givenNames
     * @return list of matching individuals, may be empty if none found.
     * @throws DataAccessException
     * @see org.authorsite.dao.IndividualDao#findIndividualsByNameAndGivenNames(java.lang.String,
     *      java.lang.String)
     */
    public List<Individual> findIndividualsByNameAndGivenNames(String name, String givenNames)
	    throws DataAccessException;

    /**
     * Finds all individuals whose name and given name match the wildcard
     * parameters.
     * 
     * @param name
     * @param givenNames
     * @return list of matching individuals, may be empty if none found.
     * @throws DataAccessException
     * @see org.authorsite.dao.IndividualDao#findIndividualsByNameAndGivenNamesWildcard(java.lang.String,
     *      java.lang.String)
     */
    public List<Individual> findIndividualsByNameAndGivenNamesWildcard(String name, String givenNames)
	    throws DataAccessException;

    /**
     * Finds all individuals whose name matches the wildcard.
     * 
     * @param name
     * @return list of matching individuals, may be empty if none found.
     * @throws DataAccessException
     * @see org.authorsite.dao.IndividualDao#findIndividualsByNameWildcard(java.lang.String)
     */
    public List<Individual> findIndividualsByNameWildcard(String name) throws DataAccessException;

    /**
     * Finds all individuals known to the system.
     * 
     * @return list of all known individuals
     * @throws DataAccessException
     * @see IndividualDao#findAllIndividuals()
     */
    public List<Individual> findAllIndividuals() throws DataAccessException;
    
    /**
     * Returns "page" of individuals known to the system.
     * 
     * @param pageNumber
     * @param pageSize
     * @return "page" list
     * @throws DataAccessException
     * @see IndividualDao#findAllIndividuals(int,int)
     */
    public List<Individual> findAllIndividuals(int pageNumber, int pageSize) throws DataAccessException;
    
    
    /**
     * Saves <em>new</em> instance to the database. Sets the ACL to allow
     * any User with privilege <code>ROLE_EDITOR</code> to administer the
     * Individual.
     * 
     * @param i
     * @throws DataAccessException
     * @see org.authorsite.dao.IndividualDao#save(org.authorsite.domain.Individual)
     * @see IndividualAclManager#createIndividualAcl(Individual)
     */
    @Secured( { "ROLE_EDITOR", "ROLE_ADMINISTRATOR" })
    public void save(Individual i) throws DataAccessException;

    /**
     * Updates the persistent storage of an <code>Individual</code>
     * instance. A reference to it must already exist in the database.
     * 
     * @param i
     * @return updated individual
     * @throws DataAccessException
     * @see org.authorsite.dao.IndividualDao#update(org.authorsite.domain.Individual)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ACL_INDIVIDUAL_ADMIN" })
    public Individual update(Individual i) throws DataAccessException;

}