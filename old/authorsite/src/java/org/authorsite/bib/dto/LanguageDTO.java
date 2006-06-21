/*
 * LanguageDTO.java
 *
 * Created on 20 November 2002, 09:28
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/dto/LanguageDTO.java,v 1.3 2003/03/01 13:37:36 jejking Exp $
 *
 * Copyright (C) 2002  John King
 *
 * This file is part of the authorsite.org bibliographic
 * application.
 *
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.authorsite.bib.dto;
import java.io.*;
/**
 * Data Transfer Object representing a language. As such, it will normally be used as part
 * of the <code>languages</code> attribute of <code>MediaItemDTO</code>.
 *
 * @author  jejking
 * @revision $Revision: 1.3 $
 */
public class LanguageDTO implements Serializable {
    
    private String iso639;
    
    /** Creates a new instance of LanguageDTO
     * @param iso639 The three letter ISO 639 code for the language.
     */
    public LanguageDTO(String iso639) {
        this.iso639 = iso639;
    }
    
    /** Returns three-letter ISO 639 code for the language.
     * @return Three-letter ISO 639 for the language.
     */    
    public String getIso639() {
        return iso639;
    }
    
}
