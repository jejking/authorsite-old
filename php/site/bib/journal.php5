<?php
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');

require_once('types/bib/Journal.php');

$db = openDbConnection();

$journalId = getId($_GET['id']);
  
$journal = Journal::get($journalId, $db);

if (!is_null($journal)) {
    $articlesCount = $journal->getArticlesCount($db);
    require('view/renderJournal.php5');
}
else {
    require ('../errors/404.php5');
}
closeDbConnection($db);
?>
 
