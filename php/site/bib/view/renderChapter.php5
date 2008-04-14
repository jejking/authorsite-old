<?php
require_once '../shared/utils/utils.php5';
ob_flush();

?>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title>
        	<?php echo $chapter->title ?>
        </title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>
			<?php echo $chapter->title ?>
		</h1>
        
        <p>
			<?php
            // authors
            for ($i = 0; $i < count($chapter->authors); $i++) {
        	    renderHuman($chapter->authors[$i]);
        	    if ($i < count($chapter->authors) - 1) {
        	        echo ', ';
        	    }
        	}
            
			// editors (if any)
			if (count($chapter->editors) > 0) {
			    echo '.';
			    echo 'ed. by ';
			    for ($i = 0; $i < count($chapter->editors); $i++) {
            	    renderHuman($chapter->editors[$i]);
            	    if ($i < count($chapter->editors) - 1) {
            	        echo ', ';
            	    }
        	    }
			}
			
			echo '.';
			// title in single quotes
			echo ' \'' . $chapter->title . '\'';
			
			// comma
			echo ', in ';
		
			// book authors
            for ($i = 0; $i < count($chapter->book->authors); $i++) {
        	    renderHuman($chapter->book->authors[$i]);
        	    if ($i < count($chapter->book->authors) - 1) {
        	        echo ', ';
        	    }
        	}
        	if ( count($chapter->book->authors) > 0) {
        	    echo '.';
        	}
			
			// book title, italicised
			echo '<i>';
			echo  '<a href="book.php5?id=' . $chapter->book->id . '">'. $chapter->book->title . '</a>';
			echo '</i>';
			
			// , ed. by + editors
			if (count($chapter->book->editors) > 0) {
			    echo ', ed. by ';
			}
            for ($i = 0; $i < count($chapter->book->editors); $i++) {
            	    renderHuman($chapter->book->editors[$i]);
            	    if ($i < count($chapter->book->editors) - 1) {
            	        echo ', ';
            	    }
        	    }

			// ( publication place : publisher name, year )
			echo ' (';
			renderPublisher($chapter->book->publisher);
			echo ', ';
			echo $chapter->book->fromDate;
			if (!is_null($chapter->book->toDate)) {
			    echo ' - ' . $chapter->book->toDate;
			}
			echo ')';

			// pp. pages
			if (!is_null($chapter->pages)) {
			    echo ', pp. ' . $chapter->pages; 
			}

			// fullstop
			echo '.';
			
			?>
        </p>
    </body>
</html> 
 