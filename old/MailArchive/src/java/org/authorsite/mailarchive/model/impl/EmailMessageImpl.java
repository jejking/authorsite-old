/*
 * EmailMessageImpl.java, created on 09-Mar-2004 at 21:15:54
 * 
 * Copyright John King, 2004.
 *
 *  EmailMessageImpl.java is part of authorsite.org's MailArchive program.
 *
 *  VocabManager is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  VocabManager is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with VocabManager; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package org.authorsite.mailarchive.model.impl;

import java.util.*;
import org.apache.log4j.*;
import org.apache.commons.lang.builder.*;
import org.authorsite.mailarchive.model.*;

/**
 * Plain Java implementation of <code>EmailMessage</code> interface.
 * 
 * @hibernate.class table="EmailMessage" proxy="org.authorsite.mailarchive.model.EmailMessage"
 * @hibernate.cache usage="nonstrict-read-write"
 * 
 * @hibernate.query name="ChronologicalAsc" query="from org.authorsite.mailarchive.model.impl.EmailMessageImpl as msg order by msg.receivedDate asc"
 * @hibernate.query name="ChronologicalDesc" query="from org.authorsite.mailarchive.model.impl.EmailMessageImpl as msg order by msg.receivedDate desc"
 * @hibernate.query name="ChronologicalFromAsc" query="from org.authorsite.mailarchive.model.impl.EmailMessageImpl as msg where msg.receivedDate > :from order by msg.receivedDate asc"
 * @hibernate.query name="ChronologicalFromDesc" query="from org.authorsite.mailarchive.model.impl.EmailMessageImpl as msg where msg.receivedDate > :from order by msg.receivedDate desc"
 * @hibernate.query name="ChronologicalToAsc" query="from org.authorsite.mailarchive.model.impl.EmailMessageImpl as msg where msg.receivedDate < :to order by msg.receivedDate asc"
 * @hibernate.query name="ChronologicalToDesc" query="from org.authorsite.mailarchive.model.impl.EmailMessageImpl as msg where msg.receivedDate < :to order by msg.receivedDate desc"
 * @hibernate.query name="ChronologicalFromToAsc" query="from org.authorsite.mailarchive.model.impl.EmailMessageImpl as msg where msg.receivedDate > :from and msg.receivedDate < :to order by msg.receivedDate asc"
 * @hibernate.query name="ChronologicalFromToDesc" query="from org.authorsite.mailarchive.model.impl.EmailMessageImpl as msg where msg.receivedDate > :from and msg.receivedDate < :to order by msg.receivedDate desc"
 * 
 * @hibernate.query name="PersonAsc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress.person = :personToFind order by msg.receivedDate asc"
 * @hibernate.query name="PersonDesc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress.person= :personToFind order by msg.receivedDate desc"
 * @hibernate.query name="PersonFromAsc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress.person = :personToFind and msg.receivedDate > :from order by msg.receivedDate asc"
 * @hibernate.query name="PersonFromDesc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress.person = :personToFind and msg.receivedDate > :from order by msg.receivedDate desc"
 * @hibernate.query name="PersonToAsc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress.person = :personToFind and msg.receivedDate < :to order by msg.receivedDate asc"
 * @hibernate.query name="PersonToDesc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress.person = :personToFind and msg.receivedDate < :to order by msg.receivedDate desc"
 * @hibernate.query name="PersonFromToAsc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress.person = :personToFind and msg.receivedDate > :from and msg.receivedDate < :to order by msg.receivedDate asc"
 * @hibernate.query name="PersonFromToDesc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress.person = :personToFind and msg.receivedDate > :from and msg.receivedDate < :to order by msg.receivedDate desc"
 * 
 * @hibernate.query name="PersonInRoleAsc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress.person = :personToFind and mea.role = :role order by msg.receivedDate asc"
 * @hibernate.query name="PersonInRoleDesc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress.person= :personToFind and mea.role = :role order by msg.receivedDate desc"
 * @hibernate.query name="PersonInRoleFromAsc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress.person = :personToFind and mea.role = :role and msg.receivedDate > :from order by msg.receivedDate asc"
 * @hibernate.query name="PersonInRoleFromDesc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress.person = :personToFind and mea.role = :role and msg.receivedDate > :from order by msg.receivedDate desc"
 * @hibernate.query name="PersonInRoleToAsc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress.person = :personToFind and mea.role = :role and msg.receivedDate < :to order by msg.receivedDate asc"
 * @hibernate.query name="PersonInRoleToDesc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress.person = :personToFind and mea.role = :role and msg.receivedDate < :to order by msg.receivedDate desc"
 * @hibernate.query name="PersonInRoleFromToAsc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress.person = :personToFind and mea.role = :role and msg.receivedDate > :from and msg.receivedDate < :to order by msg.receivedDate asc"
 * @hibernate.query name="PersonInRoleFromToDesc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress.person = :personToFind and mea.role = :role and msg.receivedDate > :from and msg.receivedDate < :to order by msg.receivedDate desc"
 * 
 * @hibernate.query name="EmailAddressAsc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress = :emailAddress order by msg.receivedDate asc"
 * @hibernate.query name="EmailAddressDesc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress = :emailAddress order by msg.receivedDate desc"
 * @hibernate.query name="EmailAddressFromAsc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress = :emailAddress and msg.receivedDate > :from order by msg.receivedDate asc"
 * @hibernate.query name="EmailAddressFromDesc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress = :emailAddress and msg.receivedDate > :from order by msg.receivedDate desc"
 * @hibernate.query name="EmailAddressToAsc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress = :emailAddress and msg.receivedDate < :to order by msg.receivedDate asc"
 * @hibernate.query name="EmailAddressToDesc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress = :emailAddress and msg.receivedDate < :to order by msg.receivedDate desc"
 * @hibernate.query name="EmailAddressFromToAsc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress = :emailAddress and msg.receivedDate > :from and msg.receivedDate < :to order by msg.receivedDate asc"
 * @hibernate.query name="EmailAddressFromToDesc" query="select msg from EmailMessageImpl msg join msg.messageEmailAddresses mea where mea.emailAddress = :emailAddress and msg.receivedDate > :from and msg.receivedDate < :to order by msg.receivedDate desc"
 * 
 * @hibernate.query name="EmailAddressInRoleAsc" query="select msg from EmailMessageImpl as msg join msg.messageEmailAddresses mea where mea.emailAddress = :emailAddress and mea.role = :role order by msg.receivedDate asc"
 * @hibernate.query name="EmailAddressInRoleDesc" query="select msg from EmailMessageImpl as msg join msg.messageEmailAddresses mea where mea.emailAddress = :emailAddress and mea.role = :role order by msg.receivedDate desc"
 * @hibernate.query name="EmailAddressInRoleFromAsc" query="select msg from EmailMessageImpl as msg join msg.messageEmailAddresses mea where mea.emailAddress = :emailAddress and mea.role = :role and msg.receivedDate > :from order by msg.receivedDate asc"
 * @hibernate.query name="EmailAddressInRoleFromDesc" query="select msg from EmailMessageImpl as msg join msg.messageEmailAddresses mea where mea.emailAddress = :emailAddress and mea.role = :role and msg.receivedDate > :from order by msg.receivedDate desc"
 * @hibernate.query name="EmailAddressInRoleToAsc" query="select msg from EmailMessageImpl as msg join msg.messageEmailAddresses mea where mea.emailAddress = :emailAddress and mea.role = :role and msg.receivedDate < :to order by msg.receivedDate asc"
 * @hibernate.query name="EmailAddressInRoleToDesc" query="select msg from EmailMessageImpl as msg join msg.messageEmailAddresses mea where mea.emailAddress = :emailAddress and mea.role = :role and msg.receivedDate < :to order by msg.receivedDate desc"
 * @hibernate.query name="EmailAddressInRoleFromToAsc" query="select msg from EmailMessageImpl as msg join msg.messageEmailAddresses mea where mea.emailAddress = :emailAddress and mea.role = :role and msg.receivedDate > :from and msg.receivedDate < :to order by msg.receivedDate asc"
 * @hibernate.query name="EmailAddressInRoleFromToDesc" query="select msg from EmailMessageImpl as msg join msg.messageEmailAddresses mea where mea.emailAddress = :emailAddress and mea.role = :role and msg.receivedDate > :from and msg.receivedDate < :to order by msg.receivedDate desc"
 * 
 * @hibernate.query name="SubjectAsc" query="select msg from EmailMessageImpl as msg where msg.subject = :subject order by msg.receivedDate asc"
 * @hibernate.query name="SubjectDesc" query="select msg from EmailMessageImpl as msg where msg.subject = :subject order by msg.receivedDate desc"
 * @hibernate.query name="SubjectFromAsc" query="select msg from EmailMessageImpl as msg where msg.subject = :subject  and msg.receivedDate > :from order by msg.receivedDate asc"
 * @hibernate.query name="SubjectFromDesc" query="select msg from EmailMessageImpl as msg where msg.subject = :subject and msg.receivedDate > :from order by msg.receivedDate desc"
 * @hibernate.query name="SubjectToAsc" query="select msg from EmailMessageImpl as msg where msg.subject = :subject and msg.receivedDate < :to order by msg.receivedDate asc"
 * @hibernate.query name="SubjectToDesc" query="select msg from EmailMessageImpl as msg where msg.subject = :subject and msg.receivedDate < :to order by msg.receivedDate desc"
 * @hibernate.query name="SubjectFromToAsc" query="select msg from EmailMessageImpl as msg where msg.subject = :subject and msg.receivedDate > :from and msg.receivedDate < :to order by msg.receivedDate asc"
 * @hibernate.query name="SubjectFromToDesc" query="select msg from EmailMessageImpl as msg where msg.subject = :subject and msg.receivedDate > :from and msg.receivedDate < :to order by msg.receivedDate desc"
 * 
 * @hibernate.query name="SubjectLikeAsc" query="select msg from EmailMessageImpl as msg where msg.subject like :subject order by msg.receivedDate asc"
 * @hibernate.query name="SubjectLikeDesc" query="select msg from EmailMessageImpl as msg where msg.subject like :subject order by msg.receivedDate desc" 
 * @hibernate.query name="SubjectLikeFromAsc" query="select msg from EmailMessageImpl as msg where msg.subject like :subject and msg.receivedDate > :from order by msg.receivedDate asc"
 * @hibernate.query name="SubjectLikeFromDesc" query="select msg from EmailMessageImpl as msg where msg.subject like :subject and msg.receivedDate > :from order by msg.receivedDate desc"
 * @hibernate.query name="SubjectLikeToAsc" query="select msg from EmailMessageImpl as msg where msg.subject like :subject and msg.receivedDate < :to order by msg.receivedDate asc"
 * @hibernate.query name="SubjectLikeToDesc" query="select msg from EmailMessageImpl as msg where msg.subject like :subject and msg.receivedDate < :to order by msg.receivedDate desc"
 * @hibernate.query name="SubjectLikeFromToAsc" query="select msg from EmailMessageImpl as msg where msg.subject like :subject and msg.receivedDate > :from and msg.receivedDate < :to order by msg.receivedDate asc"
 * @hibernate.query name="SubjectLikeFromToDesc" query="select msg from EmailMessageImpl as msg where msg.subject like :subject and msg.receivedDate > :from and msg.receivedDate < :to order by msg.receivedDate desc"
 *  
 * @author jejking
 * @version $Revision: 1.20 $
 */
