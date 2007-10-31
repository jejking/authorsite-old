/**
 * 
 */
package org.authorsite.dao.bib;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.authorsite.dao.AbstractJPATest;
import org.authorsite.dao.CollectiveDao;
import org.authorsite.dao.IndividualDao;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.Article;
import org.authorsite.domain.bib.Journal;
import org.authorsite.domain.bib.WorkDates;

/**
 * @author jejking
 *
 */
public class ArticleDaoJPATest extends AbstractJPATest {

    private JournalDao journalDao;
    private IndividualDao individualDao;
    private ArticleDao articleDao;
    
    /**
     * @param articleDao the articleDao to set
     */
    public void setArticleDao(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }


    /**
     * @param individualDao the individualDao to set
     */
    public void setIndividualDao(IndividualDao individualDao) {
        this.individualDao = individualDao;
    }


    /**
     * @param journalDao the journalDao to set
     */
    public void setJournalDao(JournalDao journalDao) {
        this.journalDao = journalDao;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:/spring-test-appcontext-1.xml"};
    }
    

    @Override
    protected void onSetUpInTransaction() throws Exception {
        super.onSetUpInTransaction();
        //  create indivs (authors, editors, publishers)
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (0, current_timestamp, 0, current_timestamp, 0, 0, null, 'Root', null, 'Individual')");
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (1, current_timestamp, 1, current_timestamp, 1, 1, null, 'Bar', 'Foo', 'Individual')");
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (2, current_timestamp, 1, current_timestamp, 1, 1, null, 'Wurst', 'Hans', 'Individual')");
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (3, current_timestamp, 1, current_timestamp, 1, 1, null, 'Bear', 'Honey', 'Individual')");
        
        // journals
        // journal 1, "Sausage Studies", no publisher
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 1, 1, current_timestamp, current_timestamp, 'Sausage Studies', " +
                "null, null, 0, 0 )");
    
        jdbcTemplate.execute("insert into JOURNAL (id) VALUES (1)");
        
