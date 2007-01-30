package org.authorsite.domain.service;

import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.authorsite.domain.Individual;
import org.springframework.dao.DataAccessException;

public interface IndividualManagementService {

    /**
     * @return
     * @throws DataAccessException
     * @see org.authorsite.dao.IndividualDao#countIndividuals()
     */
    public abstract int countIndividuals() throws DataAccessException;

    /**
     * @param i
     * @throws DataAccessException
     * @see org.authorsite.dao.IndividualDao#delete(org.authorsite.domain.Individual)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ACL_INDIVIDUAL_ADMIN" })
    public abstract void delete(Individual i) throws DataAccessException;

    /**
     * @param id
     * @return
     * @throws DataAccessException
     * @see org.authorsite.dao.IndividualDao#findById(long)
     */
    public abstract Individual findById(long id) throws DataAccessException;

    /**
     * @param name
     * @return
     * @throws DataAccessException
     * @see org.authorsite.dao.IndividualDao#findIndividualsByName(java.lang.String)
     */
    public abstract List<Individual> findIndividualsByName(String name)
	    throws DataAccessException;

    /**
     * @param name
     * @param givenNames
     * @return
     * @throws DataAccessException
     * @see org.authorsite.dao.IndividualDao#findIndividualsByNameAndGivenNames(java.lang.String, java.lang.String)
     */
    public abstract List<Individual> findIndividualsByNameAndGivenNames(
	    String name, String givenNames) throws DataAccessException;

    /**
     * @param name
     * @param givenNames
     * @return
     * @throws DataAccessException
     * @see org.authorsite.dao.IndividualDao#findIndividualsByNameAndGivenNamesWildcard(java.lang.String, java.lang.String)
     */
    public abstract List<Individual> findIndividualsByNameAndGivenNamesWildcard(
	    String name, String givenNames) throws DataAccessException;

    /**
     * @param name
     * @return
     * @throws DataAccessException
     * @see org.authorsite.dao.IndividualDao#findIndividualsByNameWildcard(java.lang.String)
     */
    public abstract List<Individual> findIndividualsByNameWildcard(String name)
	    throws DataAccessException;

    /**
     * @param i
     * @throws DataAccessException
     * @see org.authorsite.dao.IndividualDao#save(org.authorsite.domain.Individual)
     */
    @Secured( { "ROLE_EDITOR", "ROLE_ADMINISTRATOR" })
    public abstract void save(Individual i) throws DataAccessException;

    /**
     * @param i
     * @return
     * @throws DataAccessException
     * @see org.authorsite.dao.IndividualDao#update(org.authorsite.domain.Individual)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ACL_INDIVIDUAL_ADMIN" })
    public abstract Individual update(Individual i) throws DataAccessException;

}