<?php
require_once('AbstractAuthoredEditedPublishedWork.php');
require_once('Constants.php');
final class Book extends AbstractAuthoredEditedPublishedWork {
    
    const GET_BOOKS_CORE_QUERY = 
    	'SELECT b.id, w.date, w.toDate, w.title
		 FROM book b, work w
		 WHERE b.id = w.id
		 ORDER BY w.title ASC';
    
    const GET_SINGLE_ARTICLE_CORE_QUERY = 
         'SELECT b.id, b.date, b.toDate, b.title
		 FROM book b, work w
		 WHERE b.id = w.id
    	 and b.id = ?';
    
    function __construct($id, $title, $fromDate, $toDate, $authors, $editors, $publisher) {
        parent::__construct($id, $title, $fromDate, $toDate, $authors, $editors, $publisher);
    }
    
    static function count($db) {
        return AbstractEntry::doCount("book", $db);
    }

    static function getPage($pageNumber, $pageSize, $db) {
        $coreResultSet = AbstractEntry::doPagingQuery(Book::GET_BOOKS_CORE_QUERY, $pageNumber, $pageSize, $db);
        $resultArray = array();
        foreach ($coreResultSet as $coreResultSetRow) {
            $workProducers = AbstractWork::getWorkProducersForWork($coreResultSetRow['id'], $db);
            $book = Book::buildBook($coreResultSetRow, $workProducers);
            array_push($resultArray, $book);
        }
        return $resultArray;
    }

    static function get($id, $db) {
        $coreResultSet = AbstractEntry::doQueryWithIdParameter(Book::GET_SINGLE_BOOK_CORE_QUERY, $id, $db);
        if (count($coreResultSet) == 0) {
            return null;
        }
        $workProducers = AbstractWork::getWorkProducersForWork($id, $db);
        $coreResultSetRow = $coreResultSet[0];
        return Book::buildBook($coreResultSetRow, $workProducers);
    }

    static function getAll($db) {
        $coreResultSet = AbstractEntry::doSimpleQuery(Book::GET_BOOKS_CORE_QUERY, $db);
        $resultArray = array();
        foreach ($coreResultSet as $coreResultSetRow) {
            $workProducers = AbstractWork::getWorkProducersForWork($coreResultSetRow['id'], $db);
            $book = Book::buildBook($coreResultSetRow, $workProducers);
            array_push($resultArray, $article);
        }
        return $resultArray;
    }
    
    private static function buildBook($coreResultSetRow, $workProducers) {
        $id = $coreResultSetRow['id'];
        $title = $coreResultSetRow['title'];
        $fromDate = $coreResultSetRow['date'];
        $toDate = $coreResultSetRow['toDate'];
        $authors = $workProducers[Constants::AUTHOR];
        $editors = $workProducers[Constants::EDITOR];
        $publishers = $workProducers[Constants::PUBLISHER];
        $publisher = $publishers[0];
        return new Book($id, $title, $fromDate, $toDate, $authors, $editors, $publisher);
    }
}
?>