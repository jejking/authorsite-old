<?php
require_once('../shared/utils/headers.php5');
require_once('../shared/utils/db.php5');
require_once('../shared/utils/utils.php5');
require_once('../shared/types/Collective.php');
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
