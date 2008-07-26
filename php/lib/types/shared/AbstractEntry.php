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
    
	/**
     * Date elemement created.
     *
     * @var DateTime
     */
    public $createdAt;
    
    public $formattedCreatedAt;
    
	/**
     * Date element last updated.
     *
     * @var DateTime
     */
    public $updatedAt;
    
    public $formattedUpdatedAt;
    
    /**
     * Constructs basic entry.
     *
     * @param int $id
     * @param DateTime $createdAt
     * @param DateTime $updatedAt
     */
    function __construct($id, $createdAt, $updatedAt) {
        $this->id = $id;
        $this->createdAt = $createdAt;
        $this->formattedCreatedAt = date_format($createdAt, "r");
        $this->updatedAt = $updatedAt;
        $this->formattedUpdatedAt = date_format($updatedAt, "r");
    }


    /**
     * Counts the entries of the type held in the database.
     *
     * @param PDO $db
     * @return int count
     */
    abstract static function count($db);

    /**
     * Gets page of entries of the type from the database.
     *
     * @param int $pageNumber
     * @param int $pageSize
     * @param PDO $db
     * @return array of AbstractEntry
     */
    abstract static function getPage($pageNumber, $pageSize, $db);

    /**
     * Gets specified entry of the type.
     *
     * @param int $id
     * @param PDO $db
     * @return AbstractEntry (or null)
     */
    abstract static function get($id, $db);

    /**
     * Gets all entries of the type in the database.
     *
     * @param PDO $db
     */
    abstract static function getAll($db);
    
    /**
     * Inserts new entry of the specified type into the database.
     * This may require several SQL statements. Implementations 
     * should be transactional.
     *
     * @param AbstractEntry $abstractEntry
     * @param Individual $user
     * @param PDO $db
     */
    abstract static function insert($abstractEntry, $user, $db);
    
    /**
     * Deletes the specified entry from the database. This may
     * require several SQL statements. Implementations 
     * should be transactional.
     *
     * @param int $id
     * @param PDO $db
     */
    abstract static function delete($id, $db);
    
    /**
     * Verifies if a given entry has dependent children in
     * a foreign-key relationship in the database such that
     * if one were to delete the entry, a PDO Exception caused
     * by the constraint violation would be thrown.
     *
     * @param int $id
     * @param PDO $db
     * @return boolean
     */
    abstract static function isSafeToDelete($id, $db);
    
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
    protected static function doSimpleQuery($queryString, $db) {

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
    protected static function doQueryWithIdParameter($queryString, $id, $db) {

        $stmt = $db->prepare($queryString);
        $stmt->bindValue(1, $id, PDO::PARAM_INT);
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_BOTH);
        return $result;
    }
    
    protected static function doQueryWithSingleParamter($queryString, $param, $db) {
        $stmt = $db->prepare($queryString);
        $stmt->bindValue(1, $param);
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_BOTH);
        return $result;
    }
    
    protected static function doQueryWithMultipleParameters($querystring, $paramArray, $db) {
        $stmt = $db->prepare($querystring);
        $i = 1;
        foreach ($params as $param) {
          $stmt->bindValue($i, $param);
          $i++;
        }
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_BOTH);
        return $result;
    }
    
    protected static function doUpdateQuery($queryString, $params, $db) {
        $stmt = $db->prepare($querystring);
        $i = 1;
        foreach ($params as $param) {
          $stmt->bindValue($i, $param);
          $i++;
        }
        $stmt->execute();
    }
    
    /**
     * Executes database update having bound params in array to the prepared statement.
     *
     * @param String $queryString
     * @param array $paramArray
     * @param PDO $db
     * @return int last insert ID
     */
    protected static function doInsertWithMultipleParameters($queryString, $paramArray, $db) {
        $stmt = $db->prepare($queryString);
        $i = 1;
        foreach ($paramArray as $param) {
          $stmt->bindValue($i, $param);
          $i++;
        }
        $stmt->execute();
        return $db->lastInsertId();
    }
    
    /**
     * Executes a delete statement which should be parameterised with
     * the id of the element to delete from the database.
     *
     * @param string $queryString
     * @param int $id
     * @param PDO $db
     */
    protected static function doDeleteQuery($queryString, $id, $db) {
        $stmt = $db->prepare($queryString);
        $stmt->bindValue(1, $id, PDO::PARAM_INT);
        $stmt->execute();
    }
    

}
?>