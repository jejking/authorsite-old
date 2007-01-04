package org.authorsite.domain.email;

import org.authorsite.domain.AbstractEntry;

/**
 * Marker class for use in {@link MessagePartContainer} to designate
 * the fact that {@link AbstractMessagePart} and {@link MessagePartContainer}
 * can be considered equivalent when assembling multipart messages. 
 * 
 * @author jejking
 *
 */
public abstract class AbstractEmailPart extends AbstractEntry {

    // deliberately empty
    
}
