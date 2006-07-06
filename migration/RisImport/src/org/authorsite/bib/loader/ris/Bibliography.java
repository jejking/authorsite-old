package org.authorsite.bib.loader.ris;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.authorsite.bib.Book;
import org.authorsite.bib.Chapter;
import org.authorsite.bib.Collective;
import org.authorsite.bib.Individual;
import org.authorsite.bib.Journal;
import org.authorsite.bib.Thesis;


public class Bibliography {

    private static final Bibliography INSTANCE = new Bibliography();
    private static int humanIdCounter = 0;
    private static int workIdCounter = 0;
    
    private Map<Individual, Individual> individuals = new HashMap<Individual, Individual>();
    private Map<Collective, Collective> collectives = new HashMap<Collective, Collective>();
    private Map<Journal, Journal> journals = new HashMap<Journal, Journal>();
    private Map<Chapter, Chapter> chapters = new HashMap<Chapter, Chapter>();
    private Map<Book, Book> books = new HashMap<Book, Book>();
    private Map<Thesis, Thesis> theses = new HashMap<Thesis, Thesis>();
    
    private Bibliography() {
        super();
    }
    
    public static Bibliography getInstance() {
        return Bibliography.INSTANCE;
    }
    
    public static int getNextHumanId() {
        return Bibliography.humanIdCounter++;
    }
    
    public static int getNextWorkId() {
        return Bibliography.workIdCounter++;
    }
    
    public Individual getAuthoritativeIndividual(Individual i) {
        if ( this.individuals.containsKey(i) ) {
            return this.individuals.get(i);
        }
        else {
            i.setId(Bibliography.getNextHumanId());
            this.individuals.put(i, i);
            return i;
        }
    }
    
    public Collective handleCollective(Collective c) {
        if ( this.collectives.containsKey(c)) {
            return this.collectives.get(c);
        }
        else {
            c.setId(Bibliography.getNextHumanId());
            this.collectives.put(c, c);
            return c;
        }
    }
    
    public Journal getAuthoritativeJournal(Journal j) {
        return null;
    }
    
    public Chapter getAuthoritativeChapter(Chapter c) {
        return null;
    }
    
    public Book getAuthoritativeBook(Book b) {
        return null;
    }
    
    public Thesis getAuthoritativeThesis(Thesis t) {
        if ( this.theses.containsKey( t )) {
            return t;
        }
        else {
            t.setId(Bibliography.getNextWorkId());
            this.theses.put(t, t);
            return t;
        }
    }
}


