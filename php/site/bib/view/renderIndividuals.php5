<?php
require_once 'utils/utils.php5';
ob_flush();
?>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title>Individuals</title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>Individuals</h1>
        
        <table>
          <thead>
          	<tr>
          		<th>id</th>
          		<th>Family Name</th>
          		<th>Given Names</th>
          		<th>Name Qualification</th>
          	</tr>
          </thead>
          <tbody>
          <?php
            foreach ($individuals as $individual) {
                echo ('<tr>');
                    echo ('<td>');
                    echo ('<a href="individual.php5?id=' . $individual->id . '">' . $individual->id . '</a>');
                    echo ("</td>");

                    echo ('<td>');
                    echo ($individual->name);
                    echo ('</td>');
                    
                    echo ('<td>');
                    echo ($individual->givenNames);
                    echo ('</td>');
                    
                    echo ('<td>');
                    echo ($individual->nameQualification);
                    echo ('</td>');
                echo ('</tr>');
            }
          ?>
          </tbody>
        </table>
        <p>
        <?php renderPaging("individuals.php5", $pageNumber, $individualCount); ?>
        </p>
    </body>
</html> 
