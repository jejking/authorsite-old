/*
 * AboutAction.java, created on 29-Dec-2003 at 22:53:37
 * 
 * Copyright John King, 2003.
 *
 *  AboutAction.java is part of authorsite.org's VocabManager program.
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

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import javax.swing.*;

import org.apache.log4j.*;

/**
 * Displays a dialogue with some brief information about the program together with its license.
 * 
 * @author jejking
 * @version $Revision: 1.4 $
 */
public class AboutAction extends AbstractVocabAction {

	private static Logger log = Logger.getLogger("org.authorsite.vocab.gui.swing.actions.AboutAction");

	private AboutDialog dialog;
	private Frame frame;

	public AboutAction(Frame frame, Locale locale) {
		super(AboutAction.class.getName(), locale);
		dialog = new AboutDialog(frame, locale);
		this.frame = frame; 
		this.addPropertyChangeListener(new LocalePropertyChangeListener());
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		dialog.setVisible(true);
	}

	private class LocalePropertyChangeListener implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent evt) {
			log.debug("property changed " + evt.getPropertyName() + " to " + evt.getNewValue());
			if (evt.getPropertyName().equals("locale")) {
				dialog.setLocale((Locale)evt.getNewValue());
			}
		}
	}

	/**
	 * A <code>JDialog</code> to display some components with info on the program and its license. 
	 * 
	 */
	private class AboutDialog extends JDialog implements ActionListener {

		private ResourceBundle resources;
		
		AboutDialog(final Frame frame, Locale locale) {
			super(frame);
			setLocale(locale);
			resources = ResourceBundle.getBundle(AboutAction.class.getName(), locale);
			buildDialogue();
			this.addWindowListener(new WindowAdapter() {
				public void windowActivated(WindowEvent event) {
					setLocationRelativeTo(frame); // centres the dialogue on the VocabFrame each time it's shown again
				}
			});
			this.addPropertyChangeListener(new LocaleChangeListener());
		}

		/**
		 * Builds the dialogue up for display.
		 */
		private void buildDialogue() { // TODO make this work for non left to right scripts
			log.debug("buildDialogue called");
			try {
				Container contentPane = getContentPane();
				contentPane.removeAll();

				// set up outlying dialogue frame
				setModal(true);
				setTitle(resources.getString("dialogue_title"));
				setSize(400, 300);
				setResizable(false);

				// JPanel in the top
				JPanel topPanel = new JPanel();
				topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));

				// assemble components for topPanel
				JLabel iconLabel = new JLabel();
				iconLabel.setIcon(new ImageIcon(resources.getString("dialogue_icon")));
				iconLabel.setToolTipText(resources.getString("dialogue_icon_tooltip"));
				JLabel titleVersionLabel = new JLabel();
				titleVersionLabel.setText(resources.getString("dialogue_program_title") + " " + resources.getString(("dialogue_program_version")));

				// add components to topPanel with appropriate spacing
				topPanel.add(Box.createRigidArea(new Dimension(20, 52)));
				topPanel.add(iconLabel);
				topPanel.add(Box.createRigidArea(new Dimension(20, 32)));
				topPanel.add(titleVersionLabel);
				topPanel.add(Box.createHorizontalGlue());

				// TabbedPane in the middle
				JPanel middlePanel = new JPanel();
				JTabbedPane tabbedPane = new JTabbedPane();
				tabbedPane.setPreferredSize(new Dimension(360, 175));

				// we'll have two panels contained in the tabbedPane
				JPanel blurbPanel = new JPanel();
				JTextArea blurbTextArea = new JTextArea();
				blurbTextArea.setPreferredSize(new Dimension(350, 150));
				StringBuffer blurbBuffer = new StringBuffer();
				blurbBuffer.append(resources.getString("dialogue_mission"));
				blurbBuffer.append("\n\n");
				blurbBuffer.append(resources.getString("dialogue_copyright"));
				blurbBuffer.append("\n\n");
				blurbBuffer.append(resources.getString("dialogue_version_info"));
				blurbTextArea.setText(blurbBuffer.toString());
				blurbTextArea.setEditable(false);
				blurbTextArea.setLineWrap(true);
				blurbTextArea.setBackground(blurbPanel.getBackground());
				blurbPanel.add(blurbTextArea);

				JPanel licensePanel = new JPanel();
				JTextArea licenseTextArea = new JTextArea(40, 30);
				licenseTextArea.setText(getLicenseText());
				licenseTextArea.setEditable(false);
				licenseTextArea.setBackground(licensePanel.getBackground());
				JScrollPane licenseScrollPane = new JScrollPane(licenseTextArea);
				licenseScrollPane.setPreferredSize(new Dimension(350, 140));
				licensePanel.add(licenseScrollPane);

				tabbedPane.add(resources.getString("dialogue_blurb_name"), blurbPanel);
				Field field = KeyEvent.class.getField(resources.getString("dialogue_blurb_name_mnemonic"));
				tabbedPane.setMnemonicAt(0, ((Integer) field.get(null)).intValue());

				tabbedPane.add(resources.getString("dialogue_license_name"), licensePanel);
				Field field2 = KeyEvent.class.getField(resources.getString("dialogue_license_name_mnemonic"));
				tabbedPane.setMnemonicAt(1, ((Integer) field2.get(null)).intValue());

				// JPanel at the bottom
				JPanel bottomPanel = new JPanel();
				bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));

				middlePanel.add(tabbedPane);

				// assemble components for bottomPanel
				JButton okButton = new JButton(resources.getString("dialogue_ok"));
				okButton.addActionListener(this);

				bottomPanel.add(Box.createHorizontalGlue());
				bottomPanel.add(okButton);
				bottomPanel.add(Box.createRigidArea(new Dimension(20, 30)));

				contentPane.add(BorderLayout.PAGE_START, topPanel);
				contentPane.add(BorderLayout.CENTER, middlePanel);
				contentPane.add(BorderLayout.PAGE_END, bottomPanel);
			}
			catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/**
		 * @return String with license text read in from file
		 */
		private String getLicenseText() {
			StringBuffer licenseBuffer = new StringBuffer();
			try {
				BufferedReader reader = new BufferedReader(new FileReader("doc/LICENSE.txt"));
				String licenseLine = null;
				while ((licenseLine = reader.readLine()) != null) {
					licenseBuffer.append(licenseLine);
					licenseBuffer.append("\n");
				}
				reader.close();
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				if (licenseBuffer.length() == 0) {
					licenseBuffer.append("Licensed under the GNU General Public License. See www.gnu.org for details");
				}
			}
			return licenseBuffer.toString();
		}

		/**
		 * Hides the dialogue when the OK button is pressed.
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			this.setVisible(false);
		}
		
		private class LocaleChangeListener implements PropertyChangeListener {

			/**
			 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
			 */
			public void propertyChange(PropertyChangeEvent evt) {
				log.debug("Dialogue property changed " + evt.getPropertyName() + " to " + evt.getNewValue());
				if (evt.getPropertyName().equals("locale")) {
					Runnable dialogRebuilder = new Runnable() {
						public void run() {
							resources = ResourceBundle.getBundle(AboutAction.class.getName(), getLocale());
							buildDialogue();	
						}
					};
					SwingUtilities.invokeLater(dialogRebuilder);
				}
			}
			
		}

	}

}