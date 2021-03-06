/**
 * This file is part of the authorsite application.
 *
 * The authorsite application is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * The authorsite application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the authorsite application; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 */
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
