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

package org.authorsite.domain.content;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.authorsite.domain.AbstractEntry;

/**
 *
 * @author jejking
 */
@Entity()
@Table(name = "content_node")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractNode extends AbstractEntry {

    private ContainerNode parent;
    private String name;
    private String path;

    public AbstractNode() {
        super();
    }

    public AbstractNode(ContainerNode parent, String name, String path) {
        this.parent = parent;
        this.name = name;
        this.path = path;
    }

    @ManyToOne()
    @JoinColumn(name="parent_id", nullable=false)
    public ContainerNode getParent() {
        return parent;
    }

    public void setParent(ContainerNode parent) {
        this.parent = parent;
    }

    @Column(nullable=false)
    public String getName() {
        return name;
    }

    @Column(nullable=false)
    public void setName(String name) {
        this.name = name;
    }

    @Column(length=500, nullable=false)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    
}
