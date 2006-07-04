package org.authorsite.bib.loader.ris;

import java.util.HashSet;
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
    
    private Set<Individual> individuals = new HashSet<Individual>();
    private Set<Collective> collectives = new HashSet<Collective>();
    private Set<Journal> journals = new HashSet<Journal>();
    private Set<Chapter> chapters = new HashSet<Chapter>();
    private Set<Book> books = new HashSet<Book>();
    private Set<Thesis> theses = new HashSet<Thesis>();
    
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
    
    public void handleIndividual(Individual i) {
        
    }
    
    public void handleCollective(Collective c) {
        
    }
    
    public void handleJournal(Journal j) {
        
    }
    
    public void handleChapter(Chapter c) {
        
    }
    
    public void handleBook(Book b) {
        
    }
    
    public void handleThesis(Thesis t) {
        
    }
}


