/*
 * AbstractVocabNode.java, created on 08-Sep-2003 at 22:45:04
 * 
 * Copyright John King, 2003.
 *
 *  AbstractVocabNode.java is part of authorsite.org's VocabManager program.
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
package org.authorsite.vocab.core;

import java.util.*;
import java.text.*;
import org.authorsite.vocab.exceptions.*;

/**
 * Skeleton implementation of the <code>VocabNode</code> interface which provides many convenience methods.
 * 
 * 
 * @author jejking
 * @version $Revision: 1.5 $
 */
public abstract class AbstractVocabNode implements VocabNode {

	private Integer id;
	private String text;
	private Locale locale;
	private Date dateCreated;
	private String annotation;
	private String nodeType;
	private String register;

	// used to help implement the Comparable interface
	private Collator collator;

	/**
	 * Creates new instance of <code>AbstractVocabNode</code>.
	 * 
	 * <p>Constructor sets the dateCreated attribute to the current time.</p> 
	 * 
	 * @param id Integer representing id, must be determined by the <code>VocabSet</code> responsible for creating this node. 
	 * @param text the text of the node
	 * @param nodeType the type of the node. 
	 * @param locale the locale of the node.
	 * @throws VocabException
	 */
	protected AbstractVocabNode(Integer id, String text, String nodeType, Locale locale) throws VocabException {
		setUp(id, locale, text, nodeType);
		dateCreated = new Date();
	}

	/**
	 * Creates a new <code>AbstractVocabNode</code> instance with the defined attributes.
	 * 
	 * @param id
	 * @param text
	 * @param nodeType
	 * @param locale
	 * @param date the date the object was created. This will be as recorded in a persistent store out of which the implementation creates instances.
	 * @throws VocabException
	 */
	protected AbstractVocabNode(Integer id, String text, String nodeType, Locale locale, Date date) throws VocabException {
		setUp(id, locale, text, nodeType);
		if (date != null) {
			setDate(date);
		}
		else {
			dateCreated = new Date(); // don't set it to null, set it to a reasonable default 
		}
	}

	// internal utility method that sanity checks the most important elements passed in the constructor
	private void setUp(Integer id, Locale locale, String text, String nodeType) throws VocabException {

		//		check id is not null
		if (id != null) {
			this.id = id;
		}
		else {
			throw new VocabException(VocabException.MISSING_NODE_ID);
		}

		//	check text is not null or of zero length
		if (text != null && text.length() != 0) {
			this.text = text;
		}
		else {
			throw new VocabException(VocabException.MISSING_NODE_TEXT);
		}
		// check node type is not null or of zero length
		if (nodeType != null && nodeType.length() != 0) {
			this.nodeType = nodeType;
		}
		else {
			throw new VocabException(VocabException.MISSING_NODE_TYPE);
		}

		// check locale is not null
		if (locale != null) {
			String language = locale.getLanguage();
			if (language.length() == 0) { // we must check that the Locale defines a language and not just a place
				throw new VocabException(VocabException.MISSING_LOCALE_LANGUAGE);
			}
			this.locale = locale;
		}
		else {
			throw new VocabException(VocabException.MISSING_NODE_LOCALE);
		}

		collator = Collator.getInstance(Locale.getDefault());
		collator.setDecomposition(Collator.CANONICAL_DECOMPOSITION); // so accented characters are collated correctly
	}

	private void setDate(Date date) throws VocabException {
		// quick sanity check it's not in the future 
		if (date.after(new Date())) {
			throw new VocabException(VocabException.INVALID_DATE_OF_CREATION);
		}
		else {
			dateCreated = date;
		}
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#getAnnotation()
	 */
	public String getAnnotation() {
		return annotation;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#getDateCreated()
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#getId()
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#getLocale()
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#getNodeType()
	 */
	public String getNodeType() {
		return nodeType;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#getRegister()
	 */
	public String getRegister() {
		return this.register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#getText()
	 */
	public String getText() {
		return text;
	}

	protected void setText(String newText) {
		text = newText;
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		VocabNode node = (VocabNode) o;

		// sort on Locale. we use the default locale's display name and use that to sort
		String myLocaleLanguageName = locale.getDisplayLanguage();
		String objLocaleLanguageName = node.getLocale().getDisplayLanguage();

		int localeSignum = collator.compare(myLocaleLanguageName, objLocaleLanguageName);
		if (localeSignum != 0) {
			return localeSignum;
		}

		// hm, we are in the same locale, test on text
		int textSignum = collator.compare(text, node.getText());
		if (textSignum != 0) {
			return textSignum;
		}

		//		hm, text *and* locale are identical, test on nodeType
		return collator.compare(nodeType, node.getNodeType());

	}

	/**
	 * Evaluates the node for equality with object passed in.
	 * 
	 * <p>In order for the <code>VocabSet</code> to maintain the uniqueness constraint
	 * on all nodes it manages, the equals and hashCode methods inherited from java.lang.Object
	 * must be overridden.</p>
	 * 
	 * <p>Equality between node objects is defined to be a unique combination of:</p>
	 * <ul>
	 * <li>text</li>
	 * <li>Locale</li>
	 * <li>nodeType</li>
	 * </ul>
	 * 
	 * <p>This combination of attributes allows us to have a more sophisticated notion of equality between
	 * nodes than just comparing text or the reference of an object. The id of an object is essentially
	 * immaterial and is used solely to facilitate efficient lookups in caches or persistent stores. By using this 
	 * combination we can differentiate between, say the English noun "run" (to go for a run) and the English
	 * verb "run" (to run somewhere) by including nodeType in the equality calculation. We can also
	 * differentiate between the French noun "programme" and the English noun "programme".</p>
	 * 
	 * <p>It is for this reason that a node's Locale and nodeType are immutable whilst any changes
	 * to its text is subject to scrutiny to ensure uniqueness is maintained.</p>
	 * 
	 * @param an Object to compare with
	 * @return true if Object has same key values as this node, false otherwise
	 */
	public boolean equals(Object obj) {

		if (obj == this) {
			// hm, comparing with ourselves cos object references are identical
			return true;
		}

		if (obj instanceof VocabNode) {
			// ok, instance of the right class, so downcast it for checking
			VocabNode nodeToCheck = (VocabNode) obj;

			// check text, language and node type for equality
			if (nodeToCheck.getText().equals(this.text) && nodeToCheck.getLocale().equals(this.locale) && nodeToCheck.getNodeType().equals(this.nodeType)) {
				return true;
			}
			else {
				return false;
			}
		}
		else { // instance of another classs
			return false;
		}
	}

	/**
	 * Returns a hashCode for the node.
	 * 
	 * <p>Because we have overridden the equals method, it is also necessary to override
	 * the hashCode method, particularly because we are making use of <code>HashMap</code>
	 * and <code>HashSet</code> in the implementation of <code>VocabularySet</code>. The implementation
	 * used here is essentially derived from Bloch's <em>Effective Java</java>.
	 * 
	 * @return hashCode for the vocab node
	 * @see java.lang.Object
	 */
	public int hashCode() {
		int result = 23;
		result = 37 * result + text.hashCode();
		result = 37 * result + locale.hashCode();
		result = 37 * result + nodeType.hashCode();
		return result;
	}

	/**
	 * Returns a simple string representation of the node.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return ("Node id: " + id + "\nType: " + nodeType + "\nLocale: " + locale.getDisplayLanguage(Locale.ENGLISH) + "\nText: " + text + "\nCreated: " + dateCreated);
	}

}
