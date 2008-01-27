<?php
require('../inc/headers.php5');
require('../inc/db.php5');
require('../inc/utils.php5');

DEFINE ('BROWSE_INDIVIDUALS_QUERY', "SELECT id, name, givennames, place FROM human WHERE DTYPE = 'Individual' ORDER BY name, givennames, place DESC LIMIT ?, ?");

DEFINE ('BROWSE_COLLECTIVES_QUERY', "SELECT id, name, givennames, place FROM human WHERE DTYPE = 'Collective' ORDER BY name, givennames, place DESC LIMIT ?, ?");



$db = openDbConnection();
$producerCount = 0;


// we default to browsing individuals unless collectives are explicitly specified
$browseIndividuals = true;
if ( $_GET[DTYPE] == 'Collective') {
  $browseIndividuals = false;
  $producerCount = doCountWithCondition("human"," where DTYPE = 'Collective'",  $db);
}
else {
  $producerCount = doCountWithCondition("human"," where DTYPE = 'Collective'",  $db);
}

$pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $producerCount);

/*
 provides lists 
 */


$resultSet = null;
if ($browseIndividuals) {
  $resultSet = doBrowseQuery($db, BROWSE_INDIVIDUALS_QUERY, $pageNumber, PAGE_SIZE);
}
else {
  $resultSet = doBrowseQuery($db, BROWSE_COLLECTIVES_QUERY, $pageNumber, PAGE_SIZE);
}


function renderIndividual($resultRow) {
  if ($resultRow['name'] != null) {
    echo htmlspecialchars(($resultRow['name']));
    if ($resultRow['givennames'] != null) {
      echo (', ');
    }
  }
  
  if ($resultRow['givennames'] != null) {
    echo htmlspecialchars(($resultRow['givennames']));
  }
 
}

function renderCollective($resultRow) {
  if ($resultRow['name'] != null) {
    echo htmlspecialchars($resultRow['name']);
  }
  if ($resultRow['place'] != null) {
    $place = $resultRow['place'];
    echo htmlspecialchars(" ($place)");
  }
}


?>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Work Producers</title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>Producers</h1>
        <?php
        foreach ($resultSet as $resultRow) {
          echo "<p>\n";
          if ($browseIndividuals) {
            renderIndividual($resultRow);
          }
          else {
            renderCollective($resultRow);
          }
          echo "</p>";
          
        }
        renderPaging("producers.php5", $pageNumber, $producerCount);
        ?>
    </body>
</html> 

<?php
$result = null;
closeDbConnection($db);
?>
