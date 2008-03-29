<?php
require_once '../inc/utils.php5';
ob_flush();

?>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title>
        	<?php echo $article->title ?>
        </title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>
			<?php echo $article->title ?>
		</h1>
        
        <p>
        	<?
        	for ($i = 0; $i < count($article->authors); $i++) {
        	    renderHuman($article->authors[$i]);
        	    if ($i < count($article->authors) - 1) {
        	        echo ', ';
        	    }
        	}
        	echo '. ';
            echo '\'' . $article->title . '\', ';
        	echo '<i>';
        	echo ('<a href="journal.php5?id=' . $article->journal->id . '">');
                    echo ($article->journal->title);
                    echo ('</a>'); 
        	echo '</i>';
        	if (!is_null($article->volume)) {
        	    echo ' ' . $article->volume; 
        	}
        	if (!is_null($article->issue)) {
        	    echo ' (Issue: ' . $article->issue . ')';
        	}
        	echo ' (' . $article->fromDate; 
            if (!is_null($article->toDate)) {
                echo ' - ' . $article->toDate;
            }
        	echo ')';
        	echo ', pp. ' . $article->pages . '. '; 
        	?>
        </p>
    </body>
</html> 
