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
import org.authorsite.dao.CollectiveDao;
import org.authorsite.domain.Collective;
import org.springframework.dao.DataAccessException;

/**
 * Core interface for manipulating Individuals (and their ACLs) in the database.
 * 
 * @author jejking
 */
public interface CollectiveManagementService {

    /**
     * Counts the number of collective instances in the database.
     * 
     * @return the count of collectives
     * @throws DataAccessException
     * @see CollectiveDao#countCollectives()
     */
    public int countCollectives() throws DataAccessException;

    /**
     * Permanently removes the collective from the database.
     * 
     * @param c
     * @throws DataAccessException
     * @see CollectiveDao#delete(Collective)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public void deleteCollective(Collective c) throws DataAccessException;

    /**
     * Loads single collective by primary key.
     * 
     * @param id
     * @return collective or <code>null</code>
     * @throws DataAccessException
     * @see CollectiveDao#findById
     */
    public Collective findById(long id) throws DataAccessException;

    /**
     * Finds all collectives with the given name.
     * 
     * @param name
     * @return list of collectives with the name, may be empty if none found.
     * @throws DataAccessException
     * @see CollectiveDao#findCollectivesByName(String)
     */
    public List<Collective> findCollectivesByName(String name) throws DataAccessException;

    /**
     * Finds all collectives whose name matches the supplied wildcard.
     * 
     * @param name
     * @return list of collectives matching the wildcard, may be empty if none found.
     * @throws DataAccessException
     * @see CollectiveDao#findCollectivesByNameWildcard(String)
     */
    public List<Collective> findCollectivesByNameWildcard(String name) throws DataAccessException;

    /**
     * Finds all collectives associated with the given place name.
     * 
     * @param placeName
     * @return list of collectives associated with the place, may be empty if none found.
     * @throws DataAccessException
     * @see CollectiveDao#findCollectivesByPlace(String)
     */
    public List<Collective> findCollectivesByPlace(String placeName) throws DataAccessException;

    /**
     * Finds all collectives associated with a place matching the wildcard. 
     * 
     * @param placeName
     * @return list of collectives associated with places matching the wildcard, may be empty if none found.
     * @throws DataAccessException
     * @see CollectiveDao#findCollectivesByPlaceWildcard(String)
     */
    public List<Collective> findCollectivesByPlaceWildcard(String placeName) throws DataAccessException;

    /**
     * Returns list of all collectives known to the system.
     *  
     * @return list of all the collectives in the system
     * @throws DataAccessException
     * @see CollectiveDao#findAllCollectives()
     */
    public List<Collective> findAllCollectives() throws DataAccessException;

    /**
     * Returns "page" of the list of all collectives known to the system.
     * 
     * @param pageNumber
     * @param pageSize
     * @return "page" list
     * @throws DataAccessException
     */
    public List<Collective> findAllCollectivesPaging(int pageNumber, int pageSize) throws DataAccessException;

    /**
     * Saves <em>new</em> instance to the database.
     * 
     * @param c
     * @throws DataAccessException
     * @see CollectiveDao#save(Collective)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public void save(Collective c) throws DataAccessException;

    /**
     * Updates the persistent storage of a collective instance.
     * A reference to it must already exist in the database.
     * 
     * @param c
     * @return updated instance
     * @throws DataAccessException
     * @see CollectiveDao#update(Collective)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public Collective update(Collective c) throws DataAccessException;

}