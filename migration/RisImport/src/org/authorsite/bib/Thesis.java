package org.authorsite.bib;


public class Thesis extends AbstractWork {

    private Individual author;
    private Collective awardingBody;
    private String degree;
    
    public Thesis(long id) {
        super(id);
    }
    
    public Individual getAuthor() {
        return author;
    }

    public void setAuthor(Individual author) {
        this.author = author;
    }

    public Collective getAwardingBody() {
        return awardingBody;
    }

    public void setAwardingBody(Collective awardingBody) {
        this.awardingBody = awardingBody;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    @Override
    public String toSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO works (id, created_at, updated_at, type, title, year, degree ) ");
        sb.append("VALUES ( ");
        sb.append(this.getId());
        sb.append(", NOW(), NOW(), 'Thesis', ");
        sb.append("'" + super.escapeSingleApostrophes(this.getTitle()) + "', ");
        sb.append(this.getYear());
        sb.append(", '");
        sb.append( degree == null ? "Unknown" :  super.escapeSingleApostrophes(this.degree) );
        sb.append("');");
        
        if ( this.author != null ) {
            sb.append("\n");
            sb.append(super.createSqlForSingleHumanWorkRelationship(this.author, "Author"));
        }
        if ( this.awardingBody != null ) {
            sb.append("\n");
            sb.append(super.createSqlForSingleHumanWorkRelationship(this.awardingBody, "AwardingBody"));
        }
            
        return sb.toString();
    }

}
