<?php
require_once('types/shared/AbstractWork.php');
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
		 AND tc.content_name = ?";
    
    const INSERT_TEXT_CONTENT_QUERY = 
        "INSERT INTO content (id, DTYPE, content_name, mime_type, text_content)
		 VALUES (?, ?, ?, ?, ?)";
    
    const DELETE_TEXT_CONTENT_QUERY = 
        "DELETE FROM content WHERE id = ?";
    
    public $content_name;
    public $mimeType;
    public $content;
    public $author;

    /**
     * Constructs instance.
     *
     * @param int $id
     * @param DateTime $createdAt
     * @param DateTime $updatedAt
     * @param name $title
     * @param DateTime $fromDate
     * @param DateTime $toDate
     * @param string $content_name
     * @param string $mimeType
     * @param string $content
     * @param Individual $author
     */
    function __construct($id, $createdAt, $updatedAt, $title, $fromDate, $toDate, $content_name, $mimeType, $content, $author) {
        
        parent::__construct($id, $createdAt, $updatedAt, $title, $fromDate, $toDate);
        $this->content_name = $content_name;
        $this->mimeType = $mimeType;
        $this->content = $content;
        $this->author = $author;
        
    }
    
    static function getDefaultIndex() {
        
        return new TextContent(0, new DateTime(), new DateTime(), 'index', new DateTime(), new DateTime(), 
            'index', 'text/plain', 'Default content holder. Login to add content',
             new Individual(0, new DateTime(), new DateTime(), 'Root', null, null));
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
        $coreResultSet = AbstractEntry::doQueryWithSingleParamter(TextContent::GET_SINGLE_TEXT_CONTENT_BY_NAME_CORE_QUERY, $name, $db);
        if (count($coreResultSet) == 0) {
            return null;
        }
        $coreResultSetRow = $coreResultSet[0];
        $workProducers = AbstractWork::getWorkProducersForWork($coreResultSetRow['id'], $db);
        
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
    
    /**
     * Inserts new text content.
     *
     * @param TextContent $textContent
     * @param Individual $user
     * @param PDO $db
     * @return int
     */
    static function insert($textContent, $user, $db) {
        
        $db->beginTransaction();
        
        $id = AbstractWork::insert($textContent, $user, $db);
        $params = array();
        array_push($params, $id);
        array_push($params, "TextContent");
        array_push($params, $textContent->content_name);
        array_push($params, $textContent->mimeType);
        array_push($params, $textContent->content);
        
        AbstractEntry::doInsertWithMultipleParameters(TextContent::INSERT_TEXT_CONTENT_QUERY, $params, $db);
        
        AbstractWork::createWorkWorkProducerRelationship($id, $user->id, Constants::AUTHOR, $db);
        
        $db->commit();
        
        $textContent->id = $id;
        return $id;
    }
    
    /**
     * Deletes specified text content.
     *
     * @param int $id
     * @param PDO $db
     */
    static function delete($id, $db) {
        $db->beginTransaction();
        
        AbstractEntry::doDeleteQuery(TextContent::DELETE_TEXT_CONTENT_QUERY, $id, $db);
        AbstractEntry::delete($id, $db);
        
        $db->commit();
    }
    
    /**
     * Specifies if the content is safe to delete.
     *
     * @param int $id
     * @param PDO $db
     */
    static function isSafeToDelete($id, $db) {
        return true;
    }

    private static function buildTextContent($coreResultSetRow, $workProducers) {
        $authors = $workProducers[Constants::AUTHOR];
        $author = $authors[0];
        
        $id = $coreResultSetRow['id'];
        $title = $coreResultSetRow['title'];
        $content_name = $coreResultSetRow['content_name'];
        $mimeType = $coreResultSetRow['mime_type'];
        $content = $coreResultSetRow['text_content'];
        
        $fromDate = $coreResultSetRow['date'];
        $toDate = $coreResultSetRow['toDate'];
        
        $textContent = new TextContent($id, $title, $fromDate, $toDate, $content_name, $mimeType, $content, $author);
        return $textContent;
    }
    
}
?>