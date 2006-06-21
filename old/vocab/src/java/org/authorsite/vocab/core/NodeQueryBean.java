/*
 * NodeQueryBean.java, created on 09-Sep-2003 at 21:00:18
 * 
 * Copyright John King, 2003.
 *
 *  NodeQueryBean.java is part of authorsite.org's VocabManager program.
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
import java.io.*;
import java.util.*;
/**
 * Encapsulates a simple query to a <code>VocabSet</code>. 
 * 
 * <p>This class wraps up a standardised abstraction of a query to a <code>VocabSet</code> instance. The query
 * is extremely basic at present and can be considered to represent node attributes conditions which must match. The node
 * attributes which can be queried using this approach are:</p>
 * 
 * <ul>
 * <li>Locale: the language of the node</li>
 * <li>beforeDate: the node must have been created before this date</li>
 * <li>afterDate: the node must have been created after this date. Note. If both beforeDate and afterDate have been
 * set then the intepreting instance should of course return nodes created <em>between</em> these dates.</li>
 * <li>text: the exact text of the text attribute of the <code>VocabNode</code></li>
 * <li>likeQuery: a boolean flag which suggests to the interpreting instance to use a pattern matching strategy in finding nodes</li>  
 * </ul>
 * 
 * <p>Interpreting instances should interrogate the <code>NodeQueryBean</code> to discover which properties on it have been set
 * and construct an appropriate translation to locate those nodes which match all the properties which are non-null.</p>
 * 
 * <p>More sophisticated querying mechanisms will be specified in the future.</p>
 * 
 * @author jejking
 * @version $Revision: 1.2 $
 * 
 */
public class NodeQueryBean implements  Serializable {

	private Locale locale;
	private Date beforeDate;
	private Date afterDate;
	private String nodeType;
	private String text;
	private boolean likeQuery;
	
	/**
	 * Constructs an empty <code>NodeQueryBean</code>.
	 * 
	 * <p>A no-arg constructor in the JavaBean paradigm.</p>
	 */
	public NodeQueryBean() {
	}
	
   	/**
   	 * Gets the date after which nodes should have been created, if set.
   	 * 
   	 *  @return date if set, else null.
	 */
	public Date getAfterDate() {
		return afterDate;
	}

	/**
	 * Gets the date before which nodes should have been created, if set.
	 *  
	 * @return date if set, else null.
	 */
	public Date getBeforeDate() {
		return beforeDate;
	}

	/**
	 * States whether the interpreting instance should attempt a pattern matching approach to the text.
	 *  
	 * @return true if set, else false.
	 */
	public boolean isLikeFlagSet() {
		return likeQuery;
	}

	/**
	 * Gets the locale which the nodes should match, if set.
	 * 
	 * @return locale if set, else null.
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Gets the node type which the nodes should match, if set.
	 * 
	 * @return node type string to search for, if set, else null.
	 */
	public String getNodeType() {
		return nodeType;
	}

	/**
	 * Gets the text string which the nodes must match, if set.
	 * 
	 * @return text string if set, else null.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets Date after which the nodes should have been created to match query.
	 * 
	 * @param date
	 */
	public void setAfterDate(Date date) {
		afterDate = date;
	}

	/**
	 * Sets Date before which the nodes should have been created to match query.
	 * 
	 * @param date
	 */
	public void setBeforeDate(Date date) {
		beforeDate = date;
	}

	/**
	 * Specifies whether an interpreting instance should attempt a pattern matching strategy on the text string.
	 * 
	 * @param b
	 */
	public void setLikeFlag(boolean b) {
		likeQuery = b;
	}

	/**
	 * Sets Locale which nodes should match.
	 * 
	 * @param locale
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * Sets node type which nodes should match.
	 * @param string
	 */
	public void setNodeType(String string) {
		nodeType = string;
	}

	/**
	 * Sets the text string the nodes must match.
	 * @param string
	 */
	public void setText(String string) {
		text = string;
	}

}