public final class EmailMessageImpl implements EmailMessage, Versionable, Comparable {

    private Integer ID;

    private Integer version;

    private Date sentDate;

    private Date receivedDate;

    private String subject;

    private String msgID;

    private String inReplyTo;

    private String msgReferences;

    private Set messageEmailAddresses;

    private String sensitivity;

    private String importance;

    private String description;

    private String disposition;

    private Set textParts;

    private Set binaryParts;

    private static Logger logger = Logger.getLogger(EmailMessageImpl.class);

    /**
     * Default constructor.
     *  
     */
    public EmailMessageImpl() {
        messageEmailAddresses = new HashSet();
        textParts = new HashSet();
        binaryParts = new HashSet();
        logger.debug("Created new EmailMessageImpl using default constructor");
    }

    /**
     * @param msgID
     */
    public EmailMessageImpl(String msgID) {
        this();
        this.setMsgID(msgID);
    }
    
    
    /**
     * @param msgID
     * @param subject
     * 
     */
    public EmailMessageImpl(String msgID, String subject) {
        this();
        this.setMsgID(msgID);
        this.setSubject(subject);
    }
       
    /**
     * @param msgID
     * @param subject
     * @param receivedDate
     */
    public EmailMessageImpl(String msgID, String subject, Date receivedDate) {
        this();
        this.setMsgID(msgID);
        this.setSubject(subject);
        this.setReceivedDate(receivedDate);
    }
    
