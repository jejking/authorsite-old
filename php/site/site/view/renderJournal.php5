<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>
            <?php 
                echo $journal->title; 
            ?>
        </title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>
			<?php
                echo (htmlspecialchars( $journal->title));
			 ?>
		</h1>
        
        <p>
        	Articles published: <?php echo $articlesCount ?>
        </p>
        
    </body>
</html> 
