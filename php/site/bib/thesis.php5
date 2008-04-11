<?php
require_once('../shared/utils/headers.php5');
require_once('../shared/utils/db.php5');
require_once('../shared/utils/utils.php5');
require_once('types/Thesis.php');

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
 
 
