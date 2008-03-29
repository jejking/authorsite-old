<?php
require_once '../inc/utils.php5';
ob_flush();
?>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title>Web Resources</title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>Web Resources</h1>
        
        <table>
          <thead>
          	<tr>
          		<th>id</th>
          		<th>Title</th>
          		<th>Dates</th>
          		<th>Author(s)</th>
          		<th>Editor(s)</th>
          		<th>Publisher</th>
          	</tr>
          </thead>
          <tbody>
          <?php
            foreach ($webResources as $webResource) {
                echo ('<tr>');
                    echo ('<td>');
                    echo ('<a href="webResource.php5?id=' . $webResource->id . '">' . $webResource->id . '</a>');
                    echo ("</td>");

                    echo ('<td>');
                    echo ($webResource->title);
                    echo ('</td>');
                    
                    echo $webResource->fromDate;
                    if (!is_null($webResource->toDate)) {
                        echo ' - ' . $webResource->toDate;
                    }
                    
                    echo ('<td>');
                    if (count($webResource->authors) == 0) {
                        echo (' - ');
                    }
                    else {
                        foreach ($webResource->authors as $author) {
                            renderHuman($author);
                            echo (' ');
                        }
                    }
                    echo ('</td>');
                    
                    echo ('<td>');
                    if (count($webResource->editors) == 0) {
                        echo (' - ');
                    }
                    else {
                        foreach ($webResource->editors as $editor) {
                            renderHuman($editor);
                        }
                    }
                    echo ('</td>');
                    
                    echo ('<td>');
                    if (is_null($webResource->publisher)) {
                        echo (' - ');
                    }
                    else {
                        renderHuman($webResource->publisher);
                    }
                    echo ('</td>');
                    
                    
                echo ('</tr>');
            }
          ?>
          </tbody>
        </table>
        <p>
        <?php renderPaging("webResources.php5", $pageNumber, $webResourcesCount); ?>
        </p>
    </body>
</html> 