        // journal 2, "Honey Review", no publisher, no dates
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 2, 1, current_timestamp, current_timestamp, 'Honey Review', " +
                "null, null, 0, 0 )");
    
        jdbcTemplate.execute("insert into JOURNAL (id) VALUES (2)");
        
        // article 3, in sausage studies

        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 3, 1, current_timestamp, current_timestamp, 'New Sausages', " +
                "'1990-01-01', '1990-01-01', 0, 0 )");
    
        jdbcTemplate.execute("insert into ARTICLE (id, issue, pages, volume, journal_id)" +
        		" VALUES (3, null, '23-24', 'V', 1)");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
        		"values (3, 'AUTHOR', 1) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
			"values (3, 'AUTHOR', 2) ");
        
        // article 4, also in sausage studies
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 4, 1, current_timestamp, current_timestamp, 'Berlin Butchers', " +
                "'1991-03-01', '1991-03-01', 0, 0 )");
    
        jdbcTemplate.execute("insert into ARTICLE (id, issue, pages, volume, journal_id)" +
        		" VALUES (4, null, '65-70', 'VI', 1)");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
        		"values (4, 'EDITOR', 3) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
			"values (4, 'AUTHOR', 2) ");
        
        // article 5, in honey review
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 5, 1, current_timestamp, current_timestamp, 'Bees in the Field', " +
                "'1993-08-01', '1993-08-01', 0, 0 )");
    
        jdbcTemplate.execute("insert into ARTICLE (id, issue, pages, volume, journal_id)" +
        		" VALUES (5, null, '1-10', '22', 2)");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
        		"values (5, 'AUTHOR', 3) ");
        
        // article 6, bees and sheep        
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 6, 1, current_timestamp, current_timestamp, 'Why do Bees and Sheep argue?', " +
                "'1995-12-01', '1995-12-01', 0, 0 )");
    
        jdbcTemplate.execute("insert into ARTICLE (id, issue, pages, volume, journal_id)" +
        		" VALUES (6, null, '24-38', '25', 2)");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
        		 "values ( 6, 'AUTHOR', 1) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
        		"values ( 6, 'AUTHOR', 3) ");
        
        // article 7, cooking 
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 7, 1, current_timestamp, current_timestamp, 'Cooking Sausages in Honey', " +
                "'1996-01-01', '1996-01-01', 0, 0 )");
    
        jdbcTemplate.execute("insert into ARTICLE (id, issue, pages, volume, journal_id)" +
        		" VALUES (7, null, '42-51', '26', 2)");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
        		 "values ( 7, 'AUTHOR', 2) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
			"values ( 7, 'AUTHOR', 3) ");

    }
    
    
    /**
     * Test method for {@link org.authorsite.dao.bib.ArticleDaoJPA#countArticles()}.
     */
    public final void testCountArticles() {
	assertEquals(5, this.articleDao.countArticles());
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ArticleDaoJPA#findAllArticles()}.
     */
    public final void testFindAllArticles() {
	Article a3 = this.articleDao.findById(3);
	List<Article> articles = this.articleDao.findAllArticles();
	assertEquals(5, articles.size());
	assertTrue(articles.contains(a3));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ArticleDaoJPA#findAllArticles(int, int)}.
     */
    public final void testFindAllArticlesIntInt() {
	Article a3 = this.articleDao.findById(3);
	Article a7 = this.articleDao.findById(7);
	List<Article> arList1 = this.articleDao.findAllArticles(1,2);
	assertEquals(2, arList1.size());
	assertTrue(arList1.contains(a3));
	assertFalse(arList1.contains(a7));
	List<Article> arList2 = this.articleDao.findAllArticles(2, 3);
	assertEquals(2, arList2.size());
	assertTrue(arList2.contains(a7));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ArticleDaoJPA#findArticlesAfterDate(java.util.Date)}.
     */
    public final void testFindArticlesAfterDate() {
	GregorianCalendar gc = new GregorianCalendar( 1995, Calendar.JULY, 1);
	Article a6 = this.articleDao.findById(6);
	Article a7 = this.articleDao.findById(7);
	List<Article> articles = this.articleDao.findArticlesAfterDate(gc.getTime());
	assertEquals(2, articles.size());
	assertTrue(articles.contains(a6));
	assertTrue(articles.contains(a7));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ArticleDaoJPA#findArticlesBeforeDate(java.util.Date)}.
     */
    public final void testFindArticlesBeforeDate() {
	GregorianCalendar gc = new GregorianCalendar( 1995, Calendar.JULY, 1);
	Article a3 = this.articleDao.findById(3);
	Article a4 = this.articleDao.findById(4);
	Article a5 = this.articleDao.findById(5);
	
	List<Article> articles = this.articleDao.findArticlesBeforeDate(gc.getTime());
	assertEquals(3, articles.size());
	assertTrue(articles.contains(a3));
	assertTrue(articles.contains(a4));
	assertTrue(articles.contains(a5));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ArticleDaoJPA#findArticlesBetweenDates(java.util.Date, java.util.Date)}.
     */
    public final void testFindArticlesBetweenDates() {
	GregorianCalendar gcStart = new GregorianCalendar( 1994, Calendar.APRIL, 01 );
	GregorianCalendar gcEnd = new GregorianCalendar( 1995, Calendar.DECEMBER, 31 );
	Article a6 = this.articleDao.findById(6);
	
	List<Article> articles = this.articleDao.findArticlesBetweenDates(gcStart.getTime(), gcEnd.getTime());
	assertEquals(1, articles.size());
	assertTrue(articles.contains(a6));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ArticleDaoJPA#findArticlesByTitle(java.lang.String)}.
     */
    public final void testFindArticlesByTitle() {
	Article a3 = this.articleDao.findById(3);
	List<Article> articles = this.articleDao.findArticlesByTitle("New Sausages");
	assertTrue(articles.contains(a3));
	assertEquals(1, articles.size());
	
	List<Article> noneFound = this.articleDao.findArticlesByTitle("Wibble Blah Foo");
	assertTrue(noneFound.isEmpty());
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ArticleDaoJPA#findArticlesByTitleWildcard(java.lang.String)}.
     */
    public final void testFindArticlesByTitleWildcard() {
	Article a3 = this.articleDao.findById(3);
	Article a7 = this.articleDao.findById(7);
	List<Article> articles = this.articleDao.findArticlesByTitleWildcard("%Sausages%");
	assertEquals(2, articles.size());
	assertTrue(articles.contains(a3));
	assertTrue(articles.contains(a7));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ArticleDaoJPA#findArticlesInJournal(org.authorsite.domain.bib.Journal)}.
     */
    public final void testFindArticlesInJournal() {
	Journal sausageStudies = this.journalDao.findById(1);
	Article a3 = this.articleDao.findById(3);
	Article a4 = this.articleDao.findById(4);
	
	List<Article> articles = this.articleDao.findArticlesInJournal(sausageStudies);
	assertEquals(2, articles.size());
	assertTrue(articles.contains(a3));
	assertTrue(articles.contains(a4));
	
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ArticleDaoJPA#findArticlesWithAuthor(org.authorsite.domain.AbstractHuman)}.
     */
    public final void testFindArticlesWithAuthor() {
	Individual fooBar = this.individualDao.findById(1);
	Article a3 = this.articleDao.findById(3);
	Article a6 = this.articleDao.findById(6);
	
	List<Article> articles = this.articleDao.findArticlesWithAuthor(fooBar);
	assertEquals(2, articles.size());
	assertTrue(articles.contains(a3));
	assertTrue(articles.contains(a6));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ArticleDaoJPA#findArticlesWithAuthorOrEditor(org.authorsite.domain.AbstractHuman)}.
     */
    public final void testFindArticlesWithAuthorOrEditor() {
	Individual honeyBear = this.individualDao.findById(3);
	Article a4 = this.articleDao.findById(4);
	Article a5 = this.articleDao.findById(5);
	Article a6 = this.articleDao.findById(6);
	Article a7 = this.articleDao.findById(7);
	
	List<Article> articles = this.articleDao.findArticlesWithAuthorOrEditor(honeyBear);
	assertEquals(4, articles.size());
	assertTrue(articles.contains(a4));
	assertTrue(articles.contains(a5));
	assertTrue(articles.contains(a6));
	assertTrue(articles.contains(a7));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ArticleDaoJPA#findArticlesWithEditor(org.authorsite.domain.AbstractHuman)}.
     */
    public final void testFindArticlesWithEditor() {
	Individual honeyBear = this.individualDao.findById(3);
	Article a4 = this.articleDao.findById(4);
	
	List<Article> articles = this.articleDao.findArticlesWithEditor(honeyBear);
	assertEquals(1, articles.size());
	assertTrue(articles.contains(a4));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ArticleDaoJPA#findById(long)}.
     */
    public final void testFindById() {
	Journal honeyReview = this.journalDao.findById(2);
	Individual fooBar = this.individualDao.findById(1);
	Individual honeyBear = this.individualDao.findById(3);
	
	Article a6 = this.articleDao.findById(6);
	assertEquals("Why do Bees and Sheep argue?", a6.getTitle());
	assertEquals(honeyReview, a6.getJournal());
	
	assertEquals("24-38", a6.getPages());
	
	GregorianCalendar gc = new GregorianCalendar( 1995, Calendar.DECEMBER, 1 );
	WorkDates workDates = new WorkDates(gc.getTime(), gc.getTime());
	assertEquals(workDates, a6.getWorkDates());
	assertEquals(2, a6.getAuthors().size());
	assertTrue(a6.getAuthors().contains(fooBar));
	assertTrue(a6.getAuthors().contains(honeyBear));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ArticleDaoJPA#saveArticle(org.authorsite.domain.bib.Article)}.
     */
    public final void testSaveArticle() {
	Individual hansWurst = this.individualDao.findById(2);
	Individual fooBar = this.individualDao.findById(1);
	Journal sausageStudies = this.journalDao.findById(1);
	
	Article article = new Article("Beyond Salami: A Journey to the Heart of Offal");
	article.setJournal(sausageStudies);
	article.addAuthor(hansWurst);
	article.setPages("34-45");
	article.setVolume("30");
	GregorianCalendar gc = new GregorianCalendar( 1992, Calendar.JANUARY, 1);
	WorkDates workDates = new WorkDates( gc.getTime() );
	article.setWorkDates(workDates);
	article.setCreatedBy(fooBar);
	article.setUpdatedBy(fooBar);
	
	this.articleDao.saveArticle(article);
	long id = article.getId();
	
	Article loaded = this.articleDao.findById(id);
	assertEquals("Beyond Salami: A Journey to the Heart of Offal", loaded.getTitle());
	assertEquals(workDates, loaded.getWorkDates());
	assertEquals(sausageStudies, loaded.getJournal());
	assertTrue(loaded.getAuthors().contains(hansWurst));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ArticleDaoJPA#updateArticle(org.authorsite.domain.bib.Article)}.
     */
    public final void testUpdateArticle() {
	Article a3 = this.articleDao.findById(3);
	a3.setTitle("New Sausages from Old");
	this.articleDao.updateArticle(a3);
	
	Article loaded = this.articleDao.findById(3);
	assertEquals("New Sausages from Old", loaded.getTitle());
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ArticleDaoJPA#deleteArticle(org.authorsite.domain.bib.Article)}.
     */
    public final void testDeleteArticle() {
	Article a3 = this.articleDao.findById(3);
	this.articleDao.deleteArticle(a3);
	
	Article loaded = this.articleDao.findById(3);
	assertNull(loaded);
    }

}
