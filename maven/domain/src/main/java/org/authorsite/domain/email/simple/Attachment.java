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

package org.authorsite.domain.email.simple;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author jejking
 */
@Entity
@Table(name = "mail_attachment")
public class Attachment {


    private long id;

    private MailMessage message;


    private String mimeType;

    private byte[] contents;

    /**
     * @return the id
     */
    @Id
    @Column(name="attachment_id")
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * @return the mimeType
     */
    @Column(nullable=false, name="mime_type")
    public String getMimeType() {
        return mimeType;
    }

    /**
     * @param mimeType the mimeType to set
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * @return the contents
     */
    @Lob
    @Basic(optional=false)
    public byte[] getContents() {
        return contents;
    }

    /**
     * @param contents the contents to set
     */
    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    @ManyToOne
    @JoinColumn(name="message_id", nullable=false)
    public MailMessage getMessage() {
        return message;
    }

    public void setMessage(MailMessage message) {
        this.message = message;
    }



    


}
