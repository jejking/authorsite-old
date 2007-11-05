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
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link IndividualManagementService} which effectively
 * delegates to injected {@link IndividualDao} and {@link IndividualAclManager}
 * instances to do the work.
 * 
 * @author jejking
 */
@Transactional
public class IndividualManagementServiceImpl implements IndividualManagementService {

    private IndividualDao individualDao;
    private IndividualAclManager individualAclManager;
    

    /**
     * Gets individual dao.
     * 
     * @return individual dao.
     */
    public IndividualDao getIndividualDao() {
        return this.individualDao;
    }

    /**
     * Sets the individual dao.
     * 
     * <p>
     * For IoC use.
     * </p>
     * 
     * @param individualDao
     */
    public void setIndividualDao(IndividualDao individualDao) {
        this.individualDao = individualDao;
    }

    /**
     * @return the individualAclManager
     */
    public IndividualAclManager getIndividualAclManager() {
        return this.individualAclManager;
    }

    /**
     * Sets the individualAclManager.
     * 
     * <p>
     * For IoC use.
     * </p>
     * 
     * @param individualAclManager
     */
    public void setIndividualAclManager(IndividualAclManager individualAclManager) {
        this.individualAclManager = individualAclManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.authorsite.domain.service.IndividualManagementService#countIndividuals()
     */
    @Transactional(readOnly = true)
    public int countIndividuals() throws DataAccessException {
        return this.individualDao.countIndividuals();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.authorsite.domain.service.IndividualManagementService#delete(org.authorsite.domain.Individual)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ACL_INDIVIDUAL_ADMIN" })
    public void deleteIndividual(Individual i) throws DataAccessException {
        this.individualDao.delete(i);
        this.individualAclManager.deleteIndividualAcl(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.authorsite.domain.service.IndividualManagementService#findById(long)
     */
    @Transactional(readOnly = true)
    public Individual findById(long id) throws DataAccessException {
        return this.individualDao.findById(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.authorsite.domain.service.IndividualManagementService#findIndividualsByName(java.lang.String)
     */
    @Transactional(readOnly = true)
    public List<Individual> findIndividualsByName(String name) throws DataAccessException {
        return this.individualDao.findIndividualsByName(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.authorsite.domain.service.IndividualManagementService#findIndividualsByNameAndGivenNames(java.lang.String,
     *      java.lang.String)
     */
    @Transactional(readOnly = true)
    public List<Individual> findIndividualsByNameAndGivenNames(String name, String givenNames)
            throws DataAccessException {
        return this.individualDao.findIndividualsByNameAndGivenNames(name, givenNames);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.authorsite.domain.service.IndividualManagementService#findIndividualsByNameAndGivenNamesWildcard(java.lang.String,
     *      java.lang.String)
     */
    @Transactional(readOnly = true)
    public List<Individual> findIndividualsByNameAndGivenNamesWildcard(String name, String givenNames)
            throws DataAccessException {
        return this.individualDao.findIndividualsByNameAndGivenNamesWildcard(name, givenNames);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.authorsite.domain.service.IndividualManagementService#findIndividualsByNameWildcard(java.lang.String)
     */
    @Transactional(readOnly = true)
    public List<Individual> findIndividualsByNameWildcard(String name) throws DataAccessException {
        return this.individualDao.findIndividualsByNameWildcard(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.authorsite.domain.service.IndividualManagementService#save(org.authorsite.domain.Individual)
     */
    @Secured( { "ROLE_EDITOR", "ROLE_ADMINISTRATOR" })
    public void save(Individual i) throws DataAccessException {
        this.individualDao.save(i);
        this.individualAclManager.createIndividualAcl(i);
    }
    
    @Secured( {"ROLE_EDITOR","ROLE_ADMINISTRATOR" })
    public Individual createAndSaveIndividual(String name, String givenNames, String nameQualification) throws DataAccessException {
        Individual i = new Individual( name, givenNames );
        if (nameQualification != null) {
            i.setNameQualification(nameQualification);
        }
        this.save(i);
        return i;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.authorsite.domain.service.IndividualManagementService#update(org.authorsite.domain.Individual)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ACL_INDIVIDUAL_ADMIN" })
    public Individual update(Individual i) throws DataAccessException {
        Individual updated = this.individualDao.update(i);
        return updated;
    }

    @Transactional(readOnly = true)
    public List<Individual> findAllIndividuals() throws DataAccessException {
        return this.individualDao.findAllIndividuals();
    }

    @Transactional(readOnly = true)
    public List<Individual> findAllIndividualsPaging(int pageNumber, int pageSize) throws DataAccessException {
        return this.individualDao.findAllIndividuals(pageNumber, pageSize);
    }

}
