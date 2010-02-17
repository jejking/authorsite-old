/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.authorsite.maildb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;

/**
 *
 * @author jejking
 */
public class AttachmentDao {

    private static final String INSERT = "INSERT INTO MESSAGE_ATTACHMENT " +
            "(MESSAGE_ID, MIME_TYPE, DESCRIPTION, DISPOSITION, FILE_NAME, SIZE, CONTENTS) " +
            "VALUES " +
            "(?, ?, ?, ?, ?, ?, ?)";

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void insertAttachment(final long messageId, final Attachment attachment) {

        LobHandler lobHandler = new DefaultLobHandler();

        this.jdbcTemplate.execute(INSERT, new AbstractLobCreatingPreparedStatementCallback(lobHandler) {

            @Override
            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                ps.setLong(1, messageId);
                ps.setString(2, attachment.getMimeType());
                ps.setString(3, attachment.getDescription());
                ps.setString(4, attachment.getDisposition());
                ps.setString(5, attachment.getFileName());
                ps.setInt(6, attachment.getContents().length);
                lobCreator.setBlobAsBytes(ps, 7, attachment.getContents());
            }
        });

    }

    public void deleteAll() {
        this.jdbcTemplate.execute("DELETE FROM MESSAGE_ATTACHMENT");
    }

}
