<?php
require('../inc/headers.php5');
require('../inc/db.php5');
require('../inc/utils.php5');

DEFINE ('BROWSE_COLLECTIVES_QUERY', "SELECT id, name, givennames, place FROM human WHERE DTYPE = 'Collective' ORDER BY name, givennames, place DESC LIMIT ?, ?");



$db = openDbConnection();
$producerCount = doCountWithCondition("human"," where DTYPE = 'Collective'",  $db);
$pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $producerCount);

$resultSet = doBrowseQuery($db, BROWSE_COLLECTIVES_QUERY, $pageNumber, PAGE_SIZE);


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
        <title>Work Producers (Collectives)</title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>Work Producers (Collectives)</h1>
        <?php
        foreach ($resultSet as $resultRow) {
          echo "<p>\n";
          renderCollective($resultRow);
          echo "</p>";
          
        }
        renderPaging("collectives.php5", $pageNumber, $producerCount);
        ?>
    </body>
</html> 

<?php
$result = null;
closeDbConnection($db);
?>
