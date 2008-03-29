<?php
require_once('../inc/headers.php5');
require_once('../inc/db.php5');
require_once('../inc/utils.php5');
require_once('../types/WebResource.php');


try {
    $db = openDbConnection();

    $webResourcesCount = WebResource::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $webResourcesCount);

    $webResources = WebResource::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    require_once('../view/renderWebResources.php5');
}
catch (PDOException $pdoExeption) {
    require_once('../view/500.php');
}
?>
 
 
 
