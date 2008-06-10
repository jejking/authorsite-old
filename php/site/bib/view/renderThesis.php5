<?php
require_once 'utils/utils.php5';
ob_flush();

?>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title>
        	<?php echo $thesis->title ?>
        </title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>
		    <?php echo $thesis->title ?>
		</h1>
        
        <p>
        	<?
        	renderHuman($thesis->author);
        	echo '. ';
            echo '\'' . $thesis->title . '\' ';
        	echo '(';
        	echo $thesis->degree . ' thesis, ';
        	renderAwardingBody($thesis->awardingBody);
            if (!is_null($article->toDate)) {
                echo ' - ' . $article->toDate;
            }
        	echo ')';
        	echo '.';
        	?>
        </p>
    </body>
</html> 
