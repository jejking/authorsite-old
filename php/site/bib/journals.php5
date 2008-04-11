<?php
require_once('../shared/utils/headers.php5');
require_once('../shared/utils/db.php5');
require_once('../shared/utils/utils.php5');

require_once('types/Journal.php');


try {
    $db = openDbConnection();

    $journalsCount = Journal::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $journalsCount);

    $journals = Journal::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    require_once('view/renderJournals.php5');
}
catch (PDOException $pdoExeption) {
    require_once('../errors/500.php5');
}
?>
 
