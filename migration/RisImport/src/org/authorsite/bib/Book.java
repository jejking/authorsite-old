package org.authorsite.bib;

import java.util.HashSet;
import java.util.Set;

public class Book extends AbstractWork {

    private Set<AbstractHuman> authors;
    private Set<AbstractHuman> editors;
    private AbstractHuman publisher;
    private String volume;
    
    public Book(long id) {
        super(id);
        authors = new HashSet<AbstractHuman>();
        editors = new HashSet<AbstractHuman>();
    }
    
    public void addAuthor(AbstractHuman author) {
        this.authors.add(author);
    }
    
    public void addEditor(AbstractHuman editor) {
        this.editors.add(editor);
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
        sb.append("INSERT INTO works (id, created_at, updated_at, type, title, year, volume ) ");
        sb.append("VALUES ( ");
        sb.append(this.getId());
        sb.append(", NOW(), NOW(), 'Book', ");
        sb.append("'" + super.escapeSingleApostrophes(this.getTitle()) + "', ");
        sb.append(this.getYear());
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

}
