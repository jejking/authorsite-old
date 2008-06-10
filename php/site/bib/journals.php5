<?php
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');

require_once('types/bib/Journal.php');


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
 
