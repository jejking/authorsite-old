<?php
ob_start();
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
    
    $smarty->assign("title", "authorsite.org - bibliography - journals");
    
    $smarty->assign("journalsCount", $journalsCount);
    $smarty->assign("pageNumber", $pageNumber);
    $smarty->assign("journals", $journals);
    $smarty->display("bib/journals.tpl");
}
catch (PDOException $pdoExeption) {
    require_once('../errors/500.php5');
}
?>
 
