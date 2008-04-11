<?php
require_once('../shared/utils/headers.php5');
require_once('../shared/utils/db.php5');
require_once('../shared/utils/utils.php5');
require_once('types/ExternalWebResource.php');

$db = openDbConnection();

$webResourceId = getId($_GET['id']);
  
$webResource = WebResource::get($webResourceId, $db);

if (!is_null($webResource)) {
    require('view/renderWebResource.php5');
}
else {
    require ('../errors/404.php5');
}
closeDbConnection($db);
?>
 
 
 
