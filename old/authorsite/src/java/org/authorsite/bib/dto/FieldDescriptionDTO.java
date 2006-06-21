/*
 * FieldDescriptionDTO.java
 *
 * Created on 25 November 2002, 17:04
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/dto/FieldDescriptionDTO.java,v 1.2 2003/03/01 13:37:36 jejking Exp $
 *
 * Copyright (C) 2002  John King
 *
 * This file is part of the authorsite.org bibliographic
 * application.
 *
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.authorsite.bib.dto;

/**
 * <p>
 * This class is used to communicate details about a field, as defined in <code>bibTypesRelationships.xml</code>
 * to the client. The client can use this information to perform client side validation or to construct
 * forms appropriately.
 * </p>
 *
 * <p>
 * It will also be used by the integrity checks on the server side to ensure that attempts to submit oversized 
 * variables to the entity beans are trapped.
 * </p>
 *
 * <p>
 * In general, the <code>FieldDescriptionDTO</code>s will be generated from the <code>RulesProcessor</code> singleton
 * which is called by the <code>RulesManagerBean</code> to provide these details in response to client requests.
 * </p>
 *
 * @see org.authorsite.bib.ejb.services.rules.RulesProcessor
 * @see org.authorsite.bib.ejb.services.rules.RulesManagerBean
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class FieldDescriptionDTO {
    
    private String fieldName;
    private String fieldType;
    private String fieldSize;
    private String priority;
    private int maxChars = -1;
    
    /** Creates a new instance of FieldDescriptionDTO */
    public FieldDescriptionDTO(String newFieldName, String newFieldType, String newFieldSize, String newPriority) {
        setFieldName(newFieldName);
        setFieldType(newFieldType);
        setFieldSize(newFieldSize);
        setPriority(newPriority);
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public void setFieldName(String newFieldName) {
        // according to our naming convention, the field name should not continue 
        fieldName = newFieldName;
    }
    
    public String getFieldType() {
        return fieldType;
    }
    
    /** Sets the fieldType of the object.
     * @param newFieldType A string describing the type of the field being described. The string must
     * conform to the list of permissable types defined in
     * <code>bibTypesRelationships.xsd</code>. These are:
     * <ul>
     * <li>string</li>
     * <li>integer</li>
     * <li>float</li>
     * <li>text</li>
     * <li>blob</li>
     * <li>boolean</li>
     * </ul>
     */    
    public void setFieldType(String newFieldType) {
        // check we're setting a valid fieldType
        if (newFieldType.equals("string") || newFieldType.equals("integer") || newFieldType.equals("float") || newFieldType.equals("text")
         || newFieldType.equals("blob") || newFieldType.equals("boolean")) {
             fieldType = newFieldType;
        }
        else {
            throw new IllegalArgumentException("illegal fieldType");
        }
    }
    
    public String getFieldSize() {
        return fieldSize;
    }
    
    /** Sets the string describing fieldSize of the DTO.
     * @param newFieldSize string describing the size of the field. This value will be used by the client
     * to set up the input and display forms. The string passed in must conform to the
     * list of permissable values defined in <code>bibTypesRelationships.xsd</code>.
     * These are:
     * <ul>
     * <li>small</li>
     * <li>medium</li>
     * <li>large</li>
     * </ul>
     */    
    public void setFieldSize(String newFieldSize) {
        if (newFieldSize.equals("small") || newFieldSize.equals("medium") || newFieldSize.equals("large")) {
            fieldSize = newFieldSize;
        }
        else {
            throw new IllegalArgumentException("illegal field size");
        }
    }
    
    public String getPriority() {
        return priority;
    }
    
    /** Sets the string describing the priority of the field. The client may use this
     * value to decide in which order to display screens describing the fields.
     * @param newPriority String describing the priority of the field. The values must be one of those
     * listed as permissable values in <code>bibTypesRelationships.xsd</code>. These
     * are:
     * <ul>
     * <li>obligatory</li>
     * <li>expected</li>
     * <li>permitted</li>
     * </ul>
     *
     */    
    public void setPriority(String newPriority) {
        if (newPriority.equals("obligatory") || newPriority.equals("expected") || newPriority.equals("permitted")) {
            priority = newPriority;
        }
        else {
            throw new IllegalArgumentException ("illegal priority");
        }
    }
    
    /** Gets an int describing the maximum size of a string to be set in the DTO which
     * contains the corresponding field.
     * @return Returns an int describing the maximum size of a string to be set in the DTO which
     * contains the corresponding field. If there is no hard limit, returns -1.
     */    
    public int getMaxChars() {
        return maxChars;
    }
    
    /** MaxChars is intended to prevent attempts to insert excessively long strings into
     * the database. For example, it could be used to prevent attempts to submit a 500
     * character string into a database column of only 400 characters. This feature
     * should be used as an additional safety feature. The actual size will depend on
     * the configuration of the sql generation script for the database used in the
     * application.
     * @param newMaxChars int representing the maximum character size of the field.
     */    
    public void setMaxChars(int newMaxChars) {
        if (newMaxChars > 0 || newMaxChars == -1) {
            maxChars = newMaxChars;
        }
        else {
            throw new IllegalArgumentException("illegal maxChars. Must be either greater than 0 or -1");
        }
    }
    
    public String toString() {
        return ("Field Description: Name = " + fieldName + ", Type = " + fieldType + ", Size = " + fieldSize + ", Priority = " + priority + ", MaxChars = " + maxChars);
    }
}
