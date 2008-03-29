<?php
require_once('../inc/headers.php5');
require_once('../inc/db.php5');
require_once('../inc/utils.php5');
require_once('../types/Collective.php');
$db = openDbConnection();

$collectiveId = getId($_GET['id']);
  
$collective = Collective::get($collectiveId, $db);

if (!is_null($collective)) {
    $worksCounts = $collective->getWorksCountSummary($db);
    require('../view/renderCollective.php5');
}
else {
    require ('../view/404.php');
}
closeDbConnection($db);
?>
