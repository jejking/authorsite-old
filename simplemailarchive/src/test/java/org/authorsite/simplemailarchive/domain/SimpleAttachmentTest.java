/**
 * This file is part of the authorsite application.
 *
 * The authorsite application is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * The authorsite application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the authorsite application; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */
package org.authorsite.simplemailarchive.domain;

import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Test;

public class SimpleAttachmentTest {

	@Test
	public void testRegularConstruction() throws UnsupportedEncodingException {
		byte[] theContents = "hello world".getBytes("UTF-8");
		SimpleAttachment.AttachmentBuilder builder = new SimpleAttachment.AttachmentBuilder(theContents, "helloWorld.txt", "text/plain");
		builder.description("the typical example");
		builder.disposition("ATTACHMENT");
		SimpleAttachment attachment = builder.build();
		
		Assert.assertArrayEquals(theContents, attachment.getContents());
		Assert.assertNotSame(theContents, attachment.getContents()); // we should get a copy back
		Assert.assertEquals(theContents.length, attachment.getSize());
		Assert.assertEquals("text/plain", attachment.getMimeType());
		Assert.assertEquals("helloWorld.txt", attachment.getFileName());
		Assert.assertEquals("the typical example", attachment.getDescription());
		Assert.assertEquals("ATTACHMENT", attachment.getDisposition());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRejectNullContents() {
		@SuppressWarnings("unused")
		SimpleAttachment.AttachmentBuilder builder = new SimpleAttachment.AttachmentBuilder(null, "helloWorld.txt", "text/plain");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRejectContentsLengthZero() {
		byte[] theContents = new byte[0];
		@SuppressWarnings("unused")
		SimpleAttachment.AttachmentBuilder builder = new SimpleAttachment.AttachmentBuilder(theContents, "helloWorld.txt", "text/plain");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRejectNullFileName() throws UnsupportedEncodingException {
		byte[] theContents = "hello world".getBytes("UTF-8");
		@SuppressWarnings("unused")
		SimpleAttachment.AttachmentBuilder builder = new SimpleAttachment.AttachmentBuilder(theContents, null, "text/plain");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRejectEmptyFileName() throws UnsupportedEncodingException {
		byte[] theContents = "hello world".getBytes("UTF-8");
		@SuppressWarnings("unused")
		SimpleAttachment.AttachmentBuilder builder = new SimpleAttachment.AttachmentBuilder(theContents, null, "text/plain");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRejectNullMimeType() throws UnsupportedEncodingException {
		byte[] theContents = "hello world".getBytes("UTF-8");
		@SuppressWarnings("unused")
		SimpleAttachment.AttachmentBuilder builder = new SimpleAttachment.AttachmentBuilder(theContents, "helloWorld.txt", null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRejectUnparsableMimeType() throws UnsupportedEncodingException {
		byte[] theContents = "hello world".getBytes("UTF-8");
		@SuppressWarnings("unused")
		SimpleAttachment.AttachmentBuilder builder = new SimpleAttachment.AttachmentBuilder(theContents, "helloWorld.txt", "foo bar wibble");
	}
	
	
}
