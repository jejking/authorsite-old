<?php
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/bib/ExternalWebResource.php');

$db = openDbConnection();

$webResourceId = getId($_GET['id']);
  
$webResource = ExternalWebResource::get($webResourceId, $db);

if (!is_null($webResource)) {
    require('view/renderExternalWebResource.php5');
}
else {
    require ('../errors/404.php5');
}
closeDbConnection($db);
?>
 
 
 
