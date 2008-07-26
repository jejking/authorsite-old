<?php
require_once ('types/shared/AbstractEntry.php');
require_once ('types/shared/Individual.php');
require_once ('types/shared/Collective.php');
abstract class AbstractWork extends AbstractEntry {

    const GET_WORKPRODUCERS_QUERY = 
        "SELECT wwp.workProducerType, h.id, h.createdAt, h.updatedAt, 
            h.DTYPE, h.name, h.nameQualification, h.givenNames, h.place
    	 FROM work_workproducers wwp, human h
    	 WHERE wwp.abstractHuman_id = h.id
    	 AND wwp.work_id = ?";
        
    const INSERT_ABSTRACT_WORK_QUERY = 
    	"INSERT INTO work (createdAt, updatedAt, version, toDate, date, title, updatedBy_id, createdBy_id)
		VALUES (now(), now(), 1, ?, ?, ?, ?, ?)";
    
    const DELETE_ABSTRACT_WORK_QUERY = 
    	"DELETE FROM work WHERE id = ?";
    
    const DELETE_WORK_PRODUCERS_QUERY = 
        "DELETE FROM work_workproducers
         WHERE Work_id = ?";
    
    const UPDATE_UPDATED_BY_ONLY_ABSTRACT_WORK_QUERY = 
        "UPDATE work SET updatedAt = now(), updatedBy_id = ? 
         WHERE id = ?";
    
    const UPDATE_TITLE_ABSTRACT_WORK_QUERY = 
        "UPDATE work SET updatedAt = now(), updatedBy_id = ?, title = ? 
             WHERE id = ?";
    
    const CREATE_WORK_WORKPRODUCER_RELATIONSHIP_QUERY = 
        "INSERT INTO work_workproducers (Work_id, workProducerType, abstractHuman_id) 
        VALUES (?, ?, ?)";
    
    public $title;
    public $fromDate;
    public $toDate;

    /**
     * Constructs instance.
     *
     * @param int $id
     * @param DateTime $createdAt
     * @param DateTime $updatedAt
     * @param name $title
     * @param DateTime $fromDate
     * @param DateTime $toDate
     */
    function __construct($id, $createdAt, $updatedAt, $title, $fromDate, $toDate) {
        parent::__construct($id, $createdAt, $updatedAt);
        $this->title = $title;
        $this->fromDate = $fromDate;
        $this->toDate = $toDate;
    }

    /**
     * Inserts base abstract work row.
     * 
     * The method should be invoked from a transaction initiated
     * in a subclass.
     *
     * @param AbstractWork $abstractEntry
     * @param Individual $user
     * @param PDO $db
     * @return int id generated
     */
    public static function insert($abstractEntry, $user, $db) {
        $params = array();
        if (is_null($abstractEntry->toDate)) {
            array_push($params, null);
        }
        else {
            array_push($params, date_format($abstractEntry->toDate, 'Y-m-d'));    
        }
        
        if (is_null($abstractEntry->fromDate)) {
            array_push($params, null);
        }
        else {
            array_push($params, date_format($abstractEntry->fromDate, 'Y-m-d'));
        }
        
        array_push($params, $abstractEntry->title);
        array_push($params, $user->id);
        array_push($params, $user->id);
        $id = AbstractEntry::doInsertWithMultipleParameters(AbstractWork::INSERT_ABSTRACT_WORK_QUERY, $params, $db);
        return $id;
    }
    
    /**
     * Enter description here...
     *
     * @param int  $id
     * @param int $updatedById
     * @param PDO $db
     */
    public static function update($id, $updatedById, $db) {
        $stmt = $db->prepare(AbstractWork::UPDATE_UPDATED_BY_ONLY_ABSTRACT_WORK_QUERY);
        $stmt->bindValue(1, $updatedById, PDO::PARAM_INT);
        $stmt->bindValue(2, $id, PDO::PARAM_INT);
        $stmt->execute();
    }
    
    /**
     * Updates title of entry, recording who did so and when.
     *
     * @param int $id
     * @param int $updatedById
     * @param string $title
     * @param PDO $db
     */
    public static function updateTitle($id, $updatedById, $title, $db)
    {
        $stmt = $db->prepare(AbstractWork::UPDATE_TITLE_ABSTRACT_WORK_QUERY);
        $stmt->bindValue(1, $updatedById, PDO::PARAM_INT);
        $stmt->bindValue(2, $title, PDO::PARAM_STR);
        $stmt->bindValue(3, $id, PDO::PARAM_INT);
        $stmt->execute();
    }
    
    /**
     * Deletes the abstract work row. You must make sure that
     * any "child" tables have been deleted before calling
     * this function.
     *
     * @param int $id
     * @param PDO $db
     */
    public static function delete($id, $db) {
        AbstractEntry::doDeleteQuery(AbstractWork::DELETE_WORK_PRODUCERS_QUERY, $id, $db);
        AbstractEntry::doDeleteQuery(AbstractWork::DELETE_ABSTRACT_WORK_QUERY, $id, $db);
    }
 
    /**
     * Retrieves the work producers and their relationships 
     * for a given work.
     * 
     * @param integer $workId
     * @param dbConnection $db
     * @return array keyed on workproducer type. The array contains in turn an array of AbstractHuman instances.
     */
    protected static function getWorkProducersForWork($workId, $db) {
        $resultSet = AbstractEntry::doQueryWithIdParameter(AbstractWork::GET_WORKPRODUCERS_QUERY, $workId, $db);
        $resultArray = array();
        foreach ($resultSet as $resultSetRow) {
            $workProducerType = $resultSetRow['workProducerType'];
            if (!array_key_exists($workProducerType, $resultArray)) {
                $resultArray[$workProducerType] = array();
            }
            $workProducerTypeArray = $resultArray[$workProducerType];
            $human = AbstractWork::buildHumanFromResultSetRow($resultSetRow);
            array_push($workProducerTypeArray, $human);
            $resultArray[$workProducerType] = $workProducerTypeArray;
         }
        return $resultArray;
    }
    
    private static function buildHumanFromResultSetRow($resultSetRow) {
        $id = $resultSetRow['id'];
        $createdAt = new DateTime($resultSetRow['createdAt']);
        $updatedAt = new DateTime($resultSetRow['updatedAt']);
        $type = $resultSetRow['DTYPE'];
        $name = $resultSetRow['name'];
        $nameQualification = $resultSetRow['nameQualification'];
        $givenNames = $resultSetRow['givenNames'];
        $place = $resultSetRow['place'];
        if ($type == 'Individual') {
            return new Individual($id, $createdAt, $updatedAt, $name, $nameQualification, $givenNames);
        }
        else {
            return new Collective($id, $createdAt, $updatedAt, $name, $nameQualification, $place);
        }
    }
    
    /**
     * Creates a work-work producer relationship.
     *
     * @param int $workId
     * @param int $producerId (individual or collective)
     * @param string $relationship
     * @param PDO $db
     */
    protected static function createWorkWorkProducerRelationship($workId, $producerId, $relationship, $db) {
        $params = array();
        array_push($params, $workId);
        array_push($params, $relationship);
        array_push($params, $producerId);
        AbstractEntry::doInsertWithMultipleParameters(AbstractWork::CREATE_WORK_WORKPRODUCER_RELATIONSHIP_QUERY, $params, $db);
    }

}
?>