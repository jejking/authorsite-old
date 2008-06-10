<?php
require_once('AbstractAuthoredEditedPublishedWork.php');

final class ExternalWebResource extends AbstractAuthoredEditedPublishedWork {
    
    const GET_WEBRESOURCES_CORE_QUERY = 
        'SELECT wr.id, wr.url, wr.lastChecked, wr.lastStatusCode, w.date, w.toDate, w.title
		 FROM webresource wr, work w
		 WHERE wr.id = w.id
		 ORDER BY w.title ASC';
    
    const GET_SINGLE_WEBRESOURCE_CORE_QUREY = 
        'SELECT wr.id, wr.url, wr.lastChecked, wr.lastStatusCode, w.date, w.toDate, w.title
		 FROM webresource wr, work w
		 WHERE wr.id = w.id
		 AND wr.id = ?';
    
    
    public $url;
    public $lastChecked;
    public $lastStatusCode;
    
    function __construct($id, $title, $fromDate, $toDate, $authors, $editors, $publisher, $url, $lastChecked, $lastStatusCode) {
        parent::__construct($id, $title, $fromDate, $toDate, $authors, $editors, $publisher);
        $this->url = $url;
        $this->lastChecked = $lastChecked;
        $this->lastStatusCode = $lastStatusCode;
    }
    
    static function count($db) {
        return AbstractEntry::doCount("webresource", $db);
    }
    
    static function getPage($pageNumber, $pageSize, $db) {
        $coreResultSet = AbstractEntry::doPagingQuery(ExternalWebResource::GET_WEBRESOURCES_CORE_QUERY, $pageNumber, $pageSize, $db);
        $resultArray = array();
        foreach ($coreResultSet as $coreResultSetRow) {
            $workProducers = AbstractWork::getWorkProducersForWork($coreResultSetRow['id'], $db);
            $webResource = ExternalWebResource::buildWebResource($coreResultSetRow, $workProducers);
            array_push($resultArray, $webResource);
        }
        return $resultArray;
    }
    
    static function getAll($db) {
        $coreResultSet = AbstractEntry::doSimpleQuery(WebResource::GET_WEBRESOURCES_CORE_QUERY, $db);
        $resultArray = array();
        foreach ($coreResultSet as $coreResultSetRow) {
            $workProducers = AbstractWork::getWorkProducersForWork($coreResultSetRow['id'], $db);
            $webResource = WebResource::buildWebResource($coreResultSetRow, $workProducers);
            array_push($resultArray, $webResource);
        }
        return $resultArray;
    }
    
    static function get($id, $db) {
        $coreResultSet = AbstractEntry::doQueryWithIdParameter(WebResource::GET_SINGLE_WEBRESOURCE_CORE_QUREY, $id, $db);
        if (count($coreResultSet) == 0) {
            return null;
        }
        $workProducers = AbstractWork::getWorkProducersForWork($id, $db);
        $coreResultSetRow = $coreResultSet[0];
        return WebResource::buildWebResource($coreResultSetRow, $workProducers);
    }
    
    private static function buildWebResource($coreResultSetRow, $workProducers) {
        $id = $coreResultSetRow['id'];
        $title = $coreResultSetRow['title'];
        $fromDate = $coreResultSetRow['date'];
        $toDate = $coreResultSetRow['toDate'];
        
        $url = $coreResultSetRow['url'];
        $lastChecked = $coreResultSetRow['lastChecked'];
        $lastStatusCode = $coreResultSetRow['lastStatusCode'];
        $authors = $workProducers[Constants::AUTHOR];
        $editors = $workProducers[Constants::EDITOR];
        $publishers = $workProducers[Constants::PUBLISHER];
        $publisher = $publishers[0];
        
        return new ExternalWebResource($id, $title, $fromDate, $toDate, $authors, $editors, $publisher, $url, $lastChecked, $lastStatusCode);
        
    }
}
?>