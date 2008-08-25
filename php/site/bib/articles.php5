<?php
ob_start();
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/bib/Article.php');


try {
    $db = openDbConnection();

    $articlesCount = Article::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $articlesCount);

    $articles = Article::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    $smarty->assign("title", "authorsite.org - bibliography - articles");
    
    $smarty->assign("articlesCount", $articlesCount);
    $smarty->assign("pageNumber", $pageNumber);
    $smarty->assign("articles", $articles);
    $smarty->display("bib/articles.tpl");
}
catch (PDOException $pdoExeption) {
    require_once('../errors/500.php5');
}
?>
