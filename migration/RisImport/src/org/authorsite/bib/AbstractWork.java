package org.authorsite.bib;

import java.util.Set;


public abstract class AbstractWork extends AbstractBibEntry {

    private String title;
    private WorkDates years = new WorkDates();
    
    public AbstractWork(long id) {
        super(id);
    }

    
    public AbstractWork() {
        super();
    }


    public String getTitle() {
        return title;
    }

    
    public void setTitle(String title) {
        this.title = title;
    }

    
    public WorkDates getYears() {
        return this.years;
    }

    
    public void setYears(WorkDates years) {
        this.years = years;
    }
    
    public String createSqlForMultipleHumanWorkRelationship(Set<AbstractHuman> humans, String rel) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for ( AbstractHuman human : humans ) {
            sb.append(createSqlForSingleHumanWorkRelationship(human, rel));
            i++;
            if ( i < humans.size() ) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }


    public String createSqlForSingleHumanWorkRelationship(AbstractHuman human, String rel) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO humanWorkRelationships ( created_at, updated_at, humans_id, works_id, relationship ) ");
        sb.append("VALUES ( NOW(), NOW(), ");
        sb.append(human.getId());
        sb.append(", ");
        sb.append(this.getId());
        sb.append(", ");
        sb.append("'" + rel + "');");
        return sb.toString();
    }
    
    public String createSqlForWorkWorkRelationship(AbstractWork toWork, String rel) {
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO workWorkRelationships ( created_at, updated_at, from_id, to_id, relationship ) "); 
        sb.append("VALUES ( NOW(), NOW(), ");
        sb.append(this.getId());
        sb.append(", ");
        sb.append(toWork.getId());
        sb.append(", ");
        sb.append("'" + rel + "');");
        return sb.toString();
    }

}
