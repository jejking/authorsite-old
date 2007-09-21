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


package org.authorsite.dao.bib;

import org.springframework.test.jpa.AbstractJpaTests;

/**
 *
 * @author jking
 */
public class GenericWorkDaoJPATest extends AbstractJpaTests {

    public GenericWorkDaoJPATest() {
        super();
    }
    private GenericWorkDao genericWorkDao;

    public void setGenericWorkDao(GenericWorkDao genericWorkDao) {
        this.genericWorkDao = genericWorkDao;
    }

    protected String[] getConfigLocations() {
        return new String[]{"classpath:/spring-test-appcontext-1.xml"};
    }

    @Override
    protected void onSetUpInTransaction() throws Exception {
        super.onSetUpInTransaction();
        
        /*
         * what do we need
         * - a person (to have created and updated the works)
         * - some journals
         * - some articles
         */
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (0, current_timestamp, 0, current_timestamp, 0, 0, null, 'Wurst', 'Hans', 'Individual')");
        
        // create journal "Wibble Studies"
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt, title, date, toDate, createdBy_id, updatedBy_id) " +
                "");
    
        
    }
    
    
}