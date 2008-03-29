<?php
require_once('../inc/headers.php5');
require_once('../inc/db.php5');
require_once('../inc/utils.php5');
require_once('../types/Individual.php');

$db = openDbConnection();

$individualId = getId($_GET['id']);
  
$individual = Individual::get($individualId, $db);

if (!is_null($individual)) {
    $worksCounts = $individual->getWorksCountSummary($db);
    require('../view/renderIndividual.php5');
}
else {
    require ('../view/404.php');
}
closeDbConnection($db);
?>
