<?php
require('../conf/config.php5');

define('BASE_COUNT_QUERY', 'select count(*) from ');

/*
 * Returns a db connection
 */
function openDbConnection() {
  try
  {
    $db = new PDO(DB_URL, DB_USER, DB_PWD);
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


?> 
