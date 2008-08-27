<?php
require_once('types/bib/AbstractAuthoredEditedPublishedWork.php');
require_once('Constants.php');
/**
 * Class describing a book.
 *
 */
final class Book extends AbstractAuthoredEditedPublishedWork {
    
    const GET_BOOKS_CORE_QUERY = 
    	'SELECT b.id, w.createdAt, w.updatedAt, w.date, w.toDate, w.title
		 FROM book b, work w
		 WHERE b.id = w.id
		 ORDER BY w.title ASC';
    
    const GET_SINGLE_BOOK_CORE_QUERY = 
         'SELECT b.id, w.createdAt, w.updatedAt, w.date, w.toDate, w.title
		 FROM book b, work w
		 WHERE b.id = w.id
    	 and b.id = ?';
    
    const GET_CHAPTER_COUNT_QUERY = 
        'SELECT count(*) from chapter where book_id = ?';
    
    /**
     * Constructs new instance.
     *
     * @param integer $id
     * @param DateTime $createdAt
     * @param DateTime $updatedAt
     * @param string $title
     * @param DateTime $fromDate
     * @param DateTime $toDate
     * @param array $authors array of abstract humans
     * @param array $editors array of abstract humans
     * @param AbstractHuman $publisher
     */
    function __construct($id, $createdAt, $updatedAt, $title, $fromDate, $toDate, 
                    $authors, $editors, $publisher) {
        
        parent::__construct($id, $createdAt, $updatedAt, $title,
                $fromDate, $toDate, $authors, $editors, $publisher);
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

    /**
     * Gets the book with specified id or null.
     * @param int $id
     * @param DBO $db
     * @return Book
     */
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
            array_push($resultArray, $book);
        }
        return $resultArray;
    }
    
    public function getChaptersCount($db) {
        $resultSet = AbstractEntry::doQueryWithIdParameter(Book::GET_CHAPTER_COUNT_QUERY, $this->id, $db);
        $resultSetRow = $resultSet[0];
        return $resultSetRow[0];
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
    
    private static function buildBook($coreResultSetRow, $workProducers) {
        $id = $coreResultSetRow['id'];
        $createdAt = new DateTime($coreResultSetRow['createdAt']);
        $updatedAt = new DateTime($coreResultSetRow['updatedAt']);
        $title = $coreResultSetRow['title'];
        $fromDate = new DateTime($coreResultSetRow['date']);
        if (!is_null($coreResultSetRow['toDate'])) {
            $toDate = new DateTime($coreResultSetRow['toDate']);    
        }
        else {
            $toDate = null;     
        }
        $authors = $workProducers[Constants::AUTHOR];
        $editors = $workProducers[Constants::EDITOR];
        $publishers = $workProducers[Constants::PUBLISHER];
        $publisher = $publishers[0];
        return new Book($id, $createdAt, $updatedAt, $title, $fromDate, $toDate, 
            $authors, $editors, $publisher);
    }
}
?>