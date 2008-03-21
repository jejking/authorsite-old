<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>
            <?php 
                echo $collective->name; 
                if (!is_null($collective->place)) {
                    echo '( ';
                    echo $collective->place;
                    echo ') ';
                }
                echo '(' . $collective->id . ')';
            ?>
        </title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>
			<?php
                echo (htmlspecialchars( $collective->name));
                if (!is_null($collective->place)) {
                    echo '( ';
                    echo $collective->place;
                    echo ') ';
                }
			 ?>
		</h1>
        
        <?php require_once('renderWorksCounts.php5') ?>
        
    </body>
</html> 
