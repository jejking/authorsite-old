package org.authorsite.email.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class EmlFailToMboxUtil {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		File dir = new File(args[0]);
		File targetMboxFile = new File(args[1]);
		if (targetMboxFile.exists()) {
			targetMboxFile.delete();
		}
		
		BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(targetMboxFile));
		
		writer.write("From a seperator\n".getBytes());
		File[] files = dir.listFiles();
		for (File file : files) {
			if ( file.getName().endsWith(".eml")) {
				// open file
				BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
				
				// write it to the mbox
				//	Transfer bytes from in to out
		        byte[] buf = new byte[1024];
		        int len;
		        while ((len = reader.read(buf)) > 0) {
		            writer.write(buf, 0, len);
		        }
		        writer.write("\n\nFrom a seperator\n".getBytes());
				System.out.println("read " + file );
				// close it
				reader.close();
			}
		}
		
		writer.close();
	}

}
