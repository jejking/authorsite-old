<?php
require_once('../inc/headers.php5');
require_once('../inc/db.php5');
require_once('../inc/utils.php5');
require_once('../types/Journal.php');


try {
    $db = openDbConnection();

    $journalsCount = Journal::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $journalsCount);

    $journals = Journal::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    require_once('../view/renderJournals.php5');
}
catch (PDOException $pdoExeption) {
    require_once('../view/500.php');
}
?>
 
