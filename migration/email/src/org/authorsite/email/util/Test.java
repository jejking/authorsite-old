package org.authorsite.email.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;

public class Test {

	public static void main(String args[]) throws Exception {
		Session session = Session.getDefaultInstance(new Properties());

		Store store = session.getStore(new URLName("mstor:c:/tmp/ej-1998-03"));
		store.connect();

		// read messages from Inbox..
		Folder inbox = store.getDefaultFolder();
		inbox.open(Folder.READ_ONLY);

		Message[] messages = inbox.getMessages();
		
		for (Message m : messages ) {
			Address[] from = m.getFrom();
			System.out.print("From: ");
			for (Address a : from ) {
				InternetAddress ia = (InternetAddress) a;
				System.out.println(ia.getPersonal());
				System.out.println(ia.getAddress());
			}
			System.out.println("Date: " + m.getSentDate());
			System.out.println("Subject: " + m.getSubject());
			System.out.println(m.getContent());
			System.out.println("\n====================================\n");
		}
	}
	
}
