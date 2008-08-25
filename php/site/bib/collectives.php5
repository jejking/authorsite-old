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
    
    $smarty->assign("title", "authorsite.org - bibliography - collectives");
    
    $smarty->assign("collectivesCount", $collectivesCount);
    $smarty->assign("pageNumber", $pageNumber);
    $smarty->assign("collectives", $collectives);
    $smarty->display("bib/collectives.tpl");
    
}
catch (PDOException $pdoExeption) {
    require_once('../errors/500.php5');
}
?>
