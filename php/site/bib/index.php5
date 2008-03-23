<?php
require_once('../inc/headers.php5');
require_once('../inc/db.php5');
require_once('../types/Individual.php');
require_once('../types/Collective.php');
require_once('../types/Article.php');
$db = openDbConnection();

// do counts
$individualsCount = Individual::count($db);
$collectivesCount = Collective::count($db);
$booksCount = doCount('book', $db);
$articlesCount = Article::count($db);
$chaptersCount = doCount('chapter', $db);
$thesesCount = doCount('thesis', $db);
$webResourcesCount = doCount('webresource', $db);
$journalsCount = doCount('journal', $db);

closeDbConnection($db);

?>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>bibliography</title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
        
    </head>
    <body>
        <h1>Bibliography</h1>
        <p><a href="individuals.php5">Individuals</a> (<?php echo $individualsCount ?>)</p>
        <p><a href="collectives.php5">Collectives</a> (<?php echo $collectivesCount ?>)</p>
        <p><a href="books.php5">Books</a> (<?php echo $booksCount ?>)</p>
        <p><a href="articles.php5">Articles</a> (<?php echo $articlesCount ?>)</p>
        <p><a href="chapters.php5">Chapters</a> (<?php echo $chaptersCount ?>)</p>
        <p><a href="theses.php5">Theses</a> (<?php echo $thesesCount ?>)</p>
        <p><a href="journals.php5">Journals</a> (<?php echo $journalsCount ?>)</p>
        <p><a href="webresources.php5">Web Resources</a> (<?php echo $webResourcesCount ?>)</p>
    </body>
</html>
