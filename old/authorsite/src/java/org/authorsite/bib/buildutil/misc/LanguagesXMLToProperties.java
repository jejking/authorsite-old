/*
 * LanguagesXMLToProperties.java
 *
 * Created on 12 February 2003, 22:10
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/buildutil/misc/LanguagesXMLToProperties.java,v 1.1 2003/03/01 13:38:13 jejking Exp $
 *
 * Copyright (C) 2003  John King
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

package org.authorsite.bib.buildutil.misc;
import java.io.*;
import java.util.*;
import org.xml.sax.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import org.saxpath.*;

/**
 * Utility class to generate a properties file to be used as a resource bundle
 * out of languages.xml. Should only need to be run once...
 * @author  jejking
 * @version $Revision: 1.1 $
 */
public class LanguagesXMLToProperties {
    
    private InputSource xmlIn;
    private File propsOut;
    private PrintWriter out;
    private Properties languageProps;
    
    /** Creates a new instance of LanguagesXMLToProperties */
    public LanguagesXMLToProperties(String langsPath, String propsPath) {
        xmlIn = new InputSource(langsPath);
        propsOut = new File(propsPath);
        languageProps = new Properties();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LanguagesXMLToProperties app = new LanguagesXMLToProperties(args[0], args[1]);
        app.execute();
    }
    
    private void execute() {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document languagesDoc = builder.build(xmlIn);
            Element root = languagesDoc.getRootElement();
            List languages = root.getChildren();
            Iterator languagesIt = languages.iterator();
            
            while (languagesIt.hasNext()) {
                Element language = (Element) languagesIt.next();
                String iso639 = language.getAttribute("iso639").getValue();
                String engName = language.getAttribute("engName").getValue();
                languageProps.put(iso639, engName);
            }
            
            // now, write the properties to file
            PrintWriter out = new PrintWriter(new FileOutputStream(propsOut));
            languageProps.list(out);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
