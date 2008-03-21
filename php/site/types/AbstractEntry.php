<?php
/**
 * Super class for all entry types.
 *
 * @author jejking
 */
abstract class AbstractEntry {

    const BASE_COUNT_QUERY = 'select count(*) from ';
    const PAGE_SIZE = 20;

    public $id;

    function __construct($id) {
        $this->id = $id;
    }

    abstract static function count($db);

    abstract static function getPage($pageNumber, $pageSize, $db);

    abstract static function get($id, $db);

    abstract static function getAll($db);

    /**
     * Performs count of the entities held in the named table
     *
     * @param string $tableName table to do simple count on
     * @param database connection $db a database connection
     * @return integer count
     */
    protected static function doCount($tableName, $db) {

        $queryString = AbstractEntry::BASE_COUNT_QUERY . $tableName;
        $stmt = $db->prepare($queryString);
        $stmt->execute();
        $result = $stmt->fetch(PDO::FETCH_BOTH);
        $stmt = null;
        $count = $result[0];
        return $count;
    }

    /**
     * Performs count of entites in the named table
     * applying condition by text concatenation.
     *
     * @param string $tableName
     * @param string $condition
     * @param db connection $db
     * @return integer count
     */
    protected function doCountWithCondition($tableName, $condition, $db) {

        $queryString = AbstractEntry::BASE_COUNT_QUERY . $tableName . $condition;
        $stmt = $db->prepare($queryString);
        $stmt->execute();
        $result = $stmt->fetch(PDO::FETCH_BOTH);
        $stmt = null;
        $count = $result[0];
        return $count;

    }

    /**
     * Executes specified <em>simple</em> query. The query
     * should have no params to bind.
     *
     * @param string $queryString a valid mysql query with no params to bind
     * @param int $pageNumber
     * @param int $pageSize
     * @param db connection $db
     * @return result set
     */
    protected static function doPagingQuery($queryString, $pageNumber, $pageSize, $db) {

        $queryString = $queryString . ' LIMIT ?, ?';
        $stmt = $db->prepare($queryString);
        $stmt->bindValue(1, $pageNumber * $pageSize, PDO::PARAM_INT);
        $stmt->bindValue(2, $pageSize, PDO::PARAM_INT);
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_BOTH);
        return $result;
    }
    
    /**
     * Executes specified simple query and returns the 
     * result set.
     * 
     * @param string $queryString
     * @param db connection $db
     * @return result set
     */
    protected function doSimpleQuery($queryString, $db) {

        $stmt = $db->prepare($queryString);
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_BOTH);
        return $result;
    }
    
    /**
     * Executes specified query which must have an 
     * integer placeholder for an ID.
     * 
     * @param string $queryString
     * @param integer $id
     * @param db connection $db
     * @return result set, probably just with one row
     */
    protected function doQueryWithIdParameter($queryString, $id, $db) {

        $stmt = $db->prepare($queryString);
        $stmt->bindValue(1, $id);
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_BOTH);
        return $result;
    }
}
?>