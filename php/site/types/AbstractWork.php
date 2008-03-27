<?php
require_once ('AbstractEntry.php');
require_once ('Individual.php');
require_once ('Collective.php');
abstract class AbstractWork extends AbstractEntry {

    const GET_WORKPRODUCERS_QUERY = 
    "SELECT wwp.workProducerType, h.id, h.DTYPE, h.name, h.nameQualification, h.givenNames, h.place
	 FROM work_workproducers wwp, human h
	 WHERE wwp.abstractHuman_id = h.id
	 AND wwp.work_id = ?";
    
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

}
?>