/*
 * VocabException.java, created on 23-Sep-2003 at 21:45:31
 * 
 * Copyright John King, 2003.
 *
 *  VocabException.java is part of authorsite.org's VocabManager program.
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
package org.authorsite.vocab.exceptions;

/**
 * Exception class encapsulating exceptional conditions for the VocabManager
 * model layer.
 * 
 * @author jejking
 * @version $Revision: 1.5 $
 * 
 */
public class VocabException extends ChainedException {

	public static final String INVALID_NODE = "invalidNode";
	public static final String DUPLICATE_NODE = "textProducesDuplicateNode";
	public static final String INVALID_NODE_TYPE = "invalidNodeType";
	public static final String INVALID_REGISTER_TYPE = "invalidRegisterType";
	public static final String INVALID_RELATIONSHIP_TYPE = "invalidRelationshipType";
	public static final String REL_POINTS_TO_SELF = "relationshipPointsToSourceNode";
	public static final String PERSISTENCE = "persistence";
	public static final String INITIALIZATION = "initialization";
	public static final String MISSING_NODE_TEXT="missingText";
	public static final String MISSING_NODE_LOCALE="missingLocale";
	public static final String MISSING_NODE_TYPE="missingNodeType";
	public static final String MISSING_NODE_ID="missingNodeId";
	public static final String MISSING_LOCALE_LANGUAGE="missingLocaleLanguage";
	public static final String INVALID_DATE_OF_CREATION="invalidDateOfCreation";
	public static final String MISSING_FROM_NODE="missingFromNode";
	public static final String MISSING_RELATIONSHIP_TYPE="missingRelationshipType";
	public static final String MISSING_TO_NODE="missingToNode";
		

	public VocabException() {
		super("GenericVocabException");
	}
	
	public VocabException(String message) {
		super(message);
	}
	
	public VocabException (String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
