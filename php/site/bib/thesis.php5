<?php
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/bib/Thesis.php');

$db = openDbConnection();

$thesisId = getId($_GET['id']);
  
$thesis = Thesis::get($thesisId, $db);

if (!is_null($thesis)) {
    require('view/renderThesis.php5');
}
else {
    require ('../errors/404.php5');
}
closeDbConnection($db);
?>
 
 
