<?php
require_once('../inc/headers.php5');
require_once('../inc/db.php5');
require_once('../inc/utils.php5');
require_once('../types/Collective.php');
try {
    $db = openDbConnection();

    $collectivesCount = Collective::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $collectivesCount);

    $collectives = Collective::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    require_once('../view/renderCollectives.php5');
}
catch (PDOException $pdoExeption) {
    require_once('../view/500.php');
}
?>
