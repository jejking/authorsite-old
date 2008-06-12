<?php
require_once('types/bib/AbstractAuthoredEditedWork.php');
require_once('types/bib/Book.php');
require_once('Constants.php');
final class Chapter extends AbstractAuthoredEditedWork {
    
    public $book;
    public $chapter;
    
    const GET_CHAPTERS_CORE_QUERY = 
    	'SELECT c.id, c.pages, c.chapter, wc.date, wc.toDate, wc.title AS chapter_title, 
    		wb.id as book_id, wb.title AS book_title, wb.date AS book_date, wb.toDate AS book_toDate
		 FROM chapter c, work wc, work wb
		 WHERE c.id = wc.id
		 AND c.book_id = wb.id
		 ORDER BY wc.title ASC';
    
    const GET_SINGLE_CHAPTER_CORE_QUERY = 
         'SELECT c.id, c.pages, c.chapter, wc.date, wc.toDate, wc.title AS chapter_title, 
    		wb.id as book_id, wb.title AS book_title, wb.date AS book_date, wb.toDate AS book_toDate
		 FROM chapter c, work wc, work wb
		 WHERE c.id = wc.id
		 AND c.book_id = wb.id
		 AND c.id = ?';
    
    function __construct($id, $title, $fromDate, $toDate, $authors, $editors, $book, $chapter) {
        parent::__construct($id, $title, $fromDate, $toDate, $authors, $editors);
        $this->book = $book;
        $this->chapter = $chapter;
    }
    
    static function count($db) {
        return AbstractEntry::doCount("chapter", $db);
    }
    
    static function getPage($pageNumber, $pageSize, $db) {
        $coreResultSet = AbstractEntry::doPagingQuery(Chapter::GET_CHAPTERS_CORE_QUERY, $pageNumber, $pageSize, $db);
        $resultArray = array();
        foreach ($coreResultSet as $coreResultSetRow) {
            $chapterWorkProducers = AbstractWork::getWorkProducersForWork($coreResultSetRow['id'], $db);
            $bookWorkProducers = AbstractWork::getWorkProducersForWork($coreResultSet['book_id'], $db);
            $chapter = Chapter::buildChapter($coreResultSetRow, $bookWorkProducers, $chapterWorkProducers);
            array_push($resultArray, $chapter);
        }
        return $resultArray;
    }

    static function get($id, $db) {
        $coreResultSet = AbstractEntry::doQueryWithIdParameter(Chapter::GET_SINGLE_CHAPTER_CORE_QUERY, $id, $db);
        if (count($coreResultSet) == 0) {
            return null;
        }
        $resultSetRow = $coreResultSet[0];
        $bookWorkProducers = AbstractWork::getWorkProducersForWork($resultSetRow['book_id'], $db);
        $chapterWorkProducers = AbstractWork::getWorkProducersForWork($id, $db);
        return Chapter::buildChapter($resultSetRow, $bookWorkProducers, $chapterWorkProducers);
    }
    
    static function getAll($db) {
        $coreResultSet = AbstractEntry::doSimpleQuery(Chapter::GET_CHAPTERS_CORE_QUERY, $db);
        $resultArray = array();
        foreach ($coreResultSet as $coreResultSetRow) {
            $chapterWorkProducers = AbstractWork::getWorkProducersForWork($coreResultSetRow['id'], $db);
            $bookWorkProducers = AbstractWork::getWorkProducersForWork($coreResultSet['book_id'], $db);
            $chapter = Chapter::buildChapter($coreResultSetRow, $bookWorkProducers, $chapterWorkProducers);
            array_push($resultArray, $chapter);
        }
        return $resultArray;
    }
    
    static function insert($individual, $user, $db) {
        // TODO build
    }
    
    static function delete($id, $db) {
        // TODO
    }
    
    static function isSafeToDelete($id, $db) {
        // TODO
    }
    
    private static function buildChapter($coreResultSet, $bookWorkProducers, $chapterWorkProducers) {
        // book
        $bookId = $coreResultSet['book_id'];
        $bookTitle = $coreResultSet['book_title'];
        $bookFromDate = $coreResultSet['book_date'];
        $bookToDate = $coreResultSet['book_toDate'];
        $bookAuthors = $bookWorkProducers[Constants::AUTHOR];
        $bookEditors = $bookWorkProducers[Constants::EDITOR];
        $bookPublishers = $bookWorkProducers[constants::PUBLISHER];
        $bookPublisher = $bookPublishers[0];
        $book = new Book($bookId, $bookTitle, $bookFromDate, $bookToDate, $bookAuthors, $bookEditors, $bookPublisher);
        
        $chapterId = $coreResultSet['id'];
        $chapterDesignator = $coreResultSet['chapter'];
        $chapterTitle = $coreResultSet['chapter_title'];
        $chapterFromDate = $coreResultSet['date'];
        $chapterToDate = $coreResultSet['toDate'];
        $chapterAuthors = $chapterWorkProducers[Constants::AUTHOR];
        $chapterEditors = $chapterWorkProducers[Constants::EDITOR];
        
        $chapter = new Chapter($chapterId, $chapterTitle, $chapterFromDate, $chapterToDate, $chapterAuthors, $chapterEditors, $book, $chapterDesignator);
        return $chapter;
    }
}
?>