<?php
require_once('../shared/utils/headers.php5');
require_once('../shared/utils/db.php5');
require_once('../shared/utils/utils.php5');

require_once('types/ExternalWebResource.php');


try {
    $db = openDbConnection();

    $externalWebResourcesCount = ExternalWebResource::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $webResourcesCount);

    $externalWebResources = ExternalWebResource::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    require_once('view/renderExternalWebResources.php5');
}
catch (PDOException $pdoExeption) {
    require_once('../errors/500.php5');
}
?>
 
 
 
