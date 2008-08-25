<?php
ob_start();
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/bib/Chapter.php');


try {
    $db = openDbConnection();

    $chaptersCount = Chapter::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $chaptersCount);

    $chapters = Chapter::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    
    $smarty->assign("title", "authorsite.org - bibliography - chapters");
    
    $smarty->assign("chaptersCount", $chaptersCount);
    $smarty->assign("pageNumber", $pageNumber);
    $smarty->assign("chapters", $chapters);
    $smarty->display("bib/chapters.tpl");
    
}
catch (PDOException $pdoExeption) {
    require_once('../errors/500.php5');
}
?> 
