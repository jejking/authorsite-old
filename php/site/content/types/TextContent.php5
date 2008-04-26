<?php
include_once('../shared/types/AbstractWork.php');
final class TextContent extends AbstractWork {
    
    const GET_TEXT_CONTENT_CORE_QUERY = 
    	"SELECT tc.id, tc.content_name, tc.mime_type, text_content, w.date, w.toDate, w.title
		 FROM content tc, work w
		 WHERE tc.id = w.id
		 AND tc.DTYPE = 'TextContent'
		 ORDER BY w.title ASC";
    
    const GET_SINGLE_TEXT_CONTENT_CORE_QUERY = 
         "SELECT tc.id, tc.content_name, tc.mime_type, text_content, w.date, w.toDate, w.title
		 FROM content tc, work w
		 WHERE tc.id = w.id
		 AND tc.DTYPE = 'TextContent'
		 AND tc.id = ?";
    
    const GET_SINGLE_TEXT_CONTENT_BY_NAME_CORE_QUERY = 
         "SELECT tc.id, tc.content_name, tc.mime_type, text_content, w.date, w.toDate, w.title
		 FROM content tc, work w
		 WHERE tc.id = w.id
		 AND tc.DTYPE = 'TextContent'
		 AND tc.name = ?";
    
    public $content_name;
    public $mimeType;
    public $content;
    public $author;

    function __construct($id, $title, $fromDate, $toDate, $content_name, $mimeType, $content, $author) {
        
        parent::__construct($id, $title, $fromDate, $toDate);
        $this->content_name = $content_name;
        $this->mimeType = $mimeType;
        $this->content = $content;
        $this->author = $author;
        
    }
    
    static function getDefaultIndex() {
        
        return new TextContent(0, 'index', date("d-M-Y"), date("d-M-Y"), 
            'index', 'text/plain', 'Default content holder. Login to add content',
             new Individual(0, 'Root', null, null));
    }
    
    static function count($db) {
        return AbstractEntry::doCountWithCondition("content"," where DTYPE = 'TextContent'",  $db);
    }

    static function getPage($pageNumber, $pageSize, $db) {
        $coreResultSet = AbstractEntry::doPagingQuery(TextContent::GET_SINGLE_TEXT_CONTENT_CORE_QUERY, $pageNumber, $pageSize, $db);
        if (count($coreResultSet) == 0) {
            return null;
        }
        $workProducers = AbstractWork::getWorkProducersForWork($id, $db);
        $coreResultSetRow = $coreResultSet[0];
        return TextContent::buildTextContent($coreResultSetRow, $workProducers);
    }
    
    static function getByName($name, $db) {
        $coreResultSet = AbstractEntry::doQueryWithSingleParamter(GET_SINGLE_TEXT_CONTENT_BY_NAME_CORE_QUERY, $name, $db);
        if (count($coreResultSet) == 0) {
            return null;
        }
        $workProducers = AbstractWork::getWorkProducersForWork($id, $db);
        $coreResultSetRow = $coreResultSet[0];
        return TextContent::buildTextContent($coreResultSetRow, $workProducers);
    }

    static function get($id, $db) {
        $coreResultSet = AbstractEntry::doQueryWithIdParameter(TextContent::GET_SINGLE_TEXT_CONTENT_CORE_QUERY, $id, $db);
        if (count($coreResultSet) == 0) {
            return null;
        }
        $workProducers = AbstractWork::getWorkProducersForWork($id, $db);
        $coreResultSetRow = $coreResultSet[0];
        return TextContent::buildTextContent($coreResultSetRow, $workProducers);
    }

    static function getAll($db) {
        $coreResultSet = AbstractEntry::doSimpleQuery(TextContent::GET_TEXT_CONTENT_CORE_QUERY, $db);
        $resultArray = array();
        foreach ($coreResultSet as $coreResultSetRow) {
            $workProducers = AbstractWork::getWorkProducersForWork($coreResultSetRow['id'], $db);
            $textContent = TextContent::buildTextContent($coreResultSetRow, $workProducers);
            array_push($resultArray, $textContent);
        }
        return $resultArray;
    }

    private static function buildTextContent($coreResultSetRow, $workProducers) {
        $authors = $workProducers[Constants::AUTHOR];
        $author = $authors[0];
        
        $id = $coreResultSetRow['id'];
        $content_name = $coreResultSetRow['content_name'];
        $mimeType = $coreResultSetRow['mimeType'];
        $content = $coreResultSetRow['text_content'];
        
        $fromDate = $coreResultSetRow['date'];
        $toDate = $coreResultSetRow['toDate'];
        
        $textContent = new TextContent($id, $title, $fromDate, $toDate, $content_name, $mimeType, $content, $author);
    }
    
}
?>