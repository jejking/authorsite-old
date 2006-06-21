/*
 * VocabEvent.java, created on 04-Nov-2003 at 22:08:41
 * 
 * Copyright John King, 2003.
 *
 *  VocabEvent.java is part of authorsite.org's VocabManager program.
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

import java.util.*;
import java.util.EventObject;

/**
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class VocabEvent extends EventObject {

	private VocabEventType eventType;
	private VocabNode vocabNode;
	private Set results;

	/**
	 * @param mediator
	 * @param type
	 * @param results
	 */
	public VocabEvent(Object source, VocabEventType eventType, Set results) {
		super(source);
		this.eventType = eventType;
		this.results = results;
	}

	/**
	 * @param source
	 */
	public VocabEvent(Object source, VocabEventType eventType, VocabNode vocabNode) {
		super(source);
		this.eventType = eventType;
		this.vocabNode = vocabNode;
	}

	/**
	 * @return
	 */
	public VocabEventType getEventType() {
		return eventType;
	}

	/**
	 * @return
	 */
	public VocabNode getVocabNode() {
		return vocabNode;
	}
	
	public Set getResultsSet() {
		return results;
	}

}
