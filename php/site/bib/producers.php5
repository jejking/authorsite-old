<?php

DEFINE ('BROWSE_PRODUCERS_QUERY', 'SELECT id, DTYPE, name, givennames, place FROM human ORDER BY name, givennames, place DESC LIMIT ?, ?');

/*
 provides lists 
 */
require('../inc/headers.php5');
require('../inc/db.php5');
$db = openDbConnection();

$resultSet = doBrowseQuery($db, BROWSE_PRODUCERS_QUERY, 0, 20);

function renderIndividual($resultRow) {
  if ($resultRow['name'] != null) {
    echo ($resultRow['name']);
    if ($resultRow['givennames'] != null) {
      echo (', ');
    }
  }
  
  if ($resultRow['givennames'] != null) {
    echo ($resultRow['givennames']);
  }
 
}

function renderCollective($resultRow) {
  if ($resultRow['name'] != null) {
    echo ($resultRow['name']);
  }
  if ($resultRow['place'] != null) {
    $place = $resultRow['place'];
    echo (" ($place)");
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
          if ($resultRow['DTYPE'] == 'Individual') {
            renderIndividual($resultRow);
          }
          else {
            renderCollective($resultRow);
          }
          echo "</p>";
        }
        ?>
    </body>
</html> 

<?php
$result = null;
closeDbConnection($db);
?>
