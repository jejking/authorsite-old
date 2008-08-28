<?php
ob_start();
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/bib/Article.php');

$db = openDbConnection();

$articleId = getId($_GET['id']);
  
$article = Article::get($articleId, $db);
closeDbConnection($db);


if (!is_null($article)) {
    $smarty->assign("title", "authorsite.org - bibliography - articles");

    if (count($article->editors) == 0) {
        $smarty->assign("hasEditors", false);
    }
    else {
        $smarty->assign("hasEditors", true);
    }

    if (!is_null($article->volume)) {
        $smarty->assign("hasVolume", true);
    }
    else {
        $smarty->assign("hasVolume", false);
    }
    
    if (!is_null($article->issue)) {
        $smarty->assign("hasIssue", true);
    }
    else {
        $smarty->assign("hasIssue", false);
    }
    
    $smarty->assign("article", $article);
    $smarty->display("bib/article.tpl");
}
else {
    //require ('../errors/404.php');
    echo '404 - could not find ' . $articleId;
}

?>
 
 
