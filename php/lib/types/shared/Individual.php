<?php
require_once 'types/shared/AbstractHuman.php';

final class Individual extends AbstractHuman {
    
    const BROWSE_INDIVIDUALS_QUERY = 
    	"SELECT id, createdAt, updatedAt, name, nameQualification, givennames 
    	FROM human WHERE DTYPE = 'Individual' 
    	ORDER BY name, givennames, nameQualification DESC";
    
    const GET_INDIVIDUAL_QUERY = 
    	"SELECT id, createdAt, updatedAt, name, nameQualification, givennames 
    	FROM human WHERE DTYPE = 'Individual' AND id = ?";
    
    /**
     * Given names.
     *
     * @var string
     */
    public $givenNames;
    
    /**
     * Constructs instance.
     *
     * @param int $id
     * @param DateTime $createdAt
     * @param DateTime $updatedAt
     * @param string $name
     * @param string $nameQualification
     * @param string $givenNames
     */
    function  __construct($id, $createdAt, $updatedAt, $name, $nameQualification, $givenNames) {
        parent::__construct($id, $createdAt, $updatedAt, $name, $nameQualification);
        $this->givenNames = $givenNames;
    }
    
    /**
     * Counts the number of Individuals in the database.
     * 
     * @param db connection $db
     * @return integer the count of individuals
     */
    static function count($db) {
        return AbstractEntry::doCountWithCondition("human"," where DTYPE = 'Individual'",  $db);
    }
    
    /**
     * Gets page of individuals.
     * 
     * @param integer $pageNumber
     * @param integer $pageSize
     * @param db connection $db
     * @return array of Individual instances
     */
    static function getPage($pageNumber, $pageSize, $db) {
        $resultSet = AbstractEntry::doPagingQuery(Individual::BROWSE_INDIVIDUALS_QUERY, $pageNumber, $pageSize, $db);
        return Individual::buildArrayOfIndividuals($resultSet);
    }
    
    
    static function get($id, $db) {
        $resultSet = AbstractEntry::doQueryWithIdParameter(Individual::GET_INDIVIDUAL_QUERY, $id, $db);
        if (count($resultSet) == 0) {
            return null;
        }
        $individual = Individual::buildIndividual($resultSet[0]);
        return $individual;
    }
    
    static function getAll($db) {
        $resultSet = $resultSet = AbstractEntry::doSimpleQuery(Individual::BROWSE_INDIVIDUALS_QUERY, $db);
        return Individual::buildArrayOfIndividuals($resultSet);
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
    
    private static function buildArrayOfIndividuals($resultSet) {
        $individuals = array();
        foreach ($resultSet as $resultSetRow) {
            $individual = Individual::buildIndividual($resultSetRow);
            array_push($individuals, $individual);
        }
        return $individuals;
    }
    
    private static function buildIndividual($resultSetRow) {
        $id = $resultSetRow['id'];
        $createdAt = new DateTime($resultSetRow['createdAt']);
        $updatedAt = new DateTime($resultSetRow['updatedAt']);
        $name = $resultSetRow['name'];
        $nameQualification = $resultSetRow['nameQualification'];
        $givenNames = $resultSetRow['givennames'];
        $individual = new Individual($id, $createdAt, $updatedAt, $name, $nameQualification, $givenNames);
        return $individual;
    }
}
?>