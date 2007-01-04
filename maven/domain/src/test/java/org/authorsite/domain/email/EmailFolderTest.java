package org.authorsite.domain.email;

import junit.framework.TestCase;

public class EmailFolderTest extends TestCase {

	public void testGetPath() {
		EmailFolder a = new EmailFolder("a");
		a.setParent(EmailFolder.ROOT);
		EmailFolder b = new EmailFolder("b");
		b.setParent(a);
		
		assertEquals("/a/b", b.getPath());
	}
	
}
