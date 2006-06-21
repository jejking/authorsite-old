/*
 * VocabAbstractAction.java, created on 07-Dec-2003 at 21:28:30
 * 
 * Copyright John King, 2003.
 *
 *  VocabAbstractAction.java is part of authorsite.org's VocabManager program.
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
package org.authorsite.vocab.gui.swing.actions;

import java.awt.event.*;
import java.beans.*;
import java.lang.reflect.*;
import java.util.*;

import javax.swing.*;

import org.apache.log4j.Logger;

/**
 * 
 * @author jejking
 * @version $Revision: 1.4 $
 */
public abstract class AbstractVocabAction extends AbstractAction {

	private static final String TEXT = "text";
	private static final String ICON = "icon";
	private static final String DESC = "desc";
	private static final String MNEMONIC = "mnemonic";
	
	private static Logger log = Logger.getLogger("org.authorsite.vocab.gui.swing.actions.AbstractVocabAction");

	private ResourceBundle actionResources;

	private boolean iconFlag = false;
	private boolean mnemonicFlag = false;
	private String actionName;
	
	private ImageIcon imageIcon = null;

	public AbstractVocabAction(final String actionName, Locale locale) {
		log.debug("super constructor called for " + actionName);
		actionResources = ResourceBundle.getBundle(actionName, locale);
		putValue("locale", locale);
		this.actionName = actionName;
		this.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				// only react if the Locale property is changed
				if (evt.getPropertyName().equals("locale")) {
					log.debug("detected locale change in " + actionName + " to " + evt.getNewValue());
					// reload the resource bundle and reset the text accordingly
					// actionResources = ResourceBundle.getBundle(actionName, (Locale) AbstractVocabAction.this.getValue("locale"));
					actionResources = ResourceBundle.getBundle(actionName, (Locale) evt.getNewValue());
					setUpAction();
				}
			}
		});
		setUpAction();
	}

	private void setUpAction() {
		Enumeration resourceKeys = actionResources.getKeys();
		iconFlag = false;
		mnemonicFlag = false;
		while (resourceKeys.hasMoreElements()) {
			String nextKey = resourceKeys.nextElement().toString();
			if (nextKey.equals(ICON)) {
				iconFlag = true;
				continue;
			}
			if (nextKey.equals(MNEMONIC)) {
				mnemonicFlag = true;
			}
		}
		// need to set text, icon, description and mnemonic according to what is set in the resource bundle
		putValue(NAME, actionResources.getString(TEXT));
		if (iconFlag) {
			imageIcon = new ImageIcon(actionResources.getString(ICON));
			putValue(SMALL_ICON, imageIcon);
		}
		putValue(SHORT_DESCRIPTION, actionResources.getString(DESC));
		if (mnemonicFlag) {
			try {
				Field field = KeyEvent.class.getField(actionResources.getString(MNEMONIC));
				putValue(MNEMONIC_KEY, field.get(null));
			}
			catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return actionName;
	}

}
