<?php
require_once('utils/initPage.php5');
require_once('utils/utils.php5');
require_once('utils/db.php5');
require_once('types/shared/Individual.php');
try {
    $db = openDbConnection();

    $individualsCount = Individual::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $individualCount);

    $individuals = Individual::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    
    $smarty->assign("title", "authorsite.org - bibliography - individuals");
    
    $smarty->assign("individualsCount", $individualsCount);
    $smarty->assign("pageNumber", $pageNumber);
    $smarty->assign("individuals", $individuals);
    $smarty->display("bib/individuals.tpl");
}
catch (PDOException $pdoExeption) {
    require_once('../errors/500.php5');
}


?>
