<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>
            <?php 
                echo $individual->name; 
                if ($individual->givenNames != null) {
                    echo ', ';
                    echo $individual->givenNames;
                }
            ?>
        </title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>
			<?php
                echo (htmlspecialchars( $individual->name));
                if ($individual->givenNames != null) {
                    echo ', ';
                    echo $individual->givenNames;
                }
			 ?>
		</h1>
        
        <?php require_once('renderWorksCounts.php5') ?>
        
    </body>
</html> 