    /**
     * @hibernate.id column="ID" generator-class="native"
     * @see org.authorsite.mailarchive.model.Identifiable#getID()
     */
    public Integer getID() {
        return ID;
    }

    /**
     * @see org.authorsite.mailarchive.model.Identifiable#setID(int)
     */
    public void setID(Integer newID) {
        ID = newID;
    }

    /**
     * @hibernate.version @see org.authorsite.mailarchive.model.impl.Versionable#getVersion()
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @see org.authorsite.mailarchive.model.impl.Versionable#setVersion(java.lang.Integer)
     */
    public void setVersion(Integer newVersion) {
        version = newVersion;
    }

    /**
     * @hibernate.property type="timestamp"
     * @hibernate.column name="sentdate" index="idx_sent_date"
     * @see org.authorsite.mailarchive.model.EmailMessage#getSentDate()
     */
    public Date getSentDate() {
        return sentDate;
    }

    /**
     * @see org.authorsite.mailarchive.model.EmailMessage#setSentDate(java.util.Date)
     */
    public void setSentDate(Date newSentDate) {
        sentDate = newSentDate;
    }

    /**
     * @hibernate.property type="timestamp"
     * @hibernate.column name="receiveddate" index="idx_received_date"
     * @see org.authorsite.mailarchive.model.EmailMessage#getReceivedDate()
     */
    public Date getReceivedDate() {
        return receivedDate;
    }

