<?php
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/Article.php');


try {
    $db = openDbConnection();

    $articlesCount = Article::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $articlesCount);

    $articles = Article::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    require_once('view/renderArticles.php5');
}
catch (PDOException $pdoExeption) {
    require_once('../errors/500.php5');
}
?>
