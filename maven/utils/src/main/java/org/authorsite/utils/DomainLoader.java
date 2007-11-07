/**
 * This file is part of the authorsite application.
 *
 * The authorsite application is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * The authorsite application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the authorsite application; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 */

package org.authorsite.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.AbstractWork;
import org.authorsite.domain.bib.Article;
import org.authorsite.domain.bib.Book;
import org.authorsite.domain.bib.Chapter;
import org.authorsite.domain.bib.Journal;
import org.authorsite.domain.bib.Thesis;
import org.authorsite.domain.service.CollectiveManagementService;
import org.authorsite.domain.service.IndividualManagementService;
import org.authorsite.domain.service.bib.ArticleService;
import org.authorsite.domain.service.bib.BookService;
import org.authorsite.domain.service.bib.ChapterService;
import org.authorsite.domain.service.bib.JournalService;
import org.authorsite.domain.service.bib.ThesisService;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Utility class to help persist domain objects 
 * parsed from an external source.
 * 
 * @author jejking
 *
 */
public class DomainLoader {

    private static final Logger LOGGER = Logger.getLogger(DomainLoader.class);
    
    private ArticleService articleService;
    private JournalService journalService;
    private BookService bookService;
    private ChapterService chapterService;
    private ThesisService thesisService;
    
