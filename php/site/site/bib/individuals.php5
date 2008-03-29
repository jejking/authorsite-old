<?php
require_once('../inc/headers.php5');
require_once('../inc/utils.php5');
require_once('../inc/db.php5');
require_once('../types/Individual.php');
try {
    $db = openDbConnection();

    $individualCount = Individual::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $individualCount);

    $individuals = Individual::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    require_once('../view/renderIndividuals.php5');
}
catch (PDOException $pdoExeption) {
    require_once('../view/500.php');
}


?>
