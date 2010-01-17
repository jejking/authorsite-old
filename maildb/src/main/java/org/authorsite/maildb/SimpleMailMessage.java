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

package org.authorsite.maildb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 * @author jejking
 */
public class SimpleMailMessage {


    private long id;

    private String mesgId;

    private String inReplyTo;

    private String mesgReferences;

    private String senderName;

    private String senderAddress;

    private Date dateSent;

    private String subject;

    private String text;

    private List<Attachment> attachments;

    /**
     * @return the id
     */
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
     * @return the mesgId
     */
    public String getMesgId() {
        return mesgId;
    }

    /**
     * @param mesgId the mesgId to set
     */
    public void setMesgId(String mesgId) {
        this.mesgId = mesgId;
    }

    /**
     * @return the inReplyTo
     */
    public String getInReplyTo() {
        return inReplyTo;
    }

    /**
     * @param inReplyTo the inReplyTo to set
     */
    public void setInReplyTo(String inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    public String getMesgReferences() {
        return mesgReferences;
    }

    public void setMesgReferences(String mesgReferences) {
        this.mesgReferences = mesgReferences;
    }

    

    /**
     * @return the senderName
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * @param senderName the senderName to set
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * @return the senderAddress
     */
    public String getSenderAddress() {
        return senderAddress;
    }

    /**
     * @param senderAddress the senderAddress to set
     */
    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    /**
     * @return the dateSent
     */
    public Date getDateSent() {
        return dateSent;
    }

    /**
     * @param dateSent the dateSent to set
     */
    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the attachments
     */
    public List<Attachment> getAttachments() {
        return attachments;
    }

    /**
     * @param attachments the attachments to set
     */
    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void addAttachment(Attachment attachment) {
        if (this.attachments == null) {
            this.attachments = new ArrayList<Attachment>();
        }
        this.attachments.add(attachment);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("From: " + senderName + " " + senderAddress + "\n");
        builder.append("Sent: " + dateSent + "\n");
        builder.append("Subject: " + subject + "\n");
        builder.append("Id: " + id + "\n");
        builder.append("MsgId: " + mesgId + "\n");
        builder.append("In-Reply-To: " + inReplyTo + "\n");
        builder.append("References: " + mesgReferences + "\n");
        builder.append("\n");
        builder.append(text);
        if (this.attachments != null && !this.attachments.isEmpty()) {
            builder.append(this.attachments.size() + " attachment(s)");
        }
        return builder.toString();
    }

}
