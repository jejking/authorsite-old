/*
 * VocabNodeCommand.java, created on 14-Nov-2003 at 20:44:14
 * 
 * Copyright John King, 2003.
 *
 *  VocabNodeCommand.java is part of authorsite.org's VocabManager program.
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
 * @version $Revision: 1.1 $
 */
public final class CreateVocabNodeCommand implements VocabCommand {

	private VocabSet vocabSet;

	private VocabNodeDTO nodeDTO;
	
	public CreateVocabNodeCommand(VocabNodeDTO nodeDTO) {
		this.nodeDTO = nodeDTO;	
	}

	/**
	 * @see org.authorsite.vocab.gui.VocabCommand#execute(org.authorsite.vocab.gui.VocabMediator)
	 */
	public void execute(VocabMediator mediator) throws VocabException {
		vocabSet = mediator.getVocabSet();
		VocabNode newNode = vocabSet.createVocabNode(nodeDTO.getText(), nodeDTO.getNodeType(), nodeDTO.getLocale());
		if (nodeDTO.getRegister() != null && nodeDTO.getRegister().length() > 0) {
			newNode.setRegister(nodeDTO.getRegister());
		}
		if (nodeDTO.getAnnotation() != null && nodeDTO.getAnnotation().length() > 0) {
			newNode.setAnnotation(nodeDTO.getAnnotation());
		}
		// tell mediator to notify listening GUI objects
		mediator.notifyListeners(this, newNode);
	}

	/**
	 * @see org.authorsite.vocab.gui.VocabCommand#getEventType()
	 */
	public VocabEventType getEventType() {
		return VocabEventType.NODE_CREATED;
	}

}
