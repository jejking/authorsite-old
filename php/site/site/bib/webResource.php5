<?php
require_once('../inc/headers.php5');
require_once('../inc/db.php5');
require_once('../inc/utils.php5');
require_once('../types/WebResource.php');

$db = openDbConnection();

$webResourceId = getId($_GET['id']);
  
$webResource = WebResource::get($webResourceId, $db);

if (!is_null($webResource)) {
    require('../view/renderWebResource.php5');
}
else {
    require ('../view/404.php');
}
closeDbConnection($db);
?>
 
 
 
