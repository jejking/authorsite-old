package org.authorsite.utils.bib.loader.ris;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.authorsite.bib.Individual;
import org.authorsite.bib.WorkDates;

public class HandlerHelper {

    private static final Pattern SINGLE_YEAR_PATTERN = Pattern.compile("(\\d){4}");
    private static final Pattern YEAR_RANGE_PATTERN = Pattern.compile("(\\d){4}-(\\d){1,4}");
    private static final Pattern DAY_DATE_PATTERN = Pattern.compile("(\\d){4} (\\d){1,2} (\\p{Alpha})+");
    private static final Pattern MONTH_DATE_PATTERN = Pattern.compile("(\\d){4} (\\p{Alpha})+");
    private static final String[] CAL_MONTHS = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    
    
    public static SortedSet<Individual> getIndividuals(String entryLineValue) {
        
        SortedSet<Individual> individuals = new TreeSet<Individual>();
        
        // first split on ; (A2 entries are doubled up, as are editors...)
        
        String[] distinctNames = entryLineValue.trim().split(";");
        for (String distinctName : distinctNames ) {
            String[] nameComponents = distinctName.trim().split(",");
            Individual i = new Individual();
            int j = 0;
            for ( String nameComponent : nameComponents ) {
                j++;
                nameComponent = nameComponent.trim();
                switch (j) {
                case 1:
                    i.setName(nameComponent);
                    break;
                case 2:
                    i.setGivenNames(nameComponent);
                    break;
                case 3:
                    i.setNameQualification(nameComponent);
                default:
                    break;
                }
            }
            individuals.add(i);
            
        }
        
        return individuals;
    }
   
    public static SortedSet<Individual> getAuthoritativeIndividuals(RISEntry entry, String key) {
        List<String> authorStrings = entry.getValues(key);
        SortedSet<Individual> authorBeanSet = new TreeSet<Individual>();
        
        for ( String authorString : authorStrings ) {
            authorBeanSet.addAll(HandlerHelper.getIndividuals(authorString));
        }
        
        SortedSet<Individual> authoritativeAuthors = new TreeSet<Individual>();
        
        for ( Individual individual : authorBeanSet ) {
            authoritativeAuthors.add(Bibliography.getInstance().getAuthoritativeIndividual(individual));
        }
        
        return authoritativeAuthors;
    }

    public static WorkDates extractYear(List<String> values) throws RISException {
        if ( values == null || values.size() == 0 ) {
            return new WorkDates();
        }
        String yearsString = values.get(0);
        
        // case 1 - just a single year
        Matcher m1 = HandlerHelper.SINGLE_YEAR_PATTERN.matcher(yearsString);
        if ( m1.matches()) {
            return extractSingleYear(yearsString);
        }
        
        // case 2 - a range of years split by a -
        Matcher m2 = HandlerHelper.YEAR_RANGE_PATTERN.matcher(yearsString);
        if ( m2.matches()) {
            return extractYearRange(yearsString);
        }
        
        // case 3 - a date in from YYYY DD 'Month name in English'
        Matcher m3 = HandlerHelper.DAY_DATE_PATTERN.matcher(yearsString);
        if ( m3.matches() ) {
            return extractDayDate(yearsString);
        }
        
        // case 4 - a date in form YYYY 'Month name in English'
        Matcher m4 = HandlerHelper.MONTH_DATE_PATTERN.matcher(yearsString);
        if ( m4.matches()) {
            return extractMonthDate(yearsString);
        }
        
        throw new RISException("Do not know how to handle " + yearsString);
        
    }

    

    private static WorkDates extractSingleYear(String yearsString) {
        WorkDates workDates = new WorkDates();
        workDates.setYear(Integer.parseInt(yearsString));
        return workDates;
    }

    private static WorkDates extractYearRange(String yearsString) {
        String[] years = yearsString.split("-");
        WorkDates workYears = new WorkDates();
        
        
        int fromYear = Integer.parseInt(years[0]);
        workYears.setYear(fromYear);
        
        String origToYearString = years[1];
        
        String toYearString = years[0].substring(0, (years[0]
                .length() - origToYearString.length()))
                + origToYearString;
        int toYear = Integer.parseInt(toYearString);
        workYears.setToYear(toYear);
        return workYears;
    }

    private static WorkDates extractDayDate(String yearsString) throws RISException {
        String[] dateComponents = yearsString.split(" ");
        WorkDates workDate = new WorkDates();
        workDate.setYear(Integer.parseInt(dateComponents[0]));
        workDate.setDay(Integer.parseInt(dateComponents[1]));
        
        String normalisedMonth = dateComponents[2].substring(0, 3).toUpperCase();
        for ( int i = 0; i < 12; i ++ ) {
            if ( HandlerHelper.CAL_MONTHS[i].equals(normalisedMonth)) {
                workDate.setMonth(i + 1);
            }
        }
        
        if ( workDate.getMonth() == Integer.MIN_VALUE ) {
            throw new RISException("Could not determine month for " + dateComponents[2] + " using normalised month " + normalisedMonth);
        }
        
        return workDate;
    }
    
    private static WorkDates extractMonthDate(String yearsString) throws RISException {
        String[] dateComponents = yearsString.split(" ");
        WorkDates workDate = new WorkDates();
        workDate.setYear(Integer.parseInt(dateComponents[0]));
        String normalisedMonth = dateComponents[1].substring(0, 3).toUpperCase();
        for ( int i = 0; i < 12; i ++ ) {
            if ( HandlerHelper.CAL_MONTHS[i].equals(normalisedMonth)) {
                workDate.setMonth(i + 1);
            }
        }
        
        if ( workDate.getMonth() == Integer.MIN_VALUE ) {
            throw new RISException("Could not determine month for " + dateComponents[2] + " using normalised month " + normalisedMonth);
        }
        return workDate;
    }

    public static String getFirstString(List<String> values) {
        if (! values.isEmpty()) {
            return values.get(0);
        }
        else {
            return null;
        }
    }
    
}
