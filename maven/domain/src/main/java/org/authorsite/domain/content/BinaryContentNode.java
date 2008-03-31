/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.authorsite.domain.content;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;

/**
 *
 * @author jejking
 */
@Entity
public class BinaryContentNode extends AbstractContentNode {

    private byte[] binaryContent;

    public BinaryContentNode() {
        super();
    }

    @Basic(fetch= FetchType.LAZY)
    @Lob
    public byte[] getBinaryContent() {
        return binaryContent;
    }

    public void setBinaryContent(byte[] binaryContent) {
        this.binaryContent = binaryContent;
    }
    
    
    
}
