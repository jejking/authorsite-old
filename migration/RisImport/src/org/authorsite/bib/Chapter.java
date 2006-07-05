package org.authorsite.bib;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


public class Chapter extends AbstractWork implements Comparable {

    private Book book;
    private SortedSet<AbstractHuman> authors = new TreeSet<AbstractHuman>();
    private SortedSet<AbstractHuman> editors = new TreeSet<AbstractHuman>();
    private String pages;
    private String chapter;
    
    public Chapter(long id) {
        super(id);
        
     }

    public Chapter() {
        super();
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public Set<AbstractHuman> getAuthors() {
        return authors;
    }

    public Set<AbstractHuman> getEditors() {
        return editors;
    }
    
    public void addAuthor(AbstractHuman author) {
        authors.add(author);
    }
    
    public void addEditor(AbstractHuman editor) {
        editors.add(editor);
    }

    @Override
    public String toSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO works (id, created_at, updated_at, type, title, year, pages, chapter ) ");
        sb.append("VALUES ( ");
        sb.append(this.getId());
        sb.append(", NOW(), NOW(), 'Chapter', ");
        sb.append("'" + super.escapeSingleApostrophes(this.getTitle()) + "', ");
        sb.append( this.getYear() == 0 && this.book != null ? this.book.getYear() : this.getYear() );  
        sb.append(", ");
        sb.append( pages == null ? "NULL" : "'" + super.escapeSingleApostrophes(this.getPages()) + "'");
        sb.append(", ");
        sb.append( chapter == null ? "NULL" : "'" + super.escapeSingleApostrophes(this.getChapter()) + "'");
        sb.append(");");
        
        if ( !this.authors.isEmpty() ) {
            sb.append("\n");
            sb.append(super.createSqlForMultipleHumanWorkRelationship(authors, "Author"));
        }
        if ( !this.editors.isEmpty()) {
            sb.append("\n");
            sb.append(super.createSqlForMultipleHumanWorkRelationship(editors, "Editor"));
        }
        if ( this.book != null ) {
            sb.append("\n");
            sb.append(super.createSqlForWorkWorkRelationship(book, "Containment"));
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
        if ( obj instanceof Chapter ) {
            Chapter rhs = (Chapter) obj;
            if ( this.authors.size() == rhs.authors.size() && this.editors.size() == rhs.editors.size() ) {
                return new EqualsBuilder().append(this.getTitle(), rhs.getTitle())
                                          .append(this.getYear(), rhs.getYear())
                                          .append(this.authors.toArray(), rhs.authors.toArray())
                                          .append(this.editors.toArray(), rhs.editors.toArray())
                                          .append(this.book, rhs.book)
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
                                     .append(this.getYear())
                                     .append(this.authors.toArray())
                                     .append(this.editors.toArray())
                                     .append(this.book)
                                     .toHashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for ( AbstractHuman author : authors ) {
            sb.append(author);
            sb.append(" ");
        }
        sb.append(this.getTitle());
        sb.append(" (");
        sb.append(this.getYear());
        sb.append(") ");
        sb.append("Eds: ");
        for (AbstractHuman editor : editors ) {
            sb.append(editor);
            sb.append(" ");
        }
        sb.append(" in book: ");
        sb.append(book.toString());
        return sb.toString();
    }

    public int compareTo(Object o) {
        Chapter rhs = (Chapter) o;
        return new CompareToBuilder().append(this.getTitle(), rhs.getTitle())
                                     .append(this.getYear(), rhs.getYear())
                                     .append(this.authors.toArray(), rhs.authors.toArray())
                                     .append(this.editors.toArray(), rhs.editors.toArray())
                                     .append(this.book, rhs.book)
                                     .toComparison();
    }
    
    

}
