<?php
require('../inc/headers.php5');
require('../inc/db.php5');
require('../inc/utils.php5');

require('../inc/getWorksSummary.php5');
DEFINE('LOAD_COLLECTIVE_QUERY' , "select name, place FROM human WHERE DTYPE = 'Collective' and id = ?;");


$db = openDbConnection();

$collectiveId = getId($_GET['id']);
  
$collectiveResultSet = doQueryWithIdParameter($db, LOAD_COLLECTIVE_QUERY, $collectiveId);
$foundCollective = false;

// only do rest if we actually find the collective
if (count($collectiveResultSet) == 1) {
  $foundCollective = true;
}

if ($foundCollective) {
  $booksProducedResultSet = doQueryWithIdParameter($db, BOOKS_PRODUCED_QUERY, $collectiveId);
  $articlesProducedResultSet = doQueryWithIdParameter($db, ARTICLES_PRODUCED_QUERY, $collectiveId);
  $thesesProducedResultSet = doQueryWithIdParameter($db, THESES_PRODUCED_QUERY, $collectiveId);
  $chaptersProducedResultSet = doQueryWithIdParameter($db, CHAPTERS_PRODUCED_QUERY, $collectiveId);
  $webResourcesProducedResultSet = doQueryWithIdParameter($db, WEB_RESOURCES_PRODUCED_QUERY, $collectiveId);
}

closeDbConnection($db);

if ($foundCollective) {
  include('../view/renderCollective.php5');
}
else {
  include('../view/collectiveNotFound.php5');
}

?>
