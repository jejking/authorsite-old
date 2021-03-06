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


package org.authorsite.dao.bib;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.authorsite.dao.AbstractJPATest;
import org.authorsite.dao.CollectiveDao;
import org.authorsite.dao.IndividualDao;
import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.AbstractAuthoredEditedPublishedWork;
import org.authorsite.domain.bib.AbstractWork;
import org.authorsite.domain.bib.Article;
import org.authorsite.domain.bib.Chapter;
import org.authorsite.domain.bib.Journal;
import org.authorsite.domain.bib.Thesis;
import org.authorsite.domain.bib.WorkProducerType;

/**
 *
 * @author jking
 */
public class GenericWorkDaoJPATest extends AbstractJPATest {

    public GenericWorkDaoJPATest() {
        super();
    }
    private GenericWorkDao genericWorkDao;
    private IndividualDao individualDao;
    private CollectiveDao collectiveDao;

    public void setGenericWorkDao(GenericWorkDao genericWorkDao) {
        this.genericWorkDao = genericWorkDao;
    }

    public void setCollectiveDao(CollectiveDao collectiveDao) {
        this.collectiveDao = collectiveDao;
    }

    public void setIndividualDao(IndividualDao individualDao) {
        this.individualDao = individualDao;
    }
    
    protected String[] getConfigLocations() {
        return new String[]{"classpath:/spring-test-appcontext-1.xml"};
    }

    @Override
    protected void onSetUpInTransaction() throws Exception {
        super.onSetUpInTransaction();
        
        /*
         * what do we need
         * - a person (to have created and updated the works)
         * - some journals
         * - some articles
         */
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (0, current_timestamp, 0, current_timestamp, 0, 1, null, 'Wurst', 'Hans', 'Individual')");
        
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (1, current_timestamp, 0, current_timestamp, 0, 1, null, 'Wurst', 'Horst', 'Individual')");
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (2, current_timestamp, 0, current_timestamp, 0, 1, null, 'Bar', 'Foo', 'Individual')");
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (3, current_timestamp, 0, current_timestamp, 0, 1, null, 'Bear', 'Foo', 'Individual')");
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (4, current_timestamp, 0, current_timestamp, 0, 1, null, 'Oxford University', null, 'Collective')");
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (5, current_timestamp, 0, current_timestamp, 0, 1, null, 'Foo Publishing, Inc', null, 'Collective')");
        
        
        // create journal "Wibble Studies"
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 0, 1, current_timestamp, current_timestamp, 'Wibble Studies', " +
                "null, null, 0, 0 )");
    
        jdbcTemplate.execute("insert into JOURNAL (id) VALUES (0)");
        
