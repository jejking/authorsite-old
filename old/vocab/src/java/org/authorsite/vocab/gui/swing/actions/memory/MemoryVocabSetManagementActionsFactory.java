/*
 * MemoryVocabSetManagementActionsFactory.java, created on 21-Dec-2003 at 21:27:03
 * 
 * Copyright John King, 2003.
 *
 *  MemoryVocabSetManagementActionsFactory.java is part of authorsite.org's VocabManager program.
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
package org.authorsite.vocab.gui.swing.actions.memory;

import java.util.*;

import org.authorsite.vocab.gui.swing.actions.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class MemoryVocabSetManagementActionsFactory implements VocabSetManagementActionFactory {

	private Locale locale;

	/**
	 * @see org.authorsite.vocab.gui.swing.actions.VocabSetManagementActionFactory#getVocabSetManagementActions()
	 */
	public List getVocabSetManagementActions() {
		if (locale == null) {
			locale = Locale.getDefault();
		}
		
		List list = new LinkedList();
		list.add(new NewFileAction(locale));
		list.add(new OpenFileAction(locale));
		list.add(new SaveFileAction(locale));
		list.add(new SaveFileAsAction(locale));
		return list;
	}

	/**
	 * @see org.authorsite.vocab.gui.swing.actions.VocabSetManagementActionFactory#setLocale(java.util.Locale)
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}
