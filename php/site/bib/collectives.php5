<?php
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/shared/Collective.php');
try {
    $db = openDbConnection();

    $collectivesCount = Collective::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $collectivesCount);

    $collectives = Collective::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    require_once('view/renderCollectives.php5');
}
catch (PDOException $pdoExeption) {
    require_once('../errors/500.php5');
}
?>
