/*
 * VocabFrame.java, created on 24-Nov-2003 at 23:12:02
 * 
 * Copyright John King, 2003.
 *
 *  VocabFrame.java is part of authorsite.org's VocabManager program.
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
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import org.authorsite.vocab.core.*;
import org.authorsite.vocab.exceptions.*;
import org.authorsite.vocab.gui.*;
import org.authorsite.vocab.gui.swing.actions.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.7 $
 */
public class VocabFrame extends JFrame {

	private VocabMediator mediator;
	private Properties vocabProps;

	private Map actionMap;
	private LocaleChangeListener localeChangeListener;

	/**
	 * @param mediator
	 * @param guiLocale
	 * @param vocabProps
	 */
	public VocabFrame(VocabMediator mediator, Locale guiLocale, Properties vocabProps) {
		this.mediator = mediator;
		setLocale(guiLocale);
		this.vocabProps = vocabProps;
		actionMap = new HashMap();
		localeChangeListener = new LocaleChangeListener();
	}

	public void runGUI() {
		buildGUI();
		Runnable runnable = new FrameShower(this);
		EventQueue.invokeLater(runnable);
	}

	private void buildGUI() {
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("images/icons/vocab.gif"));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				VocabSet set = mediator.getVocabSet();
				try {
					set.dispose(); // tell VocabSet to clean itself up tidily before we exit the VM
				}
				catch (VocabException ve) {
					// erm, not a lot we can do at this point
				}
			}
		});
		buildMiscGUIComponents();
		assembleActions();
		this.setJMenuBar(buildMenu());
		setDefaultLookAndFeelDecorated(true);
		this.setSize(600, 500);
	}

	private void buildMiscGUIComponents() {
		ResourceBundle generalGUIResources = ResourceBundle.getBundle("org.authorsite.vocab.resources.gui.GeneralGUIResources", getLocale());
		this.setTitle(generalGUIResources.getString("frameTitle"));
	}

	/**
	 * Sets up all the actions needed for use in the menu and toolbar and puts them in the <code>actionMap</code>
	 * .
	 */
	private void assembleActions() {
		try {
			Class mgtActionsFactoryClass = Class.forName(vocabProps.getProperty("VocabManagerMenuFactoryClass"));
			VocabSetManagementActionFactory mgtActionsFactory = (VocabSetManagementActionFactory) mgtActionsFactoryClass.newInstance();
			mgtActionsFactory.setLocale(getLocale());
			java.util.List actions = mgtActionsFactory.getVocabSetManagementActions();
			actionMap.put("mgtActions", actions);
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actionMap.put("exitAction", new ExitAction(getLocale()));
		actionMap.put("cutAction", new CutAction(getLocale()));
		actionMap.put("copyAction", new CopyAction(getLocale()));
		actionMap.put("pasteAction", new PasteAction(getLocale()));
		actionMap.put("createVocabNodeAction", new CreateVocabNodeAction(mediator, getLocale()));
		actionMap.put("aboutAction", new AboutAction(this, getLocale()));
	}

	private void buildToolBar() {
		// get actions appropriate to the set in use

		// build the standard buttons
	}

	private JMenuBar buildMenu() {
		JMenuBar menubar = new JMenuBar();
		ResourceBundle menuResources = ResourceBundle.getBundle("org.authorsite.vocab.resources.gui.MenuResources", getLocale());

		// get actions appropriate to the set in use
		JMenu mgtMenu = new JMenu();
		mgtMenu.setText(menuResources.getString("mgtMenu"));
		mgtMenu.setMnemonic(menuResources.getString("mgtMenuMnemonic").charAt(0));
		java.util.List actions = (java.util.List) actionMap.get("mgtActions");
		Iterator actionsIt = actions.iterator();
		while (actionsIt.hasNext()) {
			JMenuItem item = new JMenuItem((AbstractVocabAction) actionsIt.next());
			mgtMenu.add(item);
		}
		mgtMenu.addSeparator();
		mgtMenu.add((AbstractVocabAction) actionMap.get("exitAction"));
		menubar.add(mgtMenu);

		// build the standard menus and menu items

		JMenu editMenu = new JMenu();
		editMenu.setText(menuResources.getString("editMenu"));
		editMenu.setMnemonic(menuResources.getString("editMenuMnemonic").charAt(0));
		JMenuItem cutMenuItem = new JMenuItem((AbstractVocabAction) actionMap.get("cutAction"));
		cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		editMenu.add(cutMenuItem);
		JMenuItem copyMenuItem = new JMenuItem((AbstractVocabAction) actionMap.get("copyAction"));
		copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		editMenu.add(copyMenuItem);
		JMenuItem pasteMenuItem = new JMenuItem((AbstractVocabAction) actionMap.get("pasteAction"));
		pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		editMenu.add(pasteMenuItem);
		menubar.add(editMenu);

		JMenu toolsMenu = new JMenu();
		toolsMenu.setText(menuResources.getString("toolsMenu"));
		toolsMenu.setMnemonic(menuResources.getString("toolsMenuMnemonic").charAt(0));
		toolsMenu.add(new JMenuItem((AbstractVocabAction) actionMap.get("createVocabNodeAction")));
		menubar.add(toolsMenu);

		JMenu localeMenu = new JMenu();
		localeMenu.setText(menuResources.getString("localeMenu"));
		localeMenu.setMnemonic(menuResources.getString("localeMenuMnemonic").charAt(0));
		buildLocaleMenu(localeMenu);
		menubar.add(localeMenu);

		JMenu helpMenu = new JMenu();
		helpMenu.setText(menuResources.getString("helpMenu"));
		helpMenu.setMnemonic(menuResources.getString("helpMenuMnemonic").charAt(0));
		helpMenu.add(new JMenuItem((AbstractVocabAction) actionMap.get("aboutAction")));
		menubar.add(helpMenu);

		return menubar;
	}

	/**
	 * @param localeMenu
	 */
	private void buildLocaleMenu(JMenu localeMenu) {
		Properties i18nProps = new Properties();
		try {
			i18nProps.load(new FileInputStream("conf/i18n.properties"));
			StringTokenizer supportedLocalesTokenizer = new StringTokenizer(i18nProps.getProperty("supportedLocales"));
			while (supportedLocalesTokenizer.hasMoreTokens()) {
				Locale supportedLocale = new Locale(supportedLocalesTokenizer.nextToken());
				JRadioButtonMenuItem supportedLocaleMenuItem = new JRadioButtonMenuItem();
				supportedLocaleMenuItem.setLocale(supportedLocale);
				if (supportedLocale.equals(getLocale())) {
					supportedLocaleMenuItem.setSelected(true);
				}
				supportedLocaleMenuItem.setText(supportedLocale.getDisplayLanguage(supportedLocale));
				supportedLocaleMenuItem.addActionListener(localeChangeListener);
				localeMenu.add(supportedLocaleMenuItem);
			}
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class LocaleChangeListener implements ActionListener {

		/**
		 * Reacts to change in preferred GUI locale and rebuilds the GUI with everything properly localised to new locale.
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			Runnable hide = new Runnable() {
				public void run() {
					setVisible(false);
				}
			};
			SwingUtilities.invokeLater(hide);
			Component component = (Component) e.getSource();
			setLocale(component.getLocale());
			// loop through all the actions and change their locale. they should pick up the changed text
			Iterator actionsIt = actionMap.keySet().iterator();
			while (actionsIt.hasNext()) {
				Object actionObj = actionMap.get(actionsIt.next());
				if (actionObj instanceof AbstractAction) {
					AbstractVocabAction action = (AbstractVocabAction) actionObj;
					processAction(action);
				}
				else if (actionObj instanceof java.util.List) {
					java.util.List actionList = (java.util.List) actionObj;
					Iterator listIt = actionList.iterator();
					while (listIt.hasNext()) {
						AbstractVocabAction action = (AbstractVocabAction) listIt.next();
						processAction(action);
					}
				}
			}
			Runnable menuRebuilder = new Runnable() {
				public void run() {
					buildMiscGUIComponents();
					setJMenuBar(buildMenu());
				}
			};
			SwingUtilities.invokeLater(menuRebuilder);
			Runnable showVisibleAgain = new Runnable() {
				public void run() {

					setVisible(true);
				}
			};
			SwingUtilities.invokeLater(showVisibleAgain);
		}

		private void processAction(AbstractVocabAction actionToProcess) {
			ActionRunnable actionRunnable = new ActionRunnable() {

				private AbstractVocabAction action;
				public void setAction(AbstractVocabAction action) {
					this.action = action;
				}

				public void run() {
					action.putValue("locale", getLocale()); // have to update on the event dispatch thread
				}
			};

			actionRunnable.setAction(actionToProcess);
			SwingUtilities.invokeLater(actionRunnable);
		}
	}

	/**
	 * 
	 * @see http://java.sun.com/developer/JDCTechTips/2003/tt1208.html#1
	 */
	private static class FrameShower implements Runnable {
		final VocabFrame frame;
		public FrameShower(VocabFrame frame) {
			this.frame = frame;
		}
		public void run() {
			frame.show();
		}
	}

	// amazingly, I found a use for an inner interface ;-)
	private interface ActionRunnable extends Runnable {
		void setAction(AbstractVocabAction action);
	}
}