    private IndividualManagementService individualManagementService;
    private CollectiveManagementService collectiveManagementService;
    
    
    
    
    /**
     * @param articleService the articleService to set
     */
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * @param bookService the bookService to set
     */
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * @param chapterService the chapterService to set
     */
    public void setChapterService(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    /**
     * @param collectiveManagementService the collectiveManagementService to set
     */
    public void setCollectiveManagementService(
    	CollectiveManagementService collectiveManagementService) {
        this.collectiveManagementService = collectiveManagementService;
    }

    /**
     * @param individualManagementService the individualManagementService to set
     */
    public void setIndividualManagementService(
    	IndividualManagementService individualManagementService) {
        this.individualManagementService = individualManagementService;
    }

    /**
     * @param journalService the journalService to set
     */
    public void setJournalService(JournalService journalService) {
        this.journalService = journalService;
    }

    /**
     * @param thesisService the thesisService to set
     */
    public void setThesisService(ThesisService thesisService) {
        this.thesisService = thesisService;
    }

    public void loadList(List<AbstractWork> workList) {
	
	for (AbstractWork work : workList) {
	    try {
		loadWork(work);
	    }
	    catch (DataAccessException dae) {
		LOGGER.warn(dae);
	    }
	}
	
    }

    @Transactional
    private void loadWork(AbstractWork work) throws DataAccessException {
	if ( work instanceof Article) {
	    loadArticle((Article) work);
	}
	else if (work instanceof Book) {
	    loadBook((Book) work);
	}
	else if (work instanceof Chapter) {
	    loadChapter((Chapter) work);
	}
	else if (work instanceof Thesis) {
	    loadThesis((Thesis) work);
	}
	
    }

    private Thesis loadThesis(Thesis thesis) {
	List<Thesis> thesisList = thesisService.findThesesByTitle(thesis.getTitle());
	for (Thesis loadedThesis : thesisList) {
	    if (loadedThesis.equals(thesis)) {
		return loadedThesis;
	    }
	}
	Individual author = this.loadIndividual(thesis.getAuthor());
	Collective awardingBody = this.loadCollective(thesis.getAwardingBody());
	Thesis newThesis = new Thesis();
	newThesis.setAuthor(author);
	newThesis.setAwardingBody(awardingBody);
	newThesis.setTitle(thesis.getTitle());
	newThesis.setDegree(thesis.getDegree());
	newThesis.setWorkDates(thesis.getWorkDates());
	
	thesisService.saveThesis(newThesis);
	return newThesis;
    }

    private Chapter loadChapter(Chapter chapter) {
	List<Chapter> chapterList = chapterService.findChaptersByTitle(chapter.getTitle());
	for (Chapter loadedChapter : chapterList) {
	    if (loadedChapter.equals(chapter)) {
		return chapter;
	    }
	}
	Book chapterBook = this.loadBook(chapter.getBook());
	Set< AbstractHuman > authors = loadAbstractHumans(chapter.getAuthors());
	Set<AbstractHuman> editors = loadAbstractHumans(chapter.getEditors());
	
	Chapter newChapter = new Chapter();
	newChapter.addAuthors(authors);
	newChapter.addEditors(editors);
	newChapter.setBook(chapterBook);
	newChapter.setTitle(chapter.getTitle());
	newChapter.setWorkDates(chapter.getWorkDates());
	newChapter.setPages(chapter.getPages());
	newChapter.setChapter(chapter.getChapter());
	
	chapterService.saveChapter(newChapter);
	return newChapter;
    }

    private Article loadArticle(Article article) {
	List<Article> articleList = this.articleService.findArticlesByTitle(article.getTitle());
	for (Article loadedArticle : articleList) {
	    if (loadedArticle.equals(article)) {
		return loadedArticle;
	    }
	}
	Journal journal = this.loadJournal(article.getJournal());
	Set<AbstractHuman> authors = this.loadAbstractHumans(article.getAuthors());
	Set<AbstractHuman> editors = this.loadAbstractHumans(article.getEditors());
	
	Article newArticle = new Article();
	newArticle.setJournal(journal);
	newArticle.addAuthors(authors);
	newArticle.addEditors(editors);
	newArticle.setTitle(article.getTitle());
	newArticle.setWorkDates(article.getWorkDates());
	newArticle.setIssue(article.getIssue());
	newArticle.setPages(article.getPages());
	newArticle.setVolume(article.getVolume());
	
	articleService.saveArticle(newArticle);
	return newArticle;
    }

    private Book loadBook(Book book) {
	
	List<Book> loadedList= bookService.findBooksByTitle(book.getTitle());
	for (Book loadedBook : loadedList ) {
	    if (loadedBook.equals(book)) {
		return loadedBook;
	    }
	}
	
	Set< AbstractHuman > authors = loadAbstractHumans(book.getAuthors());
	Set<AbstractHuman> editors = loadAbstractHumans(book.getEditors());
	AbstractHuman publisher = loadAbstractHuman(book.getPublisher());
	
	Book newBook = new Book();
	newBook.addAuthors(authors);
	newBook.addEditors(editors);
	newBook.setPublisher(publisher);
	newBook.setTitle(book.getTitle());
	newBook.setVolume(book.getVolume());
	newBook.setWorkDates(book.getWorkDates());
	
	bookService.saveBook(newBook);
	return newBook;
    }
    
    private AbstractHuman loadAbstractHuman(AbstractHuman abstractHuman) {
	if (abstractHuman == null) {
	    return null;
	}
	if (abstractHuman instanceof Individual) {
	    return this.loadIndividual((Individual) abstractHuman);
	}
	else {
	    return this.loadCollective((Collective) abstractHuman);
	}
    }

    private Set<AbstractHuman> loadAbstractHumans(Set<AbstractHuman> abstractHumans) {
	if (abstractHumans == null) {
	    return Collections.emptySet();
	}
	else {
	    Set<AbstractHuman> loadedSet = new HashSet<AbstractHuman>();
	    for (AbstractHuman human : abstractHumans ) {
		loadedSet.add(this.loadAbstractHuman(human));
	    }
	    return loadedSet;
	}
    }

    private Journal loadJournal(Journal journal) {
	List<Journal> journalList = this.journalService.findJournalsByTitle(journal.getTitle());
	for (Journal loadedJournal : journalList) {
	    if (journal.equals(loadedJournal)) {
		return loadedJournal;
	    }
	}
	
	Journal newJournal = new Journal();
	newJournal.setPublisher(this.loadAbstractHuman(journal.getPublisher()));
	newJournal.setTitle(journal.getTitle());
	newJournal.setWorkDates(journal.getWorkDates());
	journalService.saveJournal(newJournal);
	return newJournal;
    }
    
    private Individual loadIndividual(Individual individual) {
	List<Individual> individuals = this.individualManagementService
		.findIndividualsByNameAndGivenNames(individual.getName(),
			individual.getGivenNames());
	for (Individual loadedIndividual : individuals) {
	    if (loadedIndividual.equals(individual)) {
		return loadedIndividual;
	    }
	}
	
	individualManagementService.save(individual);
	return individual;
    }
    
    private Collective loadCollective(Collective collective) {
	List<Collective> coList = this.collectiveManagementService.findCollectivesByName(collective.getName());
	for (Collective loadedCollective : coList) {
	    if ( loadedCollective.equals(collective)) {
		return loadedCollective;
	    }
	}
	collectiveManagementService.save(collective);
	return collective;
    }
    
}
