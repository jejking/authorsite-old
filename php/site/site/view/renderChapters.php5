<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title>Chapters</title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>Chapters</h1>
        
        <table>
          <thead>
          	<tr>
          		<th>id</th>
          		<th>Title</th>
          		<th>Book</th>
          		<th>Pages</th>
          		<th>Dates</th>
          		<th>Author(s)</th>
          		<th>Editor(s)</th>
          	</tr>
          </thead>
          <tbody>
          <?php
            foreach ($chapters as $chapter) {
                echo ('<tr>');
                    echo ('<td>');
                    echo ('<a href="chapter.php5?id=' . $chapter->id . '">' . $chapter->id . '</a>');
                    echo ("</td>");

                    echo ('<td>');
                    echo ($chapter->title);
                    echo ('</td>');
                    
                    echo ('<td>');
                    echo ('<a href="book.php5?id=' . $chapter->book->id . '">');
                    echo ($chapter->book->title);
                    echo ('</a>');
                    
                    echo ('</td>');
                    
                    echo ('<td>');
                    echo ($chapter->pages);
                    echo ('</td>');
                    
                    echo ('<td>');
                    echo $chapter->fromDate;
                    if (!is_null($chapter->toDate)) {
                        echo ' - ' . $chapter->toDate;
                    }
                    
                    echo ('</td>');
                    
                    echo ('<td>');
                    if (count($chapter->authors) == 0) {
                        echo (' - ');
                    }
                    else {
                        foreach ($chapter->authors as $author) {
                            renderHuman($author);
                            echo (' ');
                        }
                    }
                    echo ('</td>');
                    
                    echo ('<td>');
                    if (count($chapter->editors) == 0) {
                        echo (' - ');
                    }
                    else {
                        foreach ($chapter->editors as $editor) {
                            renderHuman($editor);
                        }
                    }
                    echo ('</td>');
                echo ('</tr>');
            }
          ?>
          </tbody>
        </table>
        <p>
        <?php renderPaging("chapters.php5", $pageNumber, $chaptersCount); ?>
        </p>
    </body>
</html> 
