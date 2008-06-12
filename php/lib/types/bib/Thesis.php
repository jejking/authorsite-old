<?php
require_once('types/shared/AbstractWork.php');

final class Thesis extends AbstractWork {
    
    const GET_THESES_CORE_QUERY = 
        'SELECT t.id, t.degree, w.date, w.toDate, w.title
		 FROM thesis t, work w
		 WHERE t.id = w.id
		 ORDER BY w.title ASC';
    
    const GET_SINGLE_THESIS_CORE_QUREY = 
        'SELECT t.id, t.degree, w.date, w.toDate, w.title
		 FROM thesis t, work w
		 WHERE t.id = w.id
		 AND t.id = ?';
    
    public $author;
    public $awardingBody;
    public $degree;
    
    function __construct($id, $title, $fromDate, $toDate, $author, $awardingBody, $degree) {
        parent::__construct($id, $title, $fromDate, $toDate);
        $this->author = $author;
        $this->awardingBody = $awardingBody; 
        $this->degree = $degree;
    }
    
    static function count($db) {
        return AbstractEntry::doCount("thesis", $db);
    }

    static function getPage($pageNumber, $pageSize, $db) {
        $coreResultSet = AbstractEntry::doPagingQuery(Thesis::GET_THESES_CORE_QUERY, $pageNumber, $pageSize, $db);
        $resultArray = array();
        foreach ($coreResultSet as $coreResultSetRow) {
            $workProducers = AbstractWork::getWorkProducersForWork($coreResultSetRow['id'], $db);
            $thesis = Thesis::buildThesis($coreResultSetRow, $workProducers);
            array_push($resultArray, $thesis);
        }
        return $resultArray;
    }

    static function get($id, $db) {
        $coreResultSet = AbstractEntry::doQueryWithIdParameter(Thesis::GET_SINGLE_THESIS_CORE_QUREY, $id, $db);
        if (count($coreResultSet) == 0) {
            return null;
        }
        $workProducers = AbstractWork::getWorkProducersForWork($id, $db);
        $coreResultSetRow = $coreResultSet[0];
        return Thesis::buildThesis($coreResultSetRow, $workProducers);
    }

    static function getAll($db) {
        $coreResultSet = AbstractEntry::doSimpleQuery(Thesis::GET_THESES_CORE_QUERY, $db);
        $resultArray = array();
        foreach ($coreResultSet as $coreResultSetRow) {
            $workProducers = AbstractWork::getWorkProducersForWork($coreResultSetRow['id'], $db);
            $thesis = Thesis::buildThesis($coreResultSetRow, $workProducers);
            array_push($resultArray, $thesis);
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
    
   
    private static function buildThesis($coreResultSetRow, $workProducers) {
        $id = $coreResultSetRow['id'];
        $title = $coreResultSetRow['title'];
        $fromDate = $coreResultSetRow['date'];
        $toDate = $coreResultSetRow['toDate'];
        
        $degree = $coreResultSetRow['degree'];
        $authors = $workProducers[Constants::AUTHOR];
        $author = $authors[0];
        
        $awardingBodies = $workProducers[Constants::AWARDING_BODY];
        $awardingBody = $awardingBodies[0];
        
        return new Thesis($id, $title, $fromDate, $toDate, $author, $awardingBody, $degree);
    }
}
?>