<?php
require_once('utils/initPage.php5');
require_once('utils/utils.php5');
require_once('utils/db.php5');
require_once('types/shared/Individual.php');
try {
    $db = openDbConnection();

    $individualCount = Individual::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $individualCount);

    $individuals = Individual::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    require_once('view/renderIndividuals.php5');
}
catch (PDOException $pdoExeption) {
    require_once('../errors/500.php5');
}


?>
