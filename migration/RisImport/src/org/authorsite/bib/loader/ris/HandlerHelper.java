package org.authorsite.bib.loader.ris;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.authorsite.bib.Individual;

public class HandlerHelper {

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

    public static int extractYear(List<String> values) {
        String yearString = values.get(0).trim();
        return Integer.parseInt(yearString);
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