    /**
     * @see org.authorsite.mailarchive.model.EmailMessage#setReceivedDate(java.util.Date)
     */
    public void setReceivedDate(Date newReceivedDate) {
        receivedDate = newReceivedDate;
    }

    /**
     * @hibernate.property type="string" length="500"
     * @hibernate.column name="subject" index="idx_subject"
     * @see org.authorsite.mailarchive.model.EmailMessage#getSubject()
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @see org.authorsite.mailarchive.model.EmailMessage#setSubject(java.lang.String)
     */
    public void setSubject(String newSubject) {
        subject = newSubject;
    }

    /**
     * @hibernate.property type="string" length="200" not-null="true" unique="true"
     * @see org.authorsite.mailarchive.model.EmailMessage#getMsgID()
     */
    public String getMsgID() {
        return msgID;
    }

    /**
     * @see org.authorsite.mailarchive.model.EmailMessage#setMsgID(java.lang.String)
     */
    public void setMsgID(String newMsgID) {
        msgID = newMsgID;
    }

    /**
     * @hibernate.property type="string" length="200"
     * @see org.authorsite.mailarchive.model.EmailMessage#getInReplyTo()
     */
    public String getInReplyTo() {
        return inReplyTo;
    }

    /**
     * @see org.authorsite.mailarchive.model.EmailMessage#setInReplyTo(java.lang.String)
     */
    public void setInReplyTo(String newInReplyTo) {
        inReplyTo = newInReplyTo;
    }

    /**
     * @hibernate.property type="string" length="200"
     * @see org.authorsite.mailarchive.model.EmailMessage#getMsgReferences()
     */
    public String getMsgReferences() {
        return msgReferences;
    }

    /**
     * @see org.authorsite.mailarchive.model.EmailMessage#setMsgReferences(java.lang.String)
     */
    public void setMsgReferences(String newMsgReferences) {
        msgReferences = newMsgReferences;
    }

