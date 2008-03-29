<?php
require_once('../inc/headers.php5');
require_once('../inc/db.php5');
require_once('../inc/utils.php5');
require_once('../types/Journal.php');

$db = openDbConnection();

$journalId = getId($_GET['id']);
  
$journal = Journal::get($journalId, $db);

if (!is_null($journal)) {
    $articlesCount = $journal->getArticlesCount($db);
    require('../view/renderJournal.php5');
}
else {
    require ('../view/404.php');
}
closeDbConnection($db);
?>
 
