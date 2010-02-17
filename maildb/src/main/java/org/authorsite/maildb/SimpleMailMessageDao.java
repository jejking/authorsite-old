/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.authorsite.maildb;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jejking
 */
public class SimpleMailMessageDao {

    private SimpleJdbcInsert insert;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.insert = new SimpleJdbcInsert(dataSource).withTableName("SIMPLE_MAIL_MESSAGE").usingGeneratedKeyColumns("ID");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public long insertSimpleMailMessage(SimpleMailMessage message) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("MESG_ID", message.getMesgId());
        params.put("IN_REPLY_TO", message.getInReplyTo());
        params.put("MESG_REFERENCES", message.getMesgReferences());
        params.put("SENDER_NAME", message.getSenderName());
        params.put("SENDER_ADDRESS", message.getSenderAddress());
        params.put("DATE_SENT", message.getDateSent());
        params.put("SUBJECT", message.getSubject());
        params.put("MESG_TEXT", message.getText());
        Number id = this.insert.executeAndReturnKey(params);
        return id.longValue();
    }

    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM SIMPLE_MAIL_MESSAGE");
    }
}
