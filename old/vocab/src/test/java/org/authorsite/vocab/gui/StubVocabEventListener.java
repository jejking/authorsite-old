/*
 * StubVocabEventListener.java, created on 23-Nov-2003 at 21:03:11
 * 
 * Copyright John King, 2003.
 *
 *  StubVocabEventListener.java is part of authorsite.org's VocabManager program.
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
public class StubVocabEventListener implements VocabEventListener {

	private VocabEvent vocabEvent;

	public VocabEvent getVocabEvent() {
		return vocabEvent;
	}

	/**
	 * @see org.authorsite.vocab.gui.VocabEventListener#nodeEventOccurred(org.authorsite.vocab.gui.VocabEvent)
	 */
	public void nodeEventOccurred(VocabEvent event) {
		this.vocabEvent = event;
	}

}
