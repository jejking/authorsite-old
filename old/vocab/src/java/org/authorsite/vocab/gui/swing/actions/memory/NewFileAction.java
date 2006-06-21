/*
 * NewFileAction.java, created on 21-Dec-2003 at 21:31:47
 * 
 * Copyright John King, 2003.
 *
 *  NewFileAction.java is part of authorsite.org's VocabManager program.
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

import java.awt.event.*;
import java.util.*;

import org.authorsite.vocab.gui.swing.actions.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class NewFileAction extends AbstractVocabAction {

	public NewFileAction(Locale locale) {
		super(NewFileAction.class.getName(), locale);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		System.out.println("actionPerformed on NewFileAction called");
	}

}
