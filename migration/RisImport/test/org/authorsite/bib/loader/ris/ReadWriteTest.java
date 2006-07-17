package org.authorsite.bib.loader.ris;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import junit.framework.TestCase;


public class ReadWriteTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        File f = new java.io.File("biblio.sql");
        if ( f.exists() ) {
            f.delete();
        }
    }

    public void testReadWrite() throws Exception {
        Parser p = new Parser();
        p.readFile("biblio.txt"); 
        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("biblio.sql"), "UTF8"));
            
        Bibliography.getInstance().writeBibliographyToSql(writer, true);
        writer.close();
    }
    
}
