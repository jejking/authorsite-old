/*
 * DeleteSemanticRelCommand.java, created on 18-Nov-2003 at 23:05:58
 * 
 * Copyright John King, 2003.
 *
 *  DeleteSemanticRelCommand.java is part of authorsite.org's VocabManager program.
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
package org.authorsite.vocab.gui;

import org.authorsite.vocab.core.*;
import org.authorsite.vocab.core.dto.*;
import org.authorsite.vocab.exceptions.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.2 $
 */
public class DeleteSemanticRelCommand implements VocabCommand {

	private SemanticRelDTO relDTO;
	private VocabSet vocabSet;

	public DeleteSemanticRelCommand(SemanticRelDTO relDTO) {
		this.relDTO = relDTO;
	}

	/**
	 * @see org.authorsite.vocab.gui.VocabCommand#execute(org.authorsite.vocab.gui.VocabMediator)
	 */
	public void execute(VocabMediator mediator) throws VocabException {
		vocabSet = mediator.getVocabSet();
		VocabNode fromNode = vocabSet.findVocabNodeById(relDTO.getFromNodeId());
		VocabNode toNode = vocabSet.findVocabNodeById(relDTO.getToNodeId());
		fromNode.removeSemanticRel(toNode, relDTO.getRelationshipType());
		mediator.notifyListeners(this, fromNode);
	}

	/**
	 * @see org.authorsite.vocab.gui.VocabCommand#getEventType()
	 */
	public VocabEventType getEventType() {
		return VocabEventType.REL_DELETED;
	}

}
