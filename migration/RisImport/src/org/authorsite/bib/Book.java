package org.authorsite.bib;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Book extends AbstractWork implements Comparable {

    private SortedSet<AbstractHuman> authors = new TreeSet<AbstractHuman>();
    private SortedSet<AbstractHuman> editors = new TreeSet<AbstractHuman>();
    private AbstractHuman publisher;
    private String volume;
    
    public Book(long id) {
        super(id);
    }
    
    public Book() {
        super();
    }

    public void addAuthor(AbstractHuman author) {
        this.authors.add(author);
    }
    
    public void addAuthors(Collection<? extends AbstractHuman> authors) {
        this.authors.addAll(authors);
    }

    
    public void addEditor(AbstractHuman editor) {
        this.editors.add(editor);
    }
    
    public void addEditors(Collection<? extends AbstractHuman> editors) {
        this.editors.addAll(editors);
    }
    
    
    public AbstractHuman getPublisher() {
        return publisher;
    }

    public void setPublisher(AbstractHuman publisher) {
        this.publisher = publisher;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public Set<AbstractHuman> getAuthors() {
        return authors;
    }

    public Set<AbstractHuman> getEditors() {
        return editors;
    }

    @Override
    public String toSql() {
        
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO works (id, created_at, updated_at, type, title, year, toYear, volume ) ");
        sb.append("VALUES ( ");
        sb.append(this.getId());
        sb.append(", NOW(), NOW(), 'Book', ");
        sb.append("'" + super.escapeSingleApostrophes(this.getTitle()) + "', ");
        sb.append(this.getYears().getYear());
        sb.append(", ");
        sb.append( this.getYears().getToYear() == Integer.MIN_VALUE ? "NULL" : this.getYears().getToYear() );
        sb.append(", ");
        sb.append( volume == null ? "NULL" : super.escapeSingleApostrophes(this.getVolume()) );
        sb.append(");");
        
        if ( !this.authors.isEmpty() ) {
            sb.append("\n");
            sb.append(super.createSqlForMultipleHumanWorkRelationship(authors, "Author"));
        }
        if ( !this.editors.isEmpty()) {
            sb.append("\n");
            sb.append(super.createSqlForMultipleHumanWorkRelationship(editors, "Editor"));
        }
        if ( this.publisher != null ) {
            sb.append("\n");
            sb.append(super.createSqlForSingleHumanWorkRelationship(publisher, "Publisher"));
        }
            
        return sb.toString();
    }

    
    
    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }
        if ( obj == this ) {
            return true;
        }
        if ( obj instanceof Book ) {
            Book rhs = (Book) obj;
            if ( this.authors.size() == rhs.authors.size() && this.editors.size() == rhs.editors.size() ) {
                return new EqualsBuilder().append(this.getTitle(), rhs.getTitle())
                                          .append(this.getYears(), rhs.getYears())
                                          .append(this.authors.toArray(), rhs.authors.toArray())
                                          .append(this.editors.toArray(), rhs.editors.toArray())
                                          .append(this.publisher, rhs.publisher)
                                          .isEquals();
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getTitle())
                                    .append(this.getYears())
                                    .append(this.authors.toArray())
                                    .append(this.editors.toArray())
                                    .append(this.publisher)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (AbstractHuman author: this.authors) {
            sb.append(author);
            sb.append(" ");
        }
        sb.append(":");
        sb.append(this.getTitle());
        sb.append(" (");
        sb.append(this.getYears());
        sb.append(") ");
        sb.append("Eds: ");
        for ( AbstractHuman editor : this.editors ) {
            sb.append(editor);
            sb.append(" ");
        }
        sb.append(this.publisher);
        return sb.toString();
    }

    public int compareTo(Object o) {
        Book rhs = (Book) o;
        return new CompareToBuilder().append(this.getTitle(), rhs.getTitle())
                                     .append(this.getYears(), rhs.getYears())
                                     .append(this.authors.toArray(), rhs.authors.toArray())
                                     .append(this.editors.toArray(), rhs.editors.toArray())
                                     .append(this.publisher, rhs.publisher)
                                     .toComparison();
                                     
         
    }

    
    

}
