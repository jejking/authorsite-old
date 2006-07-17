package org.authorsite.bib;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public class WorkDates implements Comparable {

    private int year = 0;
    private int toYear = Integer.MIN_VALUE;
    
    private int month = Integer.MIN_VALUE;
    private int day = Integer.MIN_VALUE;

    public WorkDates(int year) {
        this.year = year;
    }
    
    public WorkDates(int year, int toYear) {
        this.year = year;
        this.toYear = toYear;
    }
    
    
    
    
    public int getDay() {
        return day;
    }

    
    public void setDay(int day) {
        this.day = day;
    }

    
    public int getMonth() {
        return month;
    }

    
    public void setMonth(int month) {
        this.month = month;
    }

    public WorkDates() {
        super();
    }

    public int getToYear() {
        return toYear;
    }

    
    public void setToYear(int toYear) {
        this.toYear = toYear;
    }

    
    public int getYear() {
        return year;
    }

    
    public void setYear(int year) {
        this.year = year;
    }


    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if ( obj instanceof WorkDates ) {
            WorkDates rhs = (WorkDates) obj;
            return new EqualsBuilder().append(this.year, rhs.year).append(this.toYear, rhs.toYear)
            .append(this.month, rhs.month).append(this.day, rhs.day).isEquals();
        }
        return false;
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.year).append(this.toYear).append(this.month).append(this.day).toHashCode();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.year);
        if ( this.toYear != Integer.MIN_VALUE) {
            sb.append("-");
            sb.append(this.toYear);
        }
        return sb.toString();
    }

    public int compareTo(Object o) {
        WorkDates rhs = (WorkDates) o;
        return new CompareToBuilder().append(this.getYear(), rhs.getYear())
                .append(this.getToYear(), rhs.getToYear())
                .append(this.getMonth(), rhs.getMonth())
                .append(this.getDay(), rhs.getDay())
                .toComparison();
        
    }
    
    
    
}
