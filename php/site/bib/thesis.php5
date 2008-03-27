<?php
require_once('../inc/headers.php5');
require_once('../inc/db.php5');
require_once('../inc/utils.php5');
require_once('../types/Thesis.php');

$db = openDbConnection();

$thesisId = getId($_GET['id']);
  
$thesis = Thesis::get($thesisId, $db);

if (!is_null($thesis)) {
    require('../view/renderThesis.php5');
}
else {
    require ('../view/404.php');
}
closeDbConnection($db);
?>
 
 
