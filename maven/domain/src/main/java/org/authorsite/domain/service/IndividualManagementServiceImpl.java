package org.authorsite.domain.service;

import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.authorsite.dao.IndividualDao;
import org.authorsite.domain.Individual;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jejking
 */
@Transactional
public class IndividualManagementServiceImpl implements IndividualManagementService {

    private IndividualDao individualDao;
    private IndividualAclManager individualAclManager;

    public IndividualDao getIndividualDao() {
        return individualDao;
    }

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
     * @param individualAclManager the individualAclManager to set
     */
    public void setIndividualAclManager(IndividualAclManager individualAclManager) {
        this.individualAclManager = individualAclManager;
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.IndividualManagementService#countIndividuals()
     */
    public int countIndividuals() throws DataAccessException {
	return this.individualDao.countIndividuals();
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.IndividualManagementService#delete(org.authorsite.domain.Individual)
     */
    @Secured({"ROLE_ADMINISTRATOR", "ACL_INDIVIDUAL_ADMIN"})
    public void delete(Individual i) throws DataAccessException {
	this.individualDao.delete(i);
	this.individualAclManager.deleteIndividualAcl(i);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.IndividualManagementService#findById(long)
     */
    public Individual findById(long id) throws DataAccessException {
	return this.individualDao.findById(id);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.IndividualManagementService#findIndividualsByName(java.lang.String)
     */
    public List<Individual> findIndividualsByName(String name) throws DataAccessException {
	return this.individualDao.findIndividualsByName(name);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.IndividualManagementService#findIndividualsByNameAndGivenNames(java.lang.String, java.lang.String)
     */
    public List<Individual> findIndividualsByNameAndGivenNames(String name, String givenNames) throws DataAccessException {
	return this.individualDao.findIndividualsByNameAndGivenNames(name, givenNames);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.IndividualManagementService#findIndividualsByNameAndGivenNamesWildcard(java.lang.String, java.lang.String)
     */
    public List<Individual> findIndividualsByNameAndGivenNamesWildcard(String name, String givenNames) throws DataAccessException {
	return this.individualDao.findIndividualsByNameAndGivenNamesWildcard(name, givenNames);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.IndividualManagementService#findIndividualsByNameWildcard(java.lang.String)
     */
    public List<Individual> findIndividualsByNameWildcard(String name) throws DataAccessException {
	return this.individualDao.findIndividualsByNameWildcard(name);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.IndividualManagementService#save(org.authorsite.domain.Individual)
     */
    @Secured({"ROLE_EDITOR", "ROLE_ADMINISTRATOR"})
    public void save(Individual i) throws DataAccessException {
	this.individualDao.save(i);
	this.individualAclManager.createIndividualAcl(i);
	this.individualAclManager.addEditorRoleToIndividualAcl(i);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.IndividualManagementService#update(org.authorsite.domain.Individual)
     */
    @Secured({"ROLE_ADMINISTRATOR", "ACL_INDIVIDUAL_ADMIN"})
    public Individual update(Individual i) throws DataAccessException {
	return this.individualDao.update(i);
    }
    
    
    
}
