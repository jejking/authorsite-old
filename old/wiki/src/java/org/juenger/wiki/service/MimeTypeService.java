package org.juenger.wiki.service;

/**
 * Defines simple service to resolve mime types from file
 * names.
 * 
 * @author jejking
 */
public interface MimeTypeService {

	/**
	 * Returns the matching mime-type description. If none can be
	 * found, returns the generic <code>application/octet-stream</code>.
	 * 
	 * @param fileName the name of the "file" to get the mime type of
	 * @return the mime type
	 */
	public String getMimeType(String fileName);
	
}
