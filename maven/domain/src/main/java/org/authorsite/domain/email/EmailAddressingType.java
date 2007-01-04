package org.authorsite.domain.email;

/**
 * Defines how {@link EmailAddressing} may be used.
 * 
 * @author jejking
 * 
 */
public enum EmailAddressingType {

    /**
     * To
     */
    TO("To"),
    /**
     * From
     */
    FROM("From"),
    /**
     * CC: Carbon Copy
     */
    CC("cc"),
    /**
     * BCC: Blind Carbon Copy
     */
    BCC("bcc"),
    /**
     * Reply-To
     */
    REPLY_TO("Reply-To"),
    /**
     * SENDER
     */
    SENDER("Sender");

    private String typeName;

    EmailAddressingType(String typeName) {
	this.typeName = typeName;
    }

    /**
     * @return name of type
     */
    public String getTypeName() {
	return typeName;
    }

    @Override
    public String toString() {
	return this.typeName;
    }

}