    // if we delete an email, delete all its text parts and binary parts. They
    // don't make sense otherwise

    /**
     * @hibernate.set name="textParts" cascade="all" lazy="true"
     * @hibernate.collection-key column="EmailMessageID"
     * @hibernate.collection-one-to-many class="org.authorsite.mailarchive.model.impl.TextContentImpl"
     * @hibernate.collection-cache usage="nonstrict-read-write"
     *
     * @see org.authorsite.mailarchive.model.EmailMessage#getTextParts()
     */
    public Set getTextParts() {
        return textParts;
    }

    /**
     * @throws IllegalArgumentException
     *                  if <code>Set</code> contains a member which is not an
     *                  instance of TextContent
     * @see org.authorsite.mailarchive.model.EmailMessage#setTextParts(java.util.Set)
     */
    public void setTextParts(Set newTextParts) {
        if (newTextParts == null) {
            logger.debug("Received null text parts set on EmailMessageImpl "
                    + ID);
            throw new IllegalArgumentException("Received null set");
        } else {
            Iterator it = newTextParts.iterator();
            while (it.hasNext()) {
                if (it.next() instanceof TextContent) {
                    continue;
                } else {
                    logger
                            .warn("EmailMessageImpl "
                                    + ID
                                    + " received newTextParts containing object that is not an instance of TextContent");
                    throw new IllegalArgumentException(
                            "Set newTextParts contained object which is not an instance of TextContent");
                }
            }
            textParts = newTextParts;
        }
    }

    /**
     * @see org.authorsite.mailarchive.model.EmailMessage#addTextPart(org.authorsite.mailarchive.model.TextContent)
     */
    public void addTextPart(TextContent newTextContent) {
        if (newTextContent != null) {
            textParts.add(newTextContent);
        } else {
            logger.warn("EmailMessageImpl " + ID
                    + " had null textContent added");
        }

    }

    /**
     * @see org.authorsite.mailarchive.model.EmailMessage#removeTextContent(org.authorsite.mailarchive.model.TextContent)
     */
    public void removeTextContent(TextContent textContentToRemove) {
        if (textContentToRemove != null) {
            textParts.remove(textContentToRemove);
        } else {
            logger.warn("EmailMessageImpl " + ID
                    + " had attempt to remove null text content");
        }
    }

    /**
     * @hibernate.set name="textParts" cascade="all" lazy="true"
     * @hibernate.collection-key column="EmailMessageID"
     * @hibernate.collection-one-to-many class="org.authorsite.mailarchive.model.impl.BinaryContentImpl"
     * @hibernate.collection-cache usage="nonstrict-read-write"
     *
     * @see org.authorsite.mailarchive.model.EmailMessage#getBinaryParts()
     */
    public Set getBinaryParts() {
        return binaryParts;
    }

    /**
     * @see org.authorsite.mailarchive.model.EmailMessage#setBinaryParts(java.util.Set)
     */
    public void setBinaryParts(Set newBinaryParts) {
        if (newBinaryParts == null) {
            logger.debug("received null binary parts set");
            throw new IllegalArgumentException("Set newBinaryParts was null");
        } else {
            Iterator it = newBinaryParts.iterator();
            while (it.hasNext()) {
                if (it.next() instanceof BinaryContent) {
                    continue;
                } else {
                    logger
                            .warn("Set newBinaryParts contained reference to an object that is not an instance of BinaryContent for EmailMessageImpl "
                                    + getID());
                    throw new IllegalArgumentException(
                            "Set newBinaryParts contained a reference to an object that was not of type BinaryContent");
                }
            }
            binaryParts = newBinaryParts;
        }
    }

    /**
     * @see org.authorsite.mailarchive.model.EmailMessage#addBinaryPart(org.authorsite.mailarchive.model.BinaryContent)
     */
    public void addBinaryPart(BinaryContent newBinaryContent) {
        if (newBinaryContent != null) {
            binaryParts.add(newBinaryContent);
        } else {
            logger.warn("EmailMessageImpl " + ID
                    + " had attempt to add null binary content");
            throw new IllegalArgumentException(
                    "Attempt to add null binary content");
        }
    }

