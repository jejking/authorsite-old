<?php
require_once '../inc/utils.php5';
ob_flush();
?>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title>Journals</title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>Journals</h1>
        
        <table>
          <thead>
          	<tr>
          		<th>id</th>
          		<th>Name</th>
          	</tr>
          </thead>
          <tbody>
          <?php
            foreach ($journals as $journal) {
                echo ('<tr>');
                    echo ('<td>');
                    echo ('<a href="journal.php5?id=' . $journal->id . '">' . $journal->id . '</a>');
                    echo ("</td>");

                    echo ('<td>');
                    echo ($journal->title);
                    echo ('</td>');
                    
                echo ('</tr>');
            }
          ?>
          </tbody>
        </table>
        <p>
        <?php renderPaging("journals.php5", $pageNumber, $journalsCount); ?>
        </p>
    </body>
</html> 
