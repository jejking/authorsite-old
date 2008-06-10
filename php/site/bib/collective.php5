<?php
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/shared/Collective.php');
$db = openDbConnection();

$collectiveId = getId($_GET['id']);
  
$collective = Collective::get($collectiveId, $db);

if (!is_null($collective)) {
    $worksCounts = $collective->getWorksCountSummary($db);
    require('view/renderCollective.php5');
}
else {
    require ('../errors/404.php5');
}
closeDbConnection($db);
?>
