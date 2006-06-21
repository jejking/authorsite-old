/*
 * Person.java, created on 06-Mar-2004 at 22:02:53
 * 
 * Copyright John King, 2004.
 *
 *  Person.java is part of authorsite.org's MailArchive program.
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
package org.authorsite.mailarchive.model;

import java.io.*;
import java.util.*;

/**
 * Abstraction of an individual human being, who may have one or more Email Addresses.
 * 
 * <p>This representation of a person is relatively simple. Its intention is to provide a way 
 * to link an individual with the various email addresses they may have had over time
 * as they change university, job, ISP, etc. The archive may implement attempts to identify
 * common traits between email addresses and suggest that they belong to the same
 * individual, or it may need manual intervention. The latter is the more likely scenario.</p>
 * 
 * <p>It should be noted that this is a relatively simple and naive representation of an
 * individual. In particular, the assumption that a person has one and only one name over
 * the course of their life is weak. This current version will work on the assumption that their
 * name by which a person is currently known provides sufficient granularity for the immediate
 * task for building a mail archive.</p>
 * 
 * <p>The fields used are:</p>
 * 
 * <ul>
 * <li>prefix: honorific title, Dr, Prof, Lord, Mrs, etc</li>
 * <li>givenName: name not inherited from family, derived from parent's name, etc. Typically
 * the name by which are person is known to family and friends.</li>
 * <li>mainName: family name, however derived</li>
 * <li>otherNames: any other names, which should be recorded</li>
 * <li>suffix: anything appended to a name, qualifying it: III, Esq, Jnr, Snr, etc</li>
 * <li>genderCode: integer representing gender, according to ISO 5218. Default value
 * should be 0, Not Known. The values are:</li>
 * <ul>
 * 	<li>0: Not known</li>
 * 	<li>1: Male</li>
 * 	<li>2: Female</li>
 * 	<li>9: Not specified</li>
 * </ul>
 * <li>dateOfBirth: date of birth</li>
 * <li>dateOfDeath: date of death</li>
 * <li>emailAdresses: set of that person's emailAddresses. Note that <code>EmailAddress</code>
 * also has visibility of an associate <code>Person</code> and implementations must
 * ensure logical integrity here.</li>
 * </ul>
 * 
 * @author jejking
 * @version $Revision: 1.5 $
 */
public interface Person extends Identifiable {
	
    /**
     * @todo replace with proper enum
     */
    public static final int NOT_KNOWN = 0;
    public static final int MALE = 1;
    public static final int FEMALE = 2;
    public static final int UNSPECIFIED = 9;
    
	public String getPrefix();
	
	public void setPrefix(String newPrefix);
	
	public String getGivenName();
	
	public void setGivenName(String newGivenName);
	
	public String getMainName();
	
	public void setMainName(String newMainName);
	
	public String getOtherNames();
	
	public void setOtherNames(String newOtherNames);
	
	public String getSuffix();
	
	public void setSuffix(String newSuffix);
	
	public int getGenderCode();
	
	public void setGenderCode(int newGenderCode);
	   
	public Date getDateOfBirth();
	
	public void setDateOfBirth(Date newDateOfBirth);
	
	public Date getDateOfDeath();

	public void setDateOfDeath(Date newDateOfDeath);
	
	public Set getEmailAddresses();
	
	public void setEmailAddresses(Set newEmailAddresses);
	
	public void addEmailAddress(EmailAddress newEmailAddress);
	
	public void removeEmailAddress(EmailAddress emailAddressToRemove);
		
}