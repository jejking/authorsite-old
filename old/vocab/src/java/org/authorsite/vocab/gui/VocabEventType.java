/*
 * VocabEventType.java, created on 04-Nov-2003 at 22:23:04
 * 
 * Copyright John King, 2003.
 *
 *  VocabEventType.java is part of authorsite.org's VocabManager program.
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

/**
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class VocabEventType {
	
	private String eventType;
	
	private VocabEventType(String eventType) {
		this.eventType = eventType;
	}
	
	public static final VocabEventType NODE_CREATED = new VocabEventType("node created");
	public static final VocabEventType NODE_DELETED = new VocabEventType("node deleted");
	public static final VocabEventType NODE_EDITED = new VocabEventType("node edited");
	
	public static final VocabEventType REL_CREATED = new VocabEventType("semantic rel created");
	public static final VocabEventType REL_DELETED = new VocabEventType("semantic rel deleted");
	
	public static final VocabEventType SEARCH_RESULTS = new VocabEventType("search results");
	
}
