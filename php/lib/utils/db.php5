<?php
require('../conf/config.php5');

define('BASE_COUNT_QUERY', 'select count(*) from ');

define('PAGE_SIZE', 20);

/*
 * Returns a db connection
 */
function openDbConnection() {
  try
  {
    $db = new PDO(DB_URL, DB_USER, DB_PWD);
    $setNamesStmt = $db->prepare("SET NAMES 'utf8'");
    $setNamesStmt->execute();
    $setNamesStmt = null;
    $setCharSetStmt = $db->prepare("SET CHARACTER SET 'utf8'");
    $setCharSetStmt->execute();
    $setNamesStmt = null;
    
    return $db;
  }
  catch (PDOException $e)
  {
    handleDatabaseError($e);
  }
}

function openDbConnectionToWrite() {
try
  {
    $db = new PDO(DB_URL, DB_WRITE_USER, DB_WRITE_PWD);
    $db->setAttribute( PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $setNamesStmt = $db->prepare("SET NAMES 'utf8'");
    $setNamesStmt->execute();
    $setNamesStmt = null;
    $setCharSetStmt = $db->prepare("SET CHARACTER SET 'utf8'");
    $setCharSetStmt->execute();
    $setNamesStmt = null;
    
    return $db;
  }
  catch (PDOException $e)
  {
    handleDatabaseError($e);
  }
}

function closeDbConnection($db) {
  $db = null;
}

function handleDatabaseError($e) {
  echo 'Database Error: ' . $e->getMessage();
}

/*
 * Executes select count(*) from $tableName.
 * Do not use with unsanitised input.
 */
function doCount($tableName, $db) {
  try {
    $queryString = BASE_COUNT_QUERY . $tableName;
    $stmt = $db->prepare($queryString);
    $stmt->execute();
    $result = $stmt->fetch(PDO::FETCH_BOTH);
    $stmt = null;
    $count = $result[0];
  return $count;
  }
  catch (PDOException $e)
  {
    handleDatabaseError($e);
  }
 
}


function doCountWithCondition($tableName, $condition, $db) {
  try {
    $queryString = BASE_COUNT_QUERY . $tableName . $condition;
    $stmt = $db->prepare($queryString);
    $stmt->execute();
    $result = $stmt->fetch(PDO::FETCH_BOTH);
    $stmt = null;
    $count = $result[0];
  return $count;
  }
  catch (PDOException $e)
  {
    handleDatabaseError($e);
  }
}


/*
 * Executes statement $queryString. Expects the $queryString
 * to have no parameters except those defined in a mysql
 * LIMIT clause.
 * e.g select * from my_table limit ?, ?;
 *
 * Returns the corresponding result set.
 */
function doBrowseQuery($db, $queryString, $startPage, $rowCount) {
  try {
    $stmt = $db->prepare($queryString);
    $stmt->bindValue(1, $startPage * PAGE_SIZE, PDO::PARAM_INT);
    $stmt->bindValue(2, PAGE_SIZE, PDO::PARAM_INT);
    $stmt->execute();
    $result = $stmt->fetchAll(PDO::FETCH_BOTH);
    return $result;
  }
  catch (PDOException $e)
  {
    handleDatabaseError($e);
  }
               
}

function doParameterisedBrowseQuery($db,$queryString, $params, $startPage, $rowCount) {
  try {
    $i = 1;
    $stmt = $db->prepare($queryString);
    foreach ($params as $param) {
      $stmt->bindValue($i, $param);
      $i++;
    }
    $stmt->bindValue($i + 1, $startPage * PAGE_SIZE, PDO::PARAM_INT);
    $stmt->bindValud($i + 1, PAGE_SIZE, PD0::PARAM_INT);
    $stmt->execute();
    $result = $stmt->fetchAll(PDO::FETCH_BOTH);
    return $result;
  }
  catch (PDOException $e)
  {
    handleDatabaseError($e);
  }
}

function doQueryWithIdParameter($db, $queryString, $id) {
  try {
    $stmt = $db->prepare($queryString);
    $stmt->bindValue(1, $id);
    $stmt->execute();
    $result = $stmt->fetchAll(PDO::FETCH_BOTH);
    return $result;
  }
  catch (PDOException $e)
  {
    handleDatabaseError($e);
  }
}

function doSimpleQuery($db, $queryString) {
   try {
    $stmt = $db->prepare($queryString);
    $stmt->execute();
    $result = $stmt->fetchAll(PDO::FETCH_BOTH);
    return $result;
  }
  catch (PDOException $e)
  {
    handleDatabaseError($e);
  }
}

function doParameterisedQuery($db, $query, $params) {
  try {
    $i = 1;
    $stmt = $db->prepare($queryString);
    foreach ($params as $param) {
      $stmt->bindValue($i, $param);
      $i++;
    }
    $stmt->execute();
    $result = $stmt->fetchAll(PDO::FETCH_BOTH);
    return $result;
  }
  catch (PDOException $e) {
    handleDatabaseError($e);
  }
}

function getFirstColumnFromResult($result) {
  return $result[0];
}


?> 