    /**
     * @see org.authorsite.mailarchive.model.EmailMessage#removeBinaryPart(org.authorsite.mailarchive.model.BinaryContent)
     */
    public void removeBinaryPart(BinaryContent binaryContentToRemove) {
        if (binaryContentToRemove != null) {
            binaryParts.remove(binaryContentToRemove);
        } else {
            logger.warn("EmailMessageImpl " + ID
                    + " had attempt to remove null binary content");
            throw new IllegalArgumentException(
                    "Attempt to remove null binary content");
        }
    }

    /**
     * @hibernate.property type="string" length="100"
     * @see org.authorsite.mailarchive.model.EmailMessage#getSensitivity()
     */
    public String getSensitivity() {
        return sensitivity;
    }

    /**
     * @see org.authorsite.mailarchive.model.EmailMessage#setSensitivity(java.lang.String)
     */
    public void setSensitivity(String newSensitivity) {
        sensitivity = newSensitivity;
    }

    /**
     * @hibernate.property type="string" length="50"
     * @see org.authorsite.mailarchive.model.EmailMessage#getImportance()
     */
    public String getImportance() {
        return importance;
    }

    public void setImportance(String newImportance) {
        importance = newImportance;
    }

    /**
     * @see org.authorsite.mailarchive.model.EmailMessage#addMessageEmailAddress(org.authorsite.mailarchive.model.MessageEmailAddress)
     * @throws IllegalArgumentException
     *                  if either the <code>EmailAddress</code> or the
     *                  <code>EmailAddressRole</code> of newMessageEmailAddress is
     *                  null
     */
    public void addMessageEmailAddress(
            MessageEmailAddress newMessageEmailAddress) {
        if (newMessageEmailAddress.getRole() == null) {
            logger.warn("newMessageEmailAddress has null EmailAddressRole, EmailMessageImpl " + getID());
            throw new IllegalArgumentException("MessageEmailAddress has null EmailAddressRole");
        }
        if (newMessageEmailAddress.getEmailAddress() == null) {
            logger.warn("newMessageEmailAddress has null EmailAddress, EmailMessageImpl " + getID());
            throw new IllegalArgumentException("MessageEmailAddress has null EmailAddress");
        }
        messageEmailAddresses.add(newMessageEmailAddress);
    }

    /**
     * @hibernate.set name="messageEmailAddresses" cascade="all" lazy="false"
     * @hibernate.collection-key column="EmailMessageID"
     * @hibernate.collection-composite-element class="org.authorsite.mailarchive.model.impl.MessageEmailAddressImpl"
     * @hibernate.collection-cache usage="nonstrict-read-write" 
     *
     * @see org.authorsite.mailarchive.model.EmailMessage#getMessageEmailAddresses()
     */
    public Set getMessageEmailAddresses() {
        return messageEmailAddresses;
    }

    /**
     * @see org.authorsite.mailarchive.model.EmailMessage#getMessageEmailAddressesInRole(org.authorsite.mailarchive.model.EmailAddressRole)
     */
    public Set getMessageEmailAddressesInRole(EmailAddressRole role) {
        Set addressesInRole = new HashSet();
        Iterator it = messageEmailAddresses.iterator();
        while (it.hasNext()) {
            MessageEmailAddress mea = (MessageEmailAddress) it.next();
            if (mea.getRole().equals(role)) {
                addressesInRole.add(mea);
            }
        }
        return addressesInRole;
    }

    /**
     * @see org.authorsite.mailarchive.model.EmailMessage#removeMessageEmailAddress(org.authorsite.mailarchive.model.MessageEmailAddress)
     */
    public void removeMessageEmailAddress(
            MessageEmailAddress messageEmailAddressToRemove) {
        if (messageEmailAddressToRemove.getRole() == null) {
            logger.warn("messageEmailAddressToRemove has null EmailAddressRole, EmailMessageImpl " + getID());
            throw new IllegalArgumentException ("MessageEmailAddressToRemove has null EmailAddressRole");
        }
        if (messageEmailAddressToRemove.getEmailAddress() == null) {
            logger.warn(("messageEmailAddressToRemove has null EmailAddress, EmailMessageImpl " + getID()));
            throw new IllegalArgumentException ("MessageEmailAddressToRemove has null EmailAddress");
        }
        messageEmailAddresses.remove(messageEmailAddressToRemove);
    }

