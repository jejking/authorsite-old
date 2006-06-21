package org.juenger.wiki;

/**
 * Standard exception for wiki,
 * 
 * @author jking
 *
 */
public class WikiException extends Exception {

	/**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
	 * @param message
	 */
	public WikiException(String message) {
		super(message);
	}
    
    /**
     * @param throwable
     */
    public WikiException(Throwable throwable) {
        super(throwable);
    }
    
    /**
     * @param message
     * @param throwable
     */
    public WikiException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
