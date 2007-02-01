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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.authorsite.domain.Collective;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of collective DAO based on JPA.
 *
 * @author jejking
 */
@Transactional()
public class CollectiveDaoJPA extends JpaDaoSupport implements CollectiveDao {

    /** Creates a new instance of CollectiveDaoJPA */
    public CollectiveDaoJPA() {
	super();
    }

    @Transactional(readOnly = true)
    public Collective findById(long id) throws DataAccessException {
	return this.getJpaTemplate().find(Collective.class, new Long(id));
    }

    public void save(final Collective c) throws DataAccessException {
	this.getJpaTemplate().persist(c);
    }

    public Collective update(Collective c) throws DataAccessException {
	return this.getJpaTemplate().merge(c);
    }

    public void delete(Collective c) throws DataAccessException {
	this.getJpaTemplate().remove(c);
    }

    @Transactional(readOnly = true)
    public int countCollectives() throws DataAccessException {
	Number n = (Number) this.getJpaTemplate().findByNamedQuery(
		"CollectiveCount").iterator().next();
	return n.intValue();
    }

    @SuppressWarnings(value = { "unchecked" })
    @Transactional(readOnly = true)
    public List<Collective> findCollectivesByName(String name)
	    throws DataAccessException {
	Map<String, String> params = new HashMap<String, String>();
	params.put("collectiveName", name);
	return this.getJpaTemplate().findByNamedQueryAndNamedParams(
		"CollectivesByName", params);
    }

    @SuppressWarnings(value = { "unchecked" })
    @Transactional(readOnly = true)
    public List<Collective> findCollectivesByNameWildcard(String name)
	    throws DataAccessException {
	Map<String, String> params = new HashMap<String, String>();
	params.put("collectiveName", name);
	return this.getJpaTemplate().findByNamedQueryAndNamedParams(
		"CollectivesByNameWildcard", params);
    }

    @SuppressWarnings(value = { "unchecked" })
    @Transactional(readOnly = true)
    public List<Collective> findCollectivesByPlace(String placeName)
	    throws DataAccessException {
	Map<String, String> params = new HashMap<String, String>();
	params.put("placeName", placeName);
	return this.getJpaTemplate().findByNamedQueryAndNamedParams(
		"CollectivesByPlace", params);
    }

    @SuppressWarnings(value = { "unchecked" })
    @Transactional(readOnly = true)
    public List<Collective> findCollectivesByPlaceWildcard(String placeName)
	    throws DataAccessException {
	Map<String, String> params = new HashMap<String, String>();
	params.put("placeName", placeName);
	return this.getJpaTemplate().findByNamedQueryAndNamedParams(
		"CollectivesByPlaceWildcard", params);
    }

}
