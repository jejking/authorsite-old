/*
 * VocabManagerGUI.java, created on 24-Nov-2003 at 23:26:47
 * 
 * Copyright John King, 2003.
 *
 *  VocabManagerGUI.java is part of authorsite.org's VocabManager program.
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

import java.io.*;
import java.util.*;
import org.authorsite.vocab.core.*;
import org.authorsite.vocab.gui.swing.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.2 $
 */
public class VocabGUIRunner {

	public static void main(String[] args) {
		// load preference properties, else use some hard coded defaults
		Properties vocabProps = new Properties();
		try {
			vocabProps.load(new FileInputStream(new File("conf/vocab.properties").getAbsolutePath()));
			VocabSetFactory factory  = GenericVocabSetFactory.getInstance();
			factory.setProperties(vocabProps);
			VocabSet set = factory.getVocabSet();
			VocabMediator mediator = new VocabMediator(set);
			// if we get a JFace GUI from Mr T, we'll look in the props and instantiate dynamically
			Locale guiLocale = new Locale(vocabProps.getProperty("guiLocale"));
			VocabFrame  frame = new VocabFrame(mediator, guiLocale, vocabProps);
			frame.runGUI();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
