<?php
require('../inc/headers.php5');
require('../inc/db.php5');
require('../inc/utils.php5');

DEFINE ('BROWSE_INDIVIDUALS_QUERY', "SELECT id, name, givennames FROM human WHERE DTYPE = 'Individual' ORDER BY name, givennames, place DESC LIMIT ?, ?");


$db = openDbConnection();

$producerCount = doCountWithCondition("human"," where DTYPE = 'Individual'",  $db);

$pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $producerCount);

$resultSet = doBrowseQuery($db, BROWSE_INDIVIDUALS_QUERY, $pageNumber, PAGE_SIZE);



function renderIndividual($resultRow) {
   echo ('<a href="individual.php5?id=' . $resultRow['id'] . '">');
  if ($resultRow['name'] != null) {
    echo htmlspecialchars(($resultRow['name']));
    if ($resultRow['givennames'] != null) {
      echo (', ');
    }
  }
  
  if ($resultRow['givennames'] != null) {
    echo htmlspecialchars(($resultRow['givennames']));
  }
  echo ('</a>');
 
}

?>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Work Producers (Individuals)</title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>Work Producers (Individuals)</h1>
        <?php
        foreach ($resultSet as $resultRow) {
          echo "<p>\n";
          renderIndividual($resultRow);
          echo "</p>";
          
        }
        renderPaging("individuals.php5", $pageNumber, $producerCount);
        ?>
    </body>
</html> 

<?php
$result = null;
closeDbConnection($db);
?>
