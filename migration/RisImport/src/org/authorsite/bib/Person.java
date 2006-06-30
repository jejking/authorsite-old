package org.authorsite.bib;


public class Person extends AbstractHuman {

    private String givenNames;
    private String nameQualification;
    
    public Person(long id) {
        super(id);
    }

    
    public String getGivenNames() {
        return givenNames;
    }

    public void setGivenNames(String givenNames) {
        this.givenNames = givenNames;
    }

    public String getNameQualification() {
        return nameQualification;
    }

    public void setNameQualification(String nameQualification) {
        this.nameQualification = nameQualification;
    }

    @Override
    public String toSql() {
        
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO HUMANS ");
        sb.append("(id, created_at, updated_at, type, name, givenNames, nameQualification ) ");
        sb.append("VALUES ");
        sb.append("( ");
        sb.append(this.getId());
        sb.append(", NOW(), NOW(), 'Individual', ");
        if ( this.getName() == null ) {
            sb.append("'Unknown', ");
        }
        else {
            sb.append("'" + super.escapeSingleApostrophes(this.getName()) + "', ");
        }
        if ( this.givenNames == null) {
            sb.append("NULL, ");
        }
        else {
            sb.append( "'" + super.escapeSingleApostrophes(this.givenNames) + "', ");
        }
        if ( this.nameQualification == null ) {
            sb.append("NULL");
        }
        else {
            sb.append("'" + super.escapeSingleApostrophes(nameQualification) + "'");
        }
        sb.append(");");
        return sb.toString();
    }

}
