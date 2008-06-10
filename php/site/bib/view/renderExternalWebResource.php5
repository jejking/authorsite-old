<?php
require_once 'utils/utils.php5';
ob_flush();

?>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title>
        	<?php echo $webResource->title ?>
        </title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>
		    <?php echo $webResource->title ?>
		</h1>
        
        <p>
        	<?
            for ($i = 0; $i < count($webResource->authors); $i++) {
        	    renderHuman($webResource->authors[$i]);
        	    if ($i < count($webResource->authors) - 1) {
        	        echo ', ';
        	    }
        	}
        	if (count($webResource->editors) > 0) {
        	    echo ' ed. ';
        	    for ($i = 0; $i < count($webResource->editors); $i++) {
        	        renderHuman($webResource->editors[$i]);
        	        if ($i < count($webResource->editors) - 1) {
        	            echo ', ';
        	        }
        	    }
        	}
        	
        	
        	echo '. ';
            
        	if ($webResource->lastStatusCode == 200) {
        	    echo '<a href="' . $webResource->url. '">' . $webResource->title . '</a>';
        	}
        	else {
        	    echo $webResource->title . ' (Currently unavailable)';
        	}
        	
            if (!is_null($webResource->publisher)) {
                echo ' Published by: ';
                renderHuman($webResource->publisher);
            }
        	?>
        </p>
        <p>
        	<?php
            echo 'Last Checked: ' . $webResource->lastChecked;
        	?>
        </p>
    </body>
</html> 
