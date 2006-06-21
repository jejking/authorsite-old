/*
 *  ChainedException.java, created on 23-Sep-2003 at 21:45:31
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

package org.authorsite.vocab.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *  <P>
 *  Implementation of Exception that maintains the stacktrace of
 *  multiple, chained exceptions.
 *  </P>
 *  @author Alan Tibbetts
 *  @version $Revision: 1.1 $
 */
public class ChainedException extends Exception {

    private Throwable cause = null;

    public ChainedException() {
        super();
    }

    public ChainedException(String message) {
        super(message);
    }

    public ChainedException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }

    /**
     * Returns the wrapped Exception.  NB. This may be null.
     */
    public Throwable getCause() {
        return cause;
    }
    
    /**
     * Indicates whether or not this object contains a wrapped exception.
     */
    public boolean hasWrappedException() {
        return (cause != null);
    }

    /**
     * Prints the stack trace of both the current and, if appropriate, the wrapped
     * exception, to System.err.
     */
    public void printStackTrace() {
        super.printStackTrace();
        if (cause != null) {
            System.err.println("Caused by:");
            cause.printStackTrace();
        }
    }

    /**
     * Prints the stack trace of both the current and, if appropriate, the wrapped
     * exception, to the supplied printstream.
     */
    public void printStackTrace(PrintStream ps) {
        super.printStackTrace(ps);
        if (cause != null) {
            ps.println("Caused by:");
            cause.printStackTrace(ps);
        }
    }

    /**
     * Prints the stack trace of both the current and, if appropriate, the wrapped
     * exception, to the supplied PrintWriter.
     */
    public void printStackTrace(PrintWriter pw) {
        super.printStackTrace(pw);
        if (cause != null) {
            pw.println("Caused by:");
            cause.printStackTrace(pw);
        }
    }
    
    /**
     * Converts the stack trace of an exception to a String.
     * 
     * @param t
     * @return
     */
    public static String getStackTrace(Throwable t) {
        ChainedException ce = new ChainedException(t.getMessage(), t);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ce.printStackTrace(pw);
        return sw.toString();
    }
}
