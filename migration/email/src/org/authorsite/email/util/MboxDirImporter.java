package org.authorsite.email.util;

import java.io.File;

public class MboxDirImporter {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        MboxFileImporter importer = new MboxFileImporter();
        File dir = new File(args[0]);
        File[] files = dir.listFiles();
        for ( File file : files ) {
            try {
                importer.processFile(file.getAbsolutePath(), args[1], args[2], args[3]);
            }
            catch (Exception e) {
                System.err.println("error reading " + file.getAbsolutePath());
                e.printStackTrace();
            }
        }

    }

}
