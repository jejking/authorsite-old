/*
 *  Log4JInitialiser.java, created on 23-Sep-2003 at 21:32:25
 *
 *  Copyright Alan Tibbetts, 2003.
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
 
package org.authorsite.vocab.util;

/**
 *  Collections of methods used to initialise the Log4J logging system.
 * 
 *  @author  Alan Tibbetts
 *  @version $Revision: 1.1 $
 */
import org.apache.log4j.xml.DOMConfigurator;

import org.authorsite.vocab.exceptions.ChainedException;

/**
 *  Uses the DOMConfigurator to initialise Log4J.
 */
public class Log4JInitialiser {

    /**
     *  Uses the supplied XML file to initialise Log4J
     */
    public static void init (String parametersFile) throws ChainedException {

        if (parametersFile == null || parametersFile.equals("")) {
            throw new ChainedException("No parameters file supplied to Log4JInitialiser");
        }

        try {
            DOMConfigurator.configure(parametersFile);
        } catch (Exception e) {
            throw new ChainedException("Unable to intialise Log4J", e);
        }
    }
}