<?php
require_once ('types/shared/AbstractEntry.php');
require_once ('types/shared/Individual.php');
require_once ('types/shared/Collective.php');
abstract class AbstractWork extends AbstractEntry {

    const GET_WORKPRODUCERS_QUERY = 
    "SELECT wwp.workProducerType, h.id, h.DTYPE, h.name, h.nameQualification, h.givenNames, h.place
	 FROM work_workproducers wwp, human h
	 WHERE wwp.abstractHuman_id = h.id
	 AND wwp.work_id = ?";
    
    const INSERT_ABSTRACT_WORK_QUERY = 
    	"INSERT INTO work (createdAt, updatedAt, version, toDate, date, title, updatedBy_id, createdBy_id)
		VALUES (now(), now(), 0, ?, ?, ?, ?, ?)";
    
    const DELETE_ABSTRACT_WORK_QUERY = 
    	"DELETE FROM work WHERE id = ?";
    
    const CREATE_WORK_WORKPRODUCER_RELATIONSHIP_QUERY = 
        "INSERT INTO work_workproducers (Work_id, workProducerType, abstractHuman_id) 
        VALUES (?, ?, ?)";
    
    public $title;
    public $fromDate;
    public $toDate;

    function __construct($id, $title, $fromDate, $toDate) {
        parent::__construct($id);
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
     * Deletes the abstract work row. You must make sure that
     * any "child" tables have been deleted before calling
     * this function.
     *
     * @param int $id
     * @param PDO $db
     */
    public static function delete($id, $db) {
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
        $type = $resultSetRow['DTYPE'];
        $name = $resultSetRow['name'];
        $nameQualification = $resultSetRow['nameQualification'];
        $givenNames = $resultSetRow['givenNames'];
        $place = $resultSetRow['place'];
        if ($type == 'Individual') {
            return new Individual($id, $name, $nameQualification, $givenNames);
        }
        else {
            return new Collective($id, $name, $nameQualification, $place);
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