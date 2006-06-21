/*
 * SearchCommand.java, created on 17-Nov-2003 at 22:08:25
 * 
 * Copyright John King, 2003.
 *
 *  SearchCommand.java is part of authorsite.org's VocabManager program.
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

import java.util.Set;

import org.authorsite.vocab.core.*;
import org.authorsite.vocab.exceptions.VocabException;

/**
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class FindNodesCommand implements VocabCommand {

	private NodeQueryBean nodeQuery;
	private VocabSet vocabSet;

	public FindNodesCommand(NodeQueryBean nodeQuery) {
		this.nodeQuery = nodeQuery;
	}

	/**
	 * @see org.authorsite.vocab.gui.VocabCommand#execute(org.authorsite.vocab.gui.VocabMediator)
	 */
	public void execute(VocabMediator mediator) throws VocabException {
		vocabSet = mediator.getVocabSet();
		Set results = vocabSet.findNodes(nodeQuery);
		mediator.notifyListeners(this, results);
	}

	/**
	 * @see org.authorsite.vocab.gui.VocabCommand#getEventType()
	 */
	public VocabEventType getEventType() {
		return VocabEventType.SEARCH_RESULTS;
	}

}
