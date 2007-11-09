package org.authorsite.utils.bib.loader.ris;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.authorsite.domain.bib.AbstractWork;


public class FileParserDriver {

    public List<AbstractWork> parseFile(String filename) throws IOException, RISException {
	java.io.File f = new File(filename);
        if ( f.exists() ) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF8"));
            Parser p = new Parser();
            return p.parse(reader);
        }
        else {
            throw new IOException( "no such file" );
        }
    }
    
    
    
}
