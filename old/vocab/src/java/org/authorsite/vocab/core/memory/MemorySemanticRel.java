/*
 * MemorySemanticRel.java, created on 09-Sep-2003 at 23:00:52
 * 
 * Copyright John King, 2003.
 *
 *  MemorySemanticRel.java is part of authorsite.org's VocabManager program.
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
package org.authorsite.vocab.core.memory;

import org.authorsite.vocab.core.*;
import org.authorsite.vocab.core.dto.*;
import org.authorsite.vocab.exceptions.*;
/**
 * Implementation of <code>SemanticRel</code> in memory with persistence handled by object serialization to disk.
 * 
 * @author jejking
 * @version $Revision: 1.5 $
 */
public class MemorySemanticRel extends AbstractSemanticRel {

	protected MemorySemanticRel(VocabNode fromNode, String relationshipType, Integer toNodeId) throws VocabException {
		super(fromNode, relationshipType, toNodeId);
	}
	
	protected MemorySemanticRel(VocabNode fromNode, String relationshipType, VocabNode toNode) throws VocabException {
		super(fromNode, relationshipType, toNode);
	}

	protected SemanticRelDTO getSerializableMemorySemanticRel() {
		SemanticRelDTO serialized = new SemanticRelDTO();
		serialized.setRelationshipType(getRelationshipType());
		serialized.setToNodeId(getToNodeId());
		serialized.setFromNodeId(getFromNode().getId());
		return serialized;
	}

	/**
	 * @see org.authorsite.vocab.core.SemanticRel#getToNode()
	 */
	public VocabNode getToNode() {
		if (toNode == null) {
			try {
				toNode =  ((MemoryVocabNode)getFromNode()).getVocabSet().findVocabNodeById(getToNodeId());
			}
			catch (VocabException ve) {
				ve.printStackTrace(); // we shouldn't actually be getting on of these
			}
		}
		return toNode;
	}

}
