<?php
require_once ('AbstractWork.php');
final class Journal extends AbstractWork {
    
    const GET_SINGLE_JOURNAL_QUERY = 
    	"SELECT w.id, w.title, w.date, w.toDate
    	 FROM work w, journal j 
    	 WHERE w.id = j.id 
    	 AND j.id = ?";
    
    const BROWSE_JOURNALS_QUERY = 
        "SELECT w.id, w.title, w.date, w.toDate
    	 FROM work w, journal j 
    	 WHERE w.id = j.id
    	 ORDER BY title ASC";
    
    const COUNT_ARTICLES_QUERY = 
        "SELECT count(*) 
        FROM article a
        WHERE a.journal_id = ?";
    
    function __construct($id, $title, $fromDate, $toDate) {
        parent::__construct($id, $title, $fromDate, $toDate);
    }
   
   
    static function count($db) {
        return AbstractEntry::doCount('journal', $db);
    }

    static function getPage($pageNumber, $pageSize, $db) {
        $resultSet = AbstractEntry::doPagingQuery(Journal::BROWSE_JOURNALS_QUERY, $pageNumber, $pageSize, $db);
        $resultArray = array();
        foreach ($resultSet as $resultSetRow) {
            $journal = Journal::buildJournal($resultSetRow);
            array_push($resultArray, $journal);
        }
        return $resultArray;
    }

    static function get($id, $db) {
        $resultSet = AbstractEntry::doQueryWithIdParameter(Journal::GET_SINGLE_JOURNAL_QUERY, $id, $db);
        if (count($resultSet) == 0) {
            return null;
        }
        return Journal::buildJournal($resultSet[0]);
    }

    static function getAll($db) {
        $resultSet = AbstractEntry::doSimpleQuery(Journal::BROWSE_JOURNALS_QUERY, $db);
        $resultArray = array();
        foreach ($resultSet as $resultSetRow) {
            $journal = Journal::buildJournal($resultSetRow);
            array_push($resultArray, $journal);
        }
        return $resultArray;
    }
    
    /**
     * Counts articles which are known to be
     * published in the journal.
     * 
     * @param dbConnection $db
     * @return integer count
     */
    function getArticlesCount($db) {
        $resultSet = AbstractEntry::doQueryWithIdParameter(Journal::COUNT_ARTICLES_QUERY, $this->id, $db);
        $resultSetRow = $resultSet[0];
        return $resultSetRow[0];
    }
    
    private static function buildJournal($resultSetRow) {
        $id = $resultSetRow['id'];
        $title = $resultSetRow['title'];
        $fromDate = $resultSetRow['date'];
        $toDate = $resultSetRow['toDate'];
        return new Journal($id, $title, $fromDate, $toDate);
    }
}
?>