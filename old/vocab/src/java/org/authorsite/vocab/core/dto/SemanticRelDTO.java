/*
 * SemanticRelDTO.java, created on 17-Sep-2003 at 22:39:41
 * 
 * Copyright John King, 2003.
 *
 *  SemanticRelDTO.java is part of authorsite.org's VocabManager program.
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

import java.io.Serializable;

/**
 * Lightweight, bean-like reprentation of a <code>MemorySemanticRel</code> for transport 
 * and persistence purposes.
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public final class SemanticRelDTO implements Serializable {

	private String relationshipType;
	private Integer toNodeId;
	private Integer fromNodeId;
	
	public SemanticRelDTO() {
	}

	
	/**
	 * @return
	 */
	public String getRelationshipType() {
		return relationshipType;
	}

	/**
	 * @return
	 */
	public Integer getToNodeId() {
		return toNodeId;
	}

	/**
	 * @param string
	 */
	public void setRelationshipType(String string) {
		relationshipType = string;
	}

	/**
	 * @param integer
	 */
	public void setToNodeId(Integer integer) {
		toNodeId = integer;
	}
	
	public void setFromNodeId(Integer integer) {
		fromNodeId = integer;
	}
	
	public Integer getFromNodeId() {
		return fromNodeId;
	}

}
