package org.authorsite.email.db;

import org.authorsite.email.BinaryMessagePart;
import org.authorsite.email.EmailAddressing;
import org.authorsite.email.EmailAddressingContainer;
import org.authorsite.email.EmailFolder;
import org.authorsite.email.EmailMessage;
import org.authorsite.email.MessagePartContainer;
import org.authorsite.email.TextMessagePart;

public interface EmailVisitor {

	public void visit(EmailFolder folder);
	
	public void visit(EmailMessage message);
	
	public void visit(EmailAddressingContainer addressingContainer);
	
	public void visit(EmailAddressing addressing);
	
	public void visit(MessagePartContainer multipartContainer);
	
	public void visit(TextMessagePart textMessagePart);
	
	public void visit(BinaryMessagePart binaryMessagePart);
	
}
