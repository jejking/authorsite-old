<?php
require_once '../shared/utils/utils.php5';
ob_flush();
?>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title>Collectives</title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>Collectives</h1>
        
        <table>
          <thead>
          	<tr>
          		<th>id</th>
          		<th>Name</th>
          		<th>Name Qualification</th>
          		<th>Place</th>
          	</tr>
          </thead>
          <tbody>
          <?php
            foreach ($collectives as $collective) {
                echo ('<tr>');
                    echo ('<td>');
                    echo ('<a href="collective.php5?id=' . $collective->id . '">' . $collective->id . '</a>');
                    echo ("</td>");

                    echo ('<td>');
                    echo ($collective->name);
                    echo ('</td>');
                    
                    echo ('<td>');
                    echo ($collective->nameQualification);
                    echo ('</td>');
                    
                    echo ('<td>');
                    echo ($collective->place);
                    echo ('</td>');
                echo ('</tr>');
            }
          ?>
          </tbody>
        </table>
        <p>
        <?php renderPaging("collectives.php5", $pageNumber, $collectivesCount); ?>
        </p>
    </body>
</html> 
