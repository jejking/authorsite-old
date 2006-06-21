/*
 * VocabNodeJPanel.java, created on 06-Jan-2004 at 21:56:54
 * 
 * Copyright John King, 2004.
 *
 *  VocabNodeJPanel.java is part of authorsite.org's VocabManager program.
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
package org.authorsite.vocab.gui.swing;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import org.authorsite.vocab.core.*;
import org.authorsite.vocab.core.dto.*;

/**
 * A <code>JPanel</code> to display a single <code>VocabNode</code>.
 *  
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class VocabNodeJPanel extends JPanel {

	private VocabNodeDTO node;
	private ResourceBundle nodeTypesBundle;
	private ResourceBundle registerBundle;
	private ResourceBundle relationshipsBundle;
	private ResourceBundle nodePropertiesBundle;
	private Map nodePropertyLabels;
	private Map nodePropertyDisplays;

	public VocabNodeJPanel(Locale locale) {
		setLocale(locale);
		nodePropertyLabels = new HashMap();
		nodePropertyDisplays = new HashMap();
		populateResourceBundles();
		populateNodePropertyLabels();
		populateNodePropertyDisplays();
	}

	/**
	 * Sets up the widgets to display/edit a VocabNode.
	 */
	private void populateNodePropertyDisplays() {
		// text goes in a scrollable JTextArea
		nodePropertyDisplays.put("text", new JScrollPane(new JTextArea(40, 10)));

		// locale goes in a JComboBox
		JComboBox localeComboBox = new JComboBox(Locale.getAvailableLocales());
		localeComboBox.setRenderer(new LocaleCellRenderer());
		nodePropertyDisplays.put("locale", localeComboBox);

		// node type goes in a JComboBox
		Enumeration nodeTypeKeys = nodeTypesBundle.getKeys();
		Vector nodeTypeKeysVector = new Vector();
		while (nodeTypeKeys.hasMoreElements()) {
			nodeTypeKeysVector.add(nodeTypeKeys.nextElement()); // urgh. but resource bundles give us enumerations and combo boxes want vectors... yuk
		}
		JComboBox nodeTypesComboBox = new JComboBox(nodeTypeKeysVector);
		nodeTypesComboBox.setRenderer(new NodeTypeCellRenderer());
		nodePropertyDisplays.put("nodeType", nodeTypesComboBox);

		// annotation goes in a scrollable JTextArea
		nodePropertyDisplays.put("annotation", new JScrollPane(new JTextArea(40, 40)));
		
		// date created is a JLabel
		nodePropertyDisplays.put("dateCreated", new JLabel());

		// nodeId is a JLabel
		nodePropertyDisplays.put("nodeId", new JLabel());

	}

	private void populateNodePropertyLabels() {
		nodePropertyLabels.put("text", new JLabel(nodePropertiesBundle.getString("text")));
		nodePropertyLabels.put("locale", new JLabel(nodePropertiesBundle.getString("locale")));
		nodePropertyLabels.put("nodeType", new JLabel(nodePropertiesBundle.getString("nodeType")));
		nodePropertyLabels.put("register", new JLabel(nodePropertiesBundle.getString("register")));
		nodePropertyLabels.put("dateCreated", new JLabel(nodePropertiesBundle.getString("dateCreated")));
		nodePropertyLabels.put("nodeId", new JLabel(nodePropertiesBundle.getString("nodeId")));
	}

	private void populateResourceBundles() {
		nodeTypesBundle = ResourceBundle.getBundle("org.authorsite.vocab.resources.terms.nodeTypes", getLocale());
		registerBundle = ResourceBundle.getBundle("org.authorsite.vocab.resources.terms.register", getLocale());
		relationshipsBundle = ResourceBundle.getBundle("org.authorsite.vocab.resources.terms.relationships", getLocale());
		nodePropertiesBundle = ResourceBundle.getBundle("org.authorsite.vocab.resources.terms.nodeProperties", getLocale());
	}

	public VocabNodeDTO getVocabNodeDTO() {
		return node;
	}

	public void setVocabNode(VocabNodeDTO newVocabNodeDTO) {
		node = newVocabNodeDTO;
	}

	public void setVocabNode(VocabNode node) {
		this.node = node.getVocabNodeDTO();
	}

	private class LocaleCellRenderer extends JLabel implements ListCellRenderer {

		/**
		 * Inspired by http://java.sun.com/docs/books/tutorial/uiswing/components/combobox.html#renderer
		 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
		 */
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			int selectedIndex = ((Integer) value).intValue();

			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			}
			else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			// basically, all we have to do is return localized text for the Locale
			Locale localeToDisplay = (Locale) value;
			this.setText(localeToDisplay.getDisplayLanguage(getLocale()));
			return this;
		}
	}

	private class NodeTypeCellRenderer extends JLabel implements ListCellRenderer {

		/**
		 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
		 */
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			int selectedIndex = ((Integer) value).intValue();

			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			}
			else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			// basically, all we have to do is return localized text for the nodeType. look it up in resource bundle
			this.setText(nodeTypesBundle.getString((String)value));
			return this;
		}
	}
}
