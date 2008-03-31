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
public class TextContentNode extends AbstractContentNode {

    private String textContent;

    public TextContentNode() {
        super();
    }

    @Basic(fetch= FetchType.EAGER)
    @Lob
    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
    
    
    
}
