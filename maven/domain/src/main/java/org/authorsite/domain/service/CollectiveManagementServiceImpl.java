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
import org.springframework.transaction.annotation.Transactional;

/**
 * Core interface for manipulating Individuals (and their ACLs) in the database.
 * 
 * @author jejking
 */
@Transactional
public class CollectiveManagementServiceImpl implements CollectiveManagementService {

    private CollectiveDao collectiveDao;

    /**
     * @return dao
     */
    public CollectiveDao getCollectiveDao() {
        return this.collectiveDao;
    }

    /**
     * Injects the dao.
     * 
     * <p>
     * For IoC use.
     * </p>
     * 
     * @param collectiveDao
     */
    public void setCollectiveDao(CollectiveDao collectiveDao) {
        this.collectiveDao = collectiveDao;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.authorsite.domain.service.CollectiveManagementService#countCollectives()
     */
    @Transactional(readOnly = true)
    public int countCollectives() throws DataAccessException {
        return this.collectiveDao.countCollectives();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.authorsite.domain.service.CollectiveManagementService#delete(org.authorsite.domain.Collective)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public void delete(Collective c) throws DataAccessException {
        this.collectiveDao.delete(c);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.authorsite.domain.service.CollectiveManagementService#findById(long)
     */
    @Transactional(readOnly = true)
    public Collective findById(long id) throws DataAccessException {
        return this.collectiveDao.findById(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.authorsite.domain.service.CollectiveManagementService#findCollectivesByName(java.lang.String)
     */
    @Transactional(readOnly = true)
    public List<Collective> findCollectivesByName(String name) throws DataAccessException {
        return this.collectiveDao.findCollectivesByName(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.authorsite.domain.service.CollectiveManagementService#findCollectivesByNameWildcard(java.lang.String)
     */
    @Transactional(readOnly = true)
    public List<Collective> findCollectivesByNameWildcard(String name) throws DataAccessException {
        return this.collectiveDao.findCollectivesByNameWildcard(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.authorsite.domain.service.CollectiveManagementService#findCollectivesByPlace(java.lang.String)
     */
    @Transactional(readOnly = true)
    public List<Collective> findCollectivesByPlace(String placeName) throws DataAccessException {
        return this.collectiveDao.findCollectivesByPlace(placeName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.authorsite.domain.service.CollectiveManagementService#findCollectivesByPlaceWildcard(java.lang.String)
     */
    @Transactional(readOnly = true)
    public List<Collective> findCollectivesByPlaceWildcard(String placeName) throws DataAccessException {
        return this.collectiveDao.findCollectivesByPlaceWildcard(placeName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.authorsite.domain.service.CollectiveManagementService#save(org.authorsite.domain.Collective)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public void save(Collective c) throws DataAccessException {
        this.collectiveDao.save(c);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.authorsite.domain.service.CollectiveManagementService#update(org.authorsite.domain.Collective)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public Collective update(Collective c) throws DataAccessException {
        return this.collectiveDao.update(c);
    }

}
