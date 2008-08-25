<?php
ob_start();
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/bib/ExternalWebResource.php');


try {
    $db = openDbConnection();

    $externalWebResourcesCount = ExternalWebResource::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $webResourcesCount);

    $externalWebResources = ExternalWebResource::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    $smarty->assign("title", "authorsite.org - bibliography - web resources");
    
    $smarty->assign("externalWebResourcesCount", $externalWebResourcesCount);
    $smarty->assign("pageNumber", $pageNumber);
    $smarty->assign("externalWebResources", $externalWebResources);
    $smarty->display("bib/externalWebResources.tpl");
}
catch (PDOException $pdoExeption) {
    require_once('../errors/500.php5');
}
?>
 
 
 
