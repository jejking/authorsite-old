package org.authorsite.email.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MailManArchiveFixer {

	/*
	 * Read in mbox file as file, line for line.
	 * 
	 * when we encounter a From separator.
	 * on next line
	 * - insert a To: <juenger-list@juenger.org> 
	 * - insert a Content-Type: text/plain; encoding=ISO-8859-1
	 * 
	 * (maybe also 
	 * - Content-Transfer-Encoding: quoted-printable  
	 * ) 
	 */
	
	public static void main(String[] args) throws Exception {
		
		boolean quotedPrintable = new Boolean(args[2]).booleanValue();
		
		// open file in args[0] for reading text, iso-8859-1
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "ISO-8859-1"));
		
		// open file in args[1] for writing, iso-8859-1
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "ISO-8859-1"));
		
		String str;
        while ((str = in.readLine()) != null) {
            process(str, out, quotedPrintable);
        }	
		
     	in.close();
		out.close();
	}

	private static void process(String str, BufferedWriter out, boolean quotedPrintable) throws Exception {
		System.out.println(str);
		out.write(str);
		out.write("\n");
		if ( str.startsWith("From") && !(str.startsWith("From:"))) {
			out.write("To: <juenger-list@juenger.org>");
			out.write("\n");
			out.write("Content-Type: text/plain; charset=iso-8859-1");
			out.write("\n");
		}
		if (quotedPrintable) {
			out.write("Content-Transfer-Encoding: quoted-printable");
			out.write("\n");
		}
		/*
Content-type: multipart/mixed; boundary="frontier"
MIME-version: 1.0
			*/
	}
	
}
