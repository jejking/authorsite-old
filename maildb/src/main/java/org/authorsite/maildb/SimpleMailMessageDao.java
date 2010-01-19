/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.authorsite.maildb;

import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jejking
 */
public class SimpleMailMessageDao {

    private DataSource dataSource;
    private SimpleJdbcInsert insert;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.insert = new SimpleJdbcInsert(dataSource).withTableName("SIMPLE_MAIL_MESSAGE").usingGeneratedKeyColumns("ID");
    }

    public long insertSimpleMailMessage(SimpleMailMessage message) {
        return 0;
    }
}
