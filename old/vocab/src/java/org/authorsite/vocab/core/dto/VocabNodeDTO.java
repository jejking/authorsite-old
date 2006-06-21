/*
 * VocabNodeDTO.java, created on 17-Sep-2003 at 22:38:41
 * 
 * Copyright John King, 2003.
 *
 *  VocabNodeDTO.java is part of authorsite.org's VocabManager program.
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
package org.authorsite.vocab.core.dto;
import java.io.*;
import java.util.*;
/**
 * Very simple representation of a <code>MemoryVocabNode</code> which
 * can be serialized to disk and is decoupled from the implementation.
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public final class VocabNodeDTO implements Serializable {

	private HashSet semanticRelSet;
	private Integer id;
	private String text;
	private Locale locale;
	private Date dateCreated;
	private String annotation;
	private String nodeType;
	private String register;
	
	public VocabNodeDTO() {
		semanticRelSet = new HashSet();
	}
	
	public void addSemanticRel(SemanticRelDTO semanticRel) {
		semanticRelSet.add(semanticRel);
	}
	
	public Set getSemanticRels() {
		return semanticRelSet;
	}

	/**
	 * @return
	 */
	public String getAnnotation() {
		return annotation;
	}

	/**
	 * @return
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @return
	 */
	public String getNodeType() {
		return nodeType;
	}

	/**
	 * @return
	 */
	public String getRegister() {
		return register;
	}

	/**
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param string
	 */
	public void setAnnotation(String string) {
		annotation = string;
	}

	/**
	 * @param date
	 */
	public void setDateCreated(Date date) {
		dateCreated = date;
	}

	/**
	 * @param integer
	 */
	public void setId(Integer integer) {
		id = integer;
	}

	/**
	 * @param locale
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * @param string
	 */
	public void setNodeType(String string) {
		nodeType = string;
	}

	/**
	 * @param string
	 */
	public void setRegister(String string) {
		register = string;
	}

	/**
	 * @param string
	 */
	public void setText(String string) {
		text = string;
	}

}
