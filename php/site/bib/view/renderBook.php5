<?php
require_once '../shared/utils/utils.php5';
ob_flush();

?>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title>
        	<?php echo $book->title ?>
        </title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>
		    <?php echo $book->title ?>
		</h1>
        
        <p>
        	<?
        	for ($i = 0; $i < count($book->authors); $i++) {
        	    renderHuman($book->authors[$i]);
        	    if ($i < count($book->authors) - 1) {
        	        echo ', ';
        	    }
        	}
        	echo '. ';
            echo '<i>' . $book->title . '</i> ';
        	echo '(';
        	renderPublisher($book->publisher);
            if (!is_null($article->toDate)) {
                echo ' - ' . $article->toDate;
            }
        	echo ')';
        	?>
        </p>
    </body>
</html> 
