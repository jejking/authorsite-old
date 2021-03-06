<?php
require_once('types/shared/AbstractHuman.php');
final class Collective extends AbstractHuman {
    
    const BROWSE_COLLECTIVES_QUERY = 
    	"SELECT id, createdAt, updatedAt, name, nameQualification, place 
    	FROM human WHERE DTYPE = 'Collective' 
    	ORDER BY name, givennames, place DESC";
    
    const GET_COLLECTIVE_QUERY = 
        "SELECT id, createdAt, updatedAt, name, nameQualification, place 
        FROM human WHERE DTYPE = 'Collective' AND id = ?";
    
    /**
     * Place where a collective is nominally located.
     *
     * @var string
     */
    public $place;
    
    /**
     * Constructs collective instance.
     *
     * @param int $id
     * @param DateTime $createdAt
     * @param DateTime $updatedAt
     * @param string $name
     * @param string $nameQualification
     * @param string $place
     */
    function __construct($id, $createdAt, $updatedAt, $name, $nameQualification, $place) {
        parent::__construct($id, $createdAt, $updatedAt, $name, $nameQualification);
        $this->place = $place;
    }
    
    static function count($db) {
        return AbstractEntry::doCountWithCondition("human"," where DTYPE = 'Collective'",  $db);
    }
    
 	/* Gets page of collectives.
     * 
     * @param integer $pageNumber
     * @param integer $pageSize
     * @param db connection $db
     * @return array of Collective instances
     */
    static function getPage($pageNumber, $pageSize, $db) {
        $resultSet = AbstractEntry::doPagingQuery(Collective::BROWSE_COLLECTIVES_QUERY, $pageNumber, $pageSize, $db);
        return Collective::buildArrayOfCollectives($resultSet);
    }
    
    
    static function get($id, $db) {
        $resultSet = AbstractEntry::doQueryWithIdParameter(Collective::GET_COLLECTIVE_QUERY, $id, $db);
        if (count($resultSet) == 0) {
            return null;
        }
        $collective = Collective::buildCollective($resultSet[0]);
        return $collective;
    }
    
    static function getAll($db) {
        $resultSet = $resultSet = AbstractEntry::doSimpleQuery(Collective::BROWSE_COLLECTIVES_QUERY, $db);
        return Collective::buildArrayOfCollectives($resultSet);
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
    
    private static function buildArrayOfCollectives($resultSet) {
        $collectives = array();
        foreach ($resultSet as $resultSetRow) {
            $collective = Collective::buildCollective($resultSetRow);
            array_push($collectives, $collective);
        }
        return $collectives;
    }
    
    private static function buildCollective($resultSetRow) {
        $id = $resultSetRow['id'];
        $createdAt = new DateTime($resultSetRow['createdAt']);
        $updatedAt = new DateTime($resultSetRow['updatedAt']);
        $name = $resultSetRow['name'];
        $nameQualification = $resultSetRow['nameQualification'];
        $place = $resultSetRow['place'];
        $collective = new Collective($id, $createdAt, $updatedAt, $name, $nameQualification, $place);
        return $collective;
    }
}
?>