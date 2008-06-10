<?php
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/shared/Individual.php');

$db = openDbConnection();

$individualId = getId($_GET['id']);
  
$individual = Individual::get($individualId, $db);

if (!is_null($individual)) {
    $worksCounts = $individual->getWorksCountSummary($db);
    require('view/renderIndividual.php5');
}
else {
    require ('../errors/404.php5');
}
closeDbConnection($db);
?>
