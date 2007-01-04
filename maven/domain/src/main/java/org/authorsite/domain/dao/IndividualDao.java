/*
 * IndividualDao.java
 *
 * Created on 04 January 2007, 21:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.domain.dao;

import java.util.List;
import org.authorsite.domain.Individual;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author jejking
 */
public interface IndividualDao {
    
    public Individual findById(long id) throws DataAccessException;
    
    public void save(Individual i) throws DataAccessException;
    
    public Individual update(Individual i) throws DataAccessException;
    
    public void delete(Individual i) throws DataAccessException;
    
    public int countIndividuals() throws DataAccessException;
    
    public List<Individual> findIndividualsByName(String name) throws DataAccessException;
    
    public List<Individual> findIndividualsByNameWildcard(String name) throws DataAccessException;
    
    public List<Individual> findIndividualsByNameAndGivenNames(String name, String givenNames) throws DataAccessException;
    
    public List<Individual> findIndividualsByNameAndGivenNamesWildcard(String name, String givenNames) throws DataAccessException;
    
}
