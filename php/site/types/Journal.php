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
    	 WHERE w.id = j.id";
    
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
    
    private static function buildJournal($resultSetRow) {
        $id = $resultSetRow['id'];
        $title = $resultSetRow['title'];
        $fromDate = $resultSetRow['date'];
        $toDate = $resultSetRow['toDate'];
        return new Journal($id, $title, $fromDate, $toDate);
    }
}
?>