package org.authorsite.utils.bib.loader.ris;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.authorsite.bib.AbstractBibEntry;
import org.authorsite.bib.Article;
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
    private Map<Article, Article> articles = new HashMap<Article, Article>();
    
    private Bibliography() {
        super();
    }
    
    public static Bibliography getInstance() {
        return Bibliography.INSTANCE;
    }
    
    public static int getNextHumanId() {
        return ++Bibliography.humanIdCounter;
    }
    
    public static int getNextWorkId() {
        return ++Bibliography.workIdCounter;
    }
    
    public void writeBibliographyToSql(Writer writer, boolean addDeletes) throws IOException {
        
        if ( addDeletes ) {
            writer.write("DELETE FROM work_work_relationships;\n");
            writer.write("DELETE FROM human_work_relationships;\n");
            writer.write("DELETE FROM works;\n");
            writer.write("DELETE FROM humans;\n");
        }
        
        this.writeCollectionToSql(writer, this.individuals.values());
        this.writeCollectionToSql(writer, this.collectives.values());
        this.writeCollectionToSql(writer, this.journals.values());
        this.writeCollectionToSql(writer, this.articles.values());
        this.writeCollectionToSql(writer, this.books.values());
        this.writeCollectionToSql(writer, this.chapters.values());
        this.writeCollectionToSql(writer, this.theses.values());
    }
    
    private void writeCollectionToSql(Writer writer, Collection<? extends AbstractBibEntry> entries) throws IOException {
        for ( AbstractBibEntry entry : entries ) {
            writer.write(entry.toSql());
            writer.write("\n");
        }
    }
    
    public Map<Book, Book> getBooks() {
        return books;
    }

    
    public Map<Chapter, Chapter> getChapters() {
        return chapters;
    }

    
    public Map<Collective, Collective> getCollectives() {
        return collectives;
    }

    
    public Map<Individual, Individual> getIndividuals() {
        return individuals;
    }

    
    public Map<Journal, Journal> getJournals() {
        return journals;
    }

    
    public Map<Thesis, Thesis> getTheses() {
        return theses;
    }
    
    public Map<Article, Article> getArticles() {
        return this.articles;
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
    
    public Collective getAuthoritativeCollective(Collective c) {
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
        if ( this.journals.containsKey(j)) {
            return this.journals.get(j);
        }
        else {
            j.setId(Bibliography.getNextWorkId());
            this.journals.put(j,j);
            return j;
        }
    }
    
    public Chapter getAuthoritativeChapter(Chapter c) {
        if (this.chapters.containsKey( c )) {
            return this.chapters.get(c);
        }
        else {
            c.setId(Bibliography.getNextWorkId());
            this.chapters.put(c, c);
            return c;
        }
    }
    
    public Book getAuthoritativeBook(Book b) {
        if ( this.books.containsKey( b )) {
            return this.books.get(b);
        }
        else {
            b.setId(Bibliography.getNextWorkId());
            this.books.put(b, b);
            return b;
        }
    }
    
    public Thesis getAuthoritativeThesis(Thesis t) {
        if ( this.theses.containsKey( t )) {
            return theses.get(t);
        }
        else {
            t.setId(Bibliography.getNextWorkId());
            this.theses.put(t, t);
            return t;
        }
    }
    
    public Article getAuthoritativeArticle(Article a) {
        if ( this.articles.containsKey(a)) {
            return this.articles.get(a);
        }
        else {
            a.setId(Bibliography.getNextWorkId());
            this.articles.put(a, a);
            return a;
        }
        
    }
    
    public void clearIndividuals() {
        this.individuals.clear();
    }
    
    public void clearCollectives() {
        this.collectives.clear();
    }
    
    public void clearJournals() {
        this.journals.clear();
    }
    
    public void clearChapters() {
        this.chapters.clear();
    }
    
    public void clearBooks() {
        this.books.clear();
    }
    
    public void clearTheses() {
        this.theses.clear();
    }
    
    public void clearArticles() {
        this.articles.clear();
    }

   
    
}