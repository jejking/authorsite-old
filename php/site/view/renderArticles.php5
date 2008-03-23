<?php
require_once '../inc/utils.php5';
ob_flush();

function renderHuman($human) {
    if ($human instanceof Individual) {
        echo ('<a href="individual.php5?id=' . $human->id . '">');
        echo ($human->name);
        if (!is_null($human->givenNames)) {
            echo (', ' . $human->givenNames);
        }
        echo ('</a>');
    }
    else {
        echo ('<a href="collective.php5?id=' . $human->id . '">');
        echo ($human->name);
        if (!is_null($human->place)) {
            echo (' ('. $human->place . ')');
        }
        echo ('</a>');
    }
}

?>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title>Articles</title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>Articles</h1>
        
        <table>
          <thead>
          	<tr>
          		<th>id</th>
          		<th>Title</th>
          		<th>Journal</th>
          		<th>Pages</th>
          		<th>Dates</th>
          		<th>Author(s)</th>
          		<th>Editor(s)</th>
          	</tr>
          </thead>
          <tbody>
          <?php
            foreach ($articles as $article) {
                echo ('<tr>');
                    echo ('<td>');
                    echo ('<a href="article.php5?id=' . $article->id . '">' . $article->id . '</a>');
                    echo ("</td>");

                    echo ('<td>');
                    echo ($article->title);
                    echo ('</td>');
                    
                    echo ('<td>');
                    echo ('<a href="journal.php5?id=' . $article->journal->id . '">');
                    echo ($article->journal->title);
                    echo ('</a>');
                    
                    if (!is_null($article->journal->volume)) {
                        echo ', Vol: ' . $article->journal->volume;
                    }
                    if (!is_null($article->journal->issue)) {
                        echo ' (Issue: ' . $article->journal->issue . ')';
                    }
                    
                    echo ('</td>');
                    
                    echo ('<td>');
                    echo ($article->pages);
                    echo ('</td>');
                    
                    echo ('<td>');
                    echo $article->fromDate;
                    if (!is_null($article->toDate)) {
                        echo ' - ' . $article->toDate;
                    }
                    
                    echo ('</td>');
                    
                    echo ('<td>');
                    if (count($article->authors) == 0) {
                        echo (' - ');
                    }
                    else {
                        foreach ($article->authors as $author) {
                            renderHuman($author);
                            echo (' ');
                        }
                    }
                    echo ('</td>');
                    
                    echo ('<td>');
                    if (count($article->editors) == 0) {
                        echo (' - ');
                    }
                    else {
                        foreach ($article->editors as $editor) {
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
        <?php renderPaging("articles.php5", $pageNumber, $articlesCount); ?>
        </p>
    </body>
</html> 
