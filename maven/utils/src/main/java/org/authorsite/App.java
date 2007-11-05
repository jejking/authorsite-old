package org.authorsite;

import java.io.IOException;
import org.authorsite.utils.bib.loader.ris.Parser;
import org.authorsite.utils.bib.loader.ris.RISException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, RISException
    {
        Parser parser = new Parser();
        parser.readFile(args[0]);
    }
}