        // create journal article 'sausages in history' by hans wurst in "Wibble Studies"
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 1, 1, current_timestamp, current_timestamp, 'Sausages in History', " +
                "'2000-01-01', null, 0, 0 )");
        
        jdbcTemplate.execute("insert into ARTICLE (id, issue, pages, volume, journal_id) " +
                "values ( 1, 'a', '22-23', 'V', 0)" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (1, 'AUTHOR', 0) ");
        
        
        // create d.phil thesis by hans wurst from oxford university
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 2, 1, current_timestamp, current_timestamp, 'Die Wurst in der Geschichte Deutschlands', " +
                "'2001-01-01', '2004-01-01', 0, 0 )");
        
        jdbcTemplate.execute("insert into THESIS (id, degree ) " +
                "values ( 2, 'D. Phil')" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (2, 'AUTHOR', 0) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (2, 'AWARDING_BODY', 4) ");
        
        // create book by Foo Bar on Salami published by Foo Publishing, Inc
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 3, 1, current_timestamp, current_timestamp, 'Salami: Really a sausage?', " +
                "'2002-01-01', null, 0, 0 )");
        
        jdbcTemplate.execute("insert into BOOK (id, volume ) " +
                "values ( 3, null)" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (3, 'AUTHOR', 2) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (3, 'PUBLISHER', 5) ");
        
        
        // create chapter authored by Foo Bear + Foo Bar, edited by Horst Wurst, inside Foo Bar's book
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 4, 1, current_timestamp, current_timestamp, 'Salami & Salciccia, Knoblauch und Wurst', " +
                "'2003-01-01', '2004-01-01', 0, 0 )");
        
        jdbcTemplate.execute("insert into CHAPTER( id, pages, chapter, book_id) " +
                "values ( 4, '22-42', '3', 3)" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (4, 'AUTHOR', 3) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (4, 'AUTHOR', 2) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (4, 'EDITOR', 1) ");
        
        
        
        
    }
    
    public void testCountWorks() throws Exception {
        assertEquals(5, this.genericWorkDao.countWorks() ) ;
    }
    
    public void testFindWorkById() throws Exception {
        AbstractWork wibbleStudies = this.genericWorkDao.findWorkById(0);
        assertNotNull(wibbleStudies);
        assertEquals("Wibble Studies", wibbleStudies.getTitle());
        
        Journal wibJournal = (Journal) wibbleStudies;
    }
    
    public void testFindWorkByIdArticle() throws Exception {
        Individual hansWurst = this.individualDao.findById(0);
        
        AbstractWork wibbleStudies = this.genericWorkDao.findWorkById(0);
        AbstractWork sausagesInHistory = this.genericWorkDao.findWorkById(1);
        assertNotNull(sausagesInHistory);
        Article sausArticle = (Article) sausagesInHistory;
        assertEquals(sausArticle.getJournal(), wibbleStudies);
        assertEquals("a", sausArticle.getIssue());
        assertEquals("22-23", sausArticle.getPages());
        assertEquals("V", sausArticle.getVolume());
        
        assertTrue( sausArticle.getAuthors().contains(hansWurst));
    }
    
    public void testFindByIdNotExistent() throws Exception {
        AbstractWork notThere = this.genericWorkDao.findWorkById(666);
        assertNull(notThere);
    }
    
    public void testFindByIdIBook() throws Exception {
        
        Individual fooBar = this.individualDao.findById(2);
        Collective fooPublishingInc = this.collectiveDao.findById(5);
        
        AbstractWork aw = this.genericWorkDao.findWorkById(3);
        AbstractAuthoredEditedPublishedWork book = (AbstractAuthoredEditedPublishedWork) aw;
        assertEquals("Salami: Really a sausage?", book.getTitle());
        assertEquals(1, book.getAuthors().size());
        assertTrue(book.getAuthors().contains(fooBar));
        assertEquals(fooPublishingInc, book.getPublisher());
        assertEquals(2, book.getWorkProducers().size());
    }
    
    public void testFindByIdChapter() throws Exception {
        Individual horstWurst = this.individualDao.findById(1);
        Individual fooBar = this.individualDao.findById(2);
        Individual fooBear = this.individualDao.findById(3);
        AbstractWork bookAw = this.genericWorkDao.findWorkById(3);
        AbstractAuthoredEditedPublishedWork book = (AbstractAuthoredEditedPublishedWork) bookAw;
        
        AbstractWork chapterAw = this.genericWorkDao.findWorkById(4);
        Chapter chapter = (Chapter) chapterAw;
        assertEquals("Salami & Salciccia, Knoblauch und Wurst", chapter.getTitle());
        assertEquals("3", chapter.getChapter());
        assertEquals("22-42", chapter.getPages());
        assertEquals(book, chapter.getBook());
        assertEquals(3, chapter.getWorkProducers().size());
        assertTrue(chapter.getAuthors().contains(fooBar));
        assertTrue(chapter.getAuthors().contains(fooBear));
        assertTrue(chapter.getEditors().contains(horstWurst));
        
    }
    
    public void testFindByIdThesis() throws Exception {
        Individual hansWurst = this.individualDao.findById(0);
        Collective OxfordUniversity = this.collectiveDao.findById(4);
        AbstractWork thesisAw = this.genericWorkDao.findWorkById(2);
        Thesis thesis = (Thesis) thesisAw;
        assertEquals("Die Wurst in der Geschichte Deutschlands", thesis.getTitle());
        assertEquals("D. Phil", thesis.getDegree());
        assertEquals(hansWurst, thesis.getAuthor());
        assertEquals(OxfordUniversity, thesis.getAwardingBody());
        assertEquals(2, thesis.getWorkProducers().size());
    }
    
    public void testFindAllWorks() throws Exception {
        List<AbstractWork> works = this.genericWorkDao.findAllWorks();
        assertEquals(5, works.size());
        AbstractWork thesisAw = this.genericWorkDao.findWorkById(2);
        assertTrue(works.contains(thesisAw));
    }
    
    public void testFindAllWorksPaging() throws Exception {
        List<AbstractWork> works = this.genericWorkDao.findAllWorks(1, 2);
        assertEquals(2, works.size());
        works = this.genericWorkDao.findAllWorks(3, 2);
        assertEquals(1, works.size());
    }
    
    public void testFindByTitle() throws Exception {
        List<AbstractWork> works = this.genericWorkDao.findWorkByTitle("Die Wurst in der Geschichte Deutschlands");
        assertEquals(1, works.size());
        AbstractWork thesisAw = this.genericWorkDao.findWorkById(2);
        assertTrue(works.contains(thesisAw));
    }
    
    public void testFindByTitleNotThere() throws Exception {
        List<AbstractWork> works = this.genericWorkDao.findWorkByTitle("wibble wobble");
        assertTrue(works.isEmpty());
    }
    
    public void testFindByTitleWildcard() throws Exception {
        List<AbstractWork> works = this.genericWorkDao.findWorkByTitleWildcard("%Wurst%");
        assertEquals(2, works.size());
        AbstractWork thesisAw = this.genericWorkDao.findWorkById(2);
        assertTrue(works.contains(thesisAw));
        AbstractWork chapterAw = this.genericWorkDao.findWorkById(4);
        assertTrue(works.contains(chapterAw));
    }
    
    public void testFindByTitleWildcardNotThere() throws Exception {
        List<AbstractWork> works = this.genericWorkDao.findWorkByTitleWildcard("%wibble%");
        assertTrue(works.isEmpty());
    }
    
    public void testFindWorksWithProducer() throws Exception {
        Individual hansWurst = this.individualDao.findById(0);
        List<AbstractWork> works = this.genericWorkDao.findWorksWithProducer(hansWurst);
        assertEquals(2, works.size());
        
        AbstractWork thesisAw = this.genericWorkDao.findWorkById(2);
        assertTrue(works.contains(thesisAw));
        AbstractWork articleAw = this.genericWorkDao.findWorkById(1);
        assertTrue(works.contains(articleAw));
    }
    
    public void testFindWorksWithProducerOfType() throws Exception {
        Individual hansWurst = this.individualDao.findById(0);
        List<AbstractWork> works = this.genericWorkDao.findWorksWithProducerOfType(hansWurst, WorkProducerType.AUTHOR);
        
        assertEquals(2, works.size());
        
        AbstractWork thesisAw = this.genericWorkDao.findWorkById(2);
        assertTrue(works.contains(thesisAw));
        AbstractWork articleAw = this.genericWorkDao.findWorkById(1);
        assertTrue(works.contains(articleAw));
    }
    
    public void testFindWorksWithProducerOfTypeNoneMatch() throws Exception {
        Individual hansWurst = this.individualDao.findById(0);
        List<AbstractWork> works = this.genericWorkDao.findWorksWithProducerOfType(hansWurst, WorkProducerType.PUBLISHER);
        assertTrue(works.isEmpty());
    }
    
    public void testFindWorksBefore2002() throws Exception {
        GregorianCalendar gc = new GregorianCalendar(2002, Calendar.JANUARY, 1);
        Date firstJan2002 = gc.getTime();
        
        AbstractWork article = this.genericWorkDao.findWorkById(1);
        AbstractWork thesis = this.genericWorkDao.findWorkById(2);
        
        List<AbstractWork> works = this.genericWorkDao.findWorksBeforeDate(firstJan2002);
        assertEquals(2, works.size());
        assertTrue(works.contains(article));
        assertTrue(works.contains(thesis));
    }
    
    public void testFindWorksBefore1999() throws Exception {
        GregorianCalendar gc = new GregorianCalendar(1999, Calendar.JANUARY, 1);
        Date date = gc.getTime();
        
        List<AbstractWork> works = this.genericWorkDao.findWorksBeforeDate(date);
        assertTrue(works.isEmpty());
                
    }
    
    public void testFindWorksAfter2002() throws Exception {
        GregorianCalendar gc = new GregorianCalendar(2002, Calendar.JANUARY, 1);
        Date firstJan2002 = gc.getTime();
        
        List<AbstractWork> works = this.genericWorkDao.findWorksAfterDate(firstJan2002);
        AbstractWork thesis = this.genericWorkDao.findWorkById(2);
        AbstractWork chapter = this.genericWorkDao.findWorkById(4);
        assertEquals(2, works.size());
        assertTrue(works.contains(thesis));
        assertTrue(works.contains(chapter));
    }
    
    public void testFindWorksAfter2005() throws Exception {
        GregorianCalendar gc = new GregorianCalendar(2005, Calendar.JANUARY, 1);
        Date date = gc.getTime();
        
        List<AbstractWork> works = this.genericWorkDao.findWorksAfterDate(date);
        assertTrue(works.isEmpty());
    }
    
    public void testFindWorksAfter1999() throws Exception {
        GregorianCalendar gc = new GregorianCalendar(1999, Calendar.JANUARY, 1);
        Date date = gc.getTime();
        
        List<AbstractWork> works = this.genericWorkDao.findWorksAfterDate(date);
        assertEquals(4, works.size());
    }
    
    public void testFindWorksBetween1999And2002() throws Exception {
        GregorianCalendar cal99 = new GregorianCalendar(1999, Calendar.JANUARY, 1);
        GregorianCalendar cal02 = new GregorianCalendar(2002, Calendar.JANUARY, 1);
        
        List<AbstractWork> works = this.genericWorkDao.findWorksBetweenDates(cal99.getTime(), cal02.getTime());
        assertEquals(3, works.size());
        AbstractWork thesisAw = this.genericWorkDao.findWorkById(2);
        assertTrue(works.contains(thesisAw));
        AbstractWork articleAw = this.genericWorkDao.findWorkById(1);
        assertTrue(works.contains(articleAw));       
        AbstractWork bookAw = this.genericWorkDao.findWorkById(3);
        assertTrue(works.contains(bookAw));
    }
    
    public void testFindWorksBetween2002And2005() throws Exception {
        GregorianCalendar cal02 = new GregorianCalendar(2002, Calendar.JANUARY, 1);
        GregorianCalendar cal05 = new GregorianCalendar(2005, Calendar.JANUARY, 1);
        
        List<AbstractWork> works = this.genericWorkDao.findWorksBetweenDates(cal02.getTime(), cal05.getTime());
        assertEquals(3, works.size());
        AbstractWork thesisAw = this.genericWorkDao.findWorkById(2);
        assertTrue(works.contains(thesisAw));
        AbstractWork chapterAw = this.genericWorkDao.findWorkById(4);
        assertTrue(works.contains(chapterAw));       
        AbstractWork bookAw = this.genericWorkDao.findWorkById(3);
        assertTrue(works.contains(bookAw));
    }
}