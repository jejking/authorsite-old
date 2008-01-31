<?php
require('../inc/headers.php5');
require('../inc/db.php5');
require('../inc/utils.php5');

require('../inc/getWorksSummary.php5');
DEFINE('LOAD_INDIVIDUAL_QUERY' , "select name, givennames FROM human WHERE DTYPE = 'Individual' and id = ?;");


$db = openDbConnection();

$individualId = getId($_GET['id']);
  
$individualResultSet = doQueryWithIdParameter($db, LOAD_INDIVIDUAL_QUERY, $individualId);
$foundIndividual = false;

// only do rest if we actually find the individual
if (count($individualResultSet) == 1) {
  $foundIndividual = true;
}

if ($foundIndividual) {
  $booksProducedResultSet = doQueryWithIdParameter($db, BOOKS_PRODUCED_QUERY, $individualId);
  $articlesProducedResultSet = doQueryWithIdParameter($db, ARTICLES_PRODUCED_QUERY, $individualId);
  $thesesProducedResultSet = doQueryWithIdParameter($db, THESES_PRODUCED_QUERY, $individualId);
  $chaptersProducedResultSet = doQueryWithIdParameter($db, CHAPTERS_PRODUCED_QUERY, $individualId);
  $webResourcesProducedResultSet = doQueryWithIdParameter($db, WEB_RESOURCES_PRODUCED_QUERY, $individualId);
}

closeDbConnection($db);

if ($foundIndividual) {
  include('../view/renderIndividual.php5');
}
else {
  include('../view/individualNotFound.php5');
}

?>
