package org.authorsite.bib;

import java.util.HashSet;
import java.util.Set;


public class Chapter extends AbstractWork {

    private Book book;
    private Set<AbstractHuman> authors;
    private Set<AbstractHuman> editors;
    private String pages;
    private String chapter;
    
    public Chapter(long id) {
        super(id);
        authors = new HashSet<AbstractHuman>();
        editors = new HashSet<AbstractHuman>();
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

}
