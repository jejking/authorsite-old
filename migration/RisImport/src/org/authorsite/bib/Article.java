package org.authorsite.bib;

import java.util.HashSet;
import java.util.Set;

public class Article extends AbstractWork {

    private HashSet<AbstractHuman> authors;
    private Journal journal;
    private String volume;
    private String issue;
    private String pages;
    private int month;
    private int day;
    
    public Article(long id) {
        super(id);
        this.authors = new HashSet<AbstractHuman>();
    }

    
    public Set<AbstractHuman> getAuthors() {
        return authors;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
    
    public void addAuthor(AbstractHuman author) {
        this.authors.add(author);
    }

    @Override
    public String toSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO works (id, created_at, updated_at, type, title, year, month, day, pages, volume, number) ");
        sb.append("VALUES ( ");
        sb.append(this.getId());
        sb.append(", NOW(), NOW(), 'Article', ");
        sb.append("'" + this.getTitle() + "', ");
        sb.append(this.getYear());
        sb.append(", ");
        sb.append( month == 0 ? "NULL" : this.getMonth());
        sb.append(", ");
        sb.append( day == 0 ? "NULL" : this.getDay() );
        sb.append(", ");
        sb.append(pages == null ? "NULL" : super.escapeSingleApostrophes(this.getPages()) );
        sb.append(", ");
        sb.append(volume == null ? "NULL" : "'" + super.escapeSingleApostrophes(this.getVolume()) + "'");
        sb.append(", ");
        sb.append(volume == null ? "NULL" : "'" + super.escapeSingleApostrophes(this.getIssue()) + "'");
        sb.append(");");
        if ( this.journal != null ) {
            sb.append("\n");
            sb.append(super.createSqlForWorkWorkRelationship(this.journal, "Containment"));
        }
        if ( !this.authors.isEmpty() ) {
            sb.append("\n");
            sb.append(super.createSqlForMultipleHumanWorkRelationship(this.getAuthors(), "Author"));
        }
        return sb.toString();
    }

}
