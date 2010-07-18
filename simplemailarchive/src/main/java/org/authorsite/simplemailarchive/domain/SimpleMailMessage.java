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

package org.authorsite.simplemailarchive.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.util.Assert;


/**
 * Extremely simplified view of an email sent (presumably
 * to a mailing list) and consisting
 * of text and an optional list of attachments along with
 * core properties.
 * 
 * @author jejking
 */
@Entity
public class SimpleMailMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	@Column(nullable = false, unique = true)
    private String mesgId;

	@Column(nullable = true)
    private String inReplyTo;

	@Column(nullable = true)
    private String mesgReferences;

	@Embedded
	private SimpleEmailAddress senderEmailAddress;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSent;

    @Column(nullable = true)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String text;

    @OneToMany(fetch = FetchType.EAGER)
    private List<SimpleAttachment> attachments;

    /**
     * Default constructor for JPA.
     */
    private SimpleMailMessage() {
    	super();
    }
    
    /**
     * For use by builder.
     * @param simpleMailMessageBuilder
     */
    private SimpleMailMessage(SimpleMailMessageBuilder simpleMailMessageBuilder) {
		this.mesgId = simpleMailMessageBuilder.mesgId;
		this.dateSent = simpleMailMessageBuilder.dateSent;
		this.senderEmailAddress = simpleMailMessageBuilder.senderEmailAddress;
		this.text = simpleMailMessageBuilder.text;
		this.subject = simpleMailMessageBuilder.subject;
		this.mesgReferences = simpleMailMessageBuilder.mesgReferences;
		this.inReplyTo = simpleMailMessageBuilder.inReplyTo;
		this.attachments = simpleMailMessageBuilder.attachments;
	}


	public long getId() {
		return id;
	}

    public String getMesgId() {
		return mesgId;
	}

	public String getInReplyTo() {
		return inReplyTo;
	}

	public String getMesgReferences() {
		return mesgReferences;
	}

	public SimpleEmailAddress getSenderEmailAddress() {
		return this.senderEmailAddress;
	}

	/**
	 * Gets date sent.
	 * @return copy of internal state (if set), else <code>null</code>
	 */
	public Date getDateSent() {
		return dateSent != null ? new Date(this.dateSent.getTime()) : null;
	}

	public String getSubject() {
		return subject;
	}

	public String getText() {
		return text;
	}

	public List<SimpleAttachment> getAttachments() {
		return Collections.unmodifiableList(this.attachments);
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateSent == null) ? 0 : dateSent.hashCode());
		result = prime * result
				+ ((inReplyTo == null) ? 0 : inReplyTo.hashCode());
		result = prime * result + ((mesgId == null) ? 0 : mesgId.hashCode());
		result = prime * result
				+ ((mesgReferences == null) ? 0 : mesgReferences.hashCode());
		result = prime
				* result
				+ ((senderEmailAddress == null) ? 0 : senderEmailAddress
						.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SimpleMailMessage))
			return false;
		SimpleMailMessage other = (SimpleMailMessage) obj;
		if (dateSent == null) {
			if (other.dateSent != null)
				return false;
		} else if (!dateSent.equals(other.dateSent))
			return false;
		if (inReplyTo == null) {
			if (other.inReplyTo != null)
				return false;
		} else if (!inReplyTo.equals(other.inReplyTo))
			return false;
		if (mesgId == null) {
			if (other.mesgId != null)
				return false;
		} else if (!mesgId.equals(other.mesgId))
			return false;
		if (mesgReferences == null) {
			if (other.mesgReferences != null)
				return false;
		} else if (!mesgReferences.equals(other.mesgReferences))
			return false;
		if (senderEmailAddress == null) {
			if (other.senderEmailAddress != null)
				return false;
		} else if (!senderEmailAddress.equals(other.senderEmailAddress))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	
	
	@Override
	public String toString() {
		return "SimpleMailMessage [attachments=" + attachments + ", dateSent="
				+ dateSent + ", id=" + id + ", inReplyTo=" + inReplyTo
				+ ", mesgId=" + mesgId + ", mesgReferences=" + mesgReferences
				+ ", senderEmailAddress=" + senderEmailAddress + ", subject="
				+ subject + ", text=" + text + "]";
	}



	public static class SimpleMailMessageBuilder {
	    
		private final String mesgId;
		private final SimpleEmailAddress senderEmailAddress;
	    private final Date dateSent;
	    private final String text;
	    
	    private String inReplyTo;
		private String mesgReferences;
	    private String subject;

	    private List<SimpleAttachment> attachments = new ArrayList<SimpleAttachment>();

	    /**
	     * Constructs builder with all mandatory parameters.
	     * @param mesgId mesgid as generated by client/server, may not be <code>null</code>. Must ultimately be unique.
	     * @param senderAddress sender address. May not be <code>null</code>.
	     * @param dateSent date sent. May not be <code>null</code>.
	     * @param text the plain text body of the message.
	     */
		public SimpleMailMessageBuilder(String mesgId, SimpleEmailAddress senderEmailAddress,
				Date dateSent, String text) {
			super();
			
			Assert.notNull(mesgId);
			this.mesgId = mesgId;
			
			Assert.notNull(dateSent);
			this.dateSent = dateSent;
			
			Assert.isTrue(text != null && text.trim().length() > 0);
			this.text = text;
			
			Assert.notNull(senderEmailAddress);
			this.senderEmailAddress = senderEmailAddress;
			
		}
	    
		public SimpleMailMessageBuilder inReplyTo(String inReplyTo) {
			this.inReplyTo = inReplyTo;
			return this;
		}
		
		public SimpleMailMessageBuilder mesgReferences(String mesgReferences) {
			this.mesgReferences = mesgReferences;
			return this;
		}
		
		public SimpleMailMessageBuilder subject(String subject) {
			this.subject = subject;
			return this;
		}
		
		/**
		 * Adds attachment to builder, provided attachment is not <code>null</code>.
		 * @param attachment
		 * @return this
		 */
		public SimpleMailMessageBuilder attachment(SimpleAttachment attachment) {
			if (attachment != null) {
				this.attachments.add(attachment);
			}
			return this;
		}
		
		/**
		 * Returns fully constructed object that is effectively immutable.
		 * @return message
		 */
		public SimpleMailMessage build() {
			return new SimpleMailMessage(this);
		}
	    
	}

}
