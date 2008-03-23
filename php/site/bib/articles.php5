<?php
require_once('../inc/headers.php5');
require_once('../inc/db.php5');
require_once('../inc/utils.php5');
require_once('../types/Article.php');


try {
    $db = openDbConnection();

    $articlesCount = Article::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $articlesCount);

    $articles = Article::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    require_once('../view/renderArticles.php5');
}
catch (PDOException $pdoExeption) {
    require_once('../view/500.php');
}
?>
