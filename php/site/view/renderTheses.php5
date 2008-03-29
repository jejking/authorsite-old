<?php
require_once '../inc/utils.php5';
ob_flush();
?>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title>Theses</title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>Theses</h1>
        
        <table>
          <thead>
          	<tr>
          		<th>id</th>
          		<th>Title</th>
          		<th>Degree</th>
          		<th>Dates</th>
          		<th>Author</th>
          		<th>Awarding Body</th>
          	</tr>
          </thead>
          <tbody>
          <?php
            foreach ($theses as $thesis) {
                echo ('<tr>');
                    echo ('<td>');
                    echo ('<a href="thesis.php5?id=' . $thesis->id . '">' . $thesis->id . '</a>');
                    echo ("</td>");

                    echo ('<td>');
                    echo ($thesis->title);
                    echo ('</td>');
                    
                    echo ('<td>');
                    echo ($thesis->degree);
                    echo ('</td>');
                    
                    echo ('<td>');
                    echo $thesis->fromDate;
                    echo ('</td>');
                    
                    echo ('<td>');
                    renderHuman($thesis->author);
                    echo ('</td>');
                    
                    echo ('<td>');
                    if (is_null($thesis->awardingBody)) {
                        echo (' - ');
                    }
                    else {
                        renderHuman($thesis->awardingBody);
                    }
                    echo ('</td>');
                echo ('</tr>');
            }
          ?>
          </tbody>
        </table>
        <p>
        <?php renderPaging("theses.php5", $pageNumber, $thesesCount); ?>
        </p>
    </body>
</html> 
