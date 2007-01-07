package org.authorsite.dao;

import java.util.List;
import org.authorsite.domain.*;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author jejking
 */
public interface CollectiveDao {

    public Collective findById(long id) throws DataAccessException;
    
    public void save(Collective c) throws DataAccessException;
    
    public Collective update(Collective c) throws DataAccessException;
    
    public void delete(Collective c) throws DataAccessException;
    
    public int countCollectives() throws DataAccessException;
    
    public List<Collective> findCollectivesByName(String name) throws DataAccessException;
    
    public List<Collective> findCollectivesByNameWildcard(String name) throws DataAccessException;
    
    public List<Collective> findCollectivesByPlace(String placeName) throws DataAccessException;
    
    public List<Collective> findCollectivesByPlaceWildcard(String placeName) throws DataAccessException;
    
    
}
