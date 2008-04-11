<?php
require_once('../shared/utils/headers.php5');
require_once('../shared/utils/db.php5');
require_once('../shared/types/Individual.php');
require_once('../shared/types/Collective.php');
require_once('types/Article.php');
require_once('types/Book.php');
require_once('types/Journal.php');
require_once('types/Chapter.php');
require_once('types/Thesis.php');
require_once('types/ExternalWebResource.php');
$db = openDbConnection();

// do counts
$individualsCount = Individual::count($db);
$collectivesCount = Collective::count($db);
$booksCount = Book::count($db);
$articlesCount = Article::count($db);
$chaptersCount = Chapter::count($db);
$thesesCount = Thesis::count($db);
$externalWebResourcesCount = ExternalWebResource::count($db);
$journalsCount = Journal::count($db);

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
        <p><a href="externalWebResources.php5">External Web Resources</a> (<?php echo $externalWebResourcesCount ?>)</p>
    </body>
</html>
