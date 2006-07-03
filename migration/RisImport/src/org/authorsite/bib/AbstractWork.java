package org.authorsite.bib;


public abstract class AbstractWork extends AbstractBibEntry {

    private String title;
    private int year;
    
    public AbstractWork(long id) {
        super(id);
    }

    
    public String getTitle() {
        return title;
    }

    
    public void setTitle(String title) {
        this.title = title;
    }

    
    public int getYear() {
        return year;
    }

    
    public void setYear(int year) {
        this.year = year;
    }
    
    

}