    /**
     * @param Set
     *                 of <code>MessageEmailAddress</code> instances, which must
     *                 have both the <code>EmailAddress</code> and the
     *                 <code>EmailAddressRole</code> set
     * @see org.authorsite.mailarchive.model.EmailMessage#setMessageEmailAddresses(java.util.Set)
     * @throws IllegalArgumentException
     *                  if Set contains references to objects which are not instances
     *                  of <code>MessageEmailAddress</code> or if either the
     *                  <code>EmailAddress</code> or the
     *                  <code>EmailAddressRole</code> reference is null in any
     *                  member of the Set
     */
    public void setMessageEmailAddresses(Set newMessageEmailAddresses) {
        if (newMessageEmailAddresses == null) {
            logger.debug("Set newMessageEmailAddresses was null");
            throw new IllegalArgumentException(
                    "Set newMessageEmailAddresses was null");
        } else {
            Iterator it = newMessageEmailAddresses.iterator();
            while (it.hasNext()) {
                try {
                    MessageEmailAddress mea = (MessageEmailAddress) it.next();
                    if (mea.getRole() == null) {
                        logger
                                .warn("Set newMessageEmailAddresses contains MessageEmailAddress with null EmailAddressRole, EmailMessageImpl "
                                        + getID());
                        throw new IllegalArgumentException(
                                "Set newMessageEmailAddresses contains MessageEmailAddress with null EmailAddressRole");
                    }
                    if (mea.getEmailAddress() == null) {
                        logger
                                .warn("Set newMessageEmailAddresses contains MessageEmailAddress with null EmailAddress, EmailMessageImpl "
                                        + getID());
                        throw new IllegalArgumentException(
                                "Set newMessageEmailAddresses contains MessageEmailAddress with null EmailAddress");
                    }
                } catch (ClassCastException cce) {
                    logger
                            .warn("Set newMessageEmailAddresses contains a reference to an object that is not a MessageEmailAddress, EmailMessageImpl "
                                    + getID());
                    throw new IllegalArgumentException(
                            "Set newMessageEmailAddresses contained a reference to an object that was not a MessageEmailAddress");
                }
            }
            messageEmailAddresses = newMessageEmailAddresses;
        }
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof EmailMessage) {
            EmailMessage input  = (EmailMessage) o;
            return new EqualsBuilder().append(this.getMsgID(), input.getMsgID()).isEquals();
        }
        else {
            return false;
        }
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(this.getMsgID()).toHashCode();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("EmailMessageImpl:\n");
        sb.append("msgID:\t");
        sb.append(getMsgID());
        sb.append("\n");
        sb.append("From: \t");
        Iterator fromIt = this.getMessageEmailAddressesInRole(
                EmailAddressRole.FROM).iterator();
        while (fromIt.hasNext()) {
            MessageEmailAddress msgEmailAddress = (MessageEmailAddress) fromIt
                    .next();
            sb.append(msgEmailAddress.getEmailAddress());
            sb.append(", ");
        }
        sb.append("\n");
        sb.append("To: \t");
        Iterator toIt = this
                .getMessageEmailAddressesInRole(EmailAddressRole.TO).iterator();
        while (toIt.hasNext()) {
            MessageEmailAddress msgEmailAddress = (MessageEmailAddress) toIt
                    .next();
            sb.append(msgEmailAddress.getEmailAddress());
            sb.append(", ");
        }
        sb.append("\n");
        sb.append("Subject: ");
        sb.append(getSubject());
        sb.append("\n");
        return sb.toString();
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
public int compareTo(Object o) {
		EmailMessage input = (EmailMessage) o;
		return new CompareToBuilder().append(getMsgID(), input.getMsgID()).toComparison();
	}
    
}