<?php
ob_start();
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/bib/Thesis.php');


try {
    $db = openDbConnection();

    $thesesCount = Thesis::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $thesesCount);

    $theses = Thesis::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    $smarty->assign("title", "authorsite.org - bibliography - theses");
    
    $smarty->assign("thesesCount", $thesesCount);
    $smarty->assign("pageNumber", $pageNumber);
    $smarty->assign("theses", $theses);
    $smarty->display("bib/theses.tpl");
}
catch (PDOException $pdoExeption) {
    require_once('errors/500.php5');
}
?>
 
 
