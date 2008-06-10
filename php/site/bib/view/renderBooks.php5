<?php
require_once 'utils/utils.php5';
ob_flush();
?>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title>Books</title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>Books</h1>
        
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
            foreach ($books as $book) {
                echo ('<tr>');
                    echo ('<td>');
                    echo ('<a href="book.php5?id=' . $book->id . '">' . $book->id . '</a>');
                    echo ("</td>");

                    echo ('<td>');
                    echo ($book->title);
                    echo ('</td>');
                    
                    echo ('<td>');
                    echo $book->fromDate;
                    if (!is_null($book->toDate)) {
                        echo ' - ' . $book->toDate;
                    }
                    
                    echo ('</td>');
                    
                    echo ('<td>');
                    if (count($book->authors) == 0) {
                        echo (' - ');
                    }
                    else {
                        foreach ($book->authors as $author) {
                            renderHuman($author);
                            echo (' ');
                        }
                    }
                    echo ('</td>');
                    
                    echo ('<td>');
                    if (count($book->editors) == 0) {
                        echo (' - ');
                    }
                    else {
                        foreach ($book->editors as $editor) {
                            renderHuman($editor);
                        }
                    }
                    echo ('</td>');
                    
                    echo ('<td>');
                    if (is_null($book->publisher)) {
                        echo (' - ');
                    }
                    else {
                        renderHuman($book->publisher);
                    }
                    echo ('</td>');
                echo ('</tr>');
            }
          ?>
          </tbody>
        </table>
        <p>
        <?php renderPaging("books.php5", $pageNumber, $booksCount); ?>
        </p>
    </body>
</html> 
