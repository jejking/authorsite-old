<?php
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/bib/Article.php');

$db = openDbConnection();

$articleId = getId($_GET['id']);
  
$article = Article::get($articleId, $db);

if (!is_null($article)) {
    require('view/renderArticle.php5');
}
else {
    require ('../errors/404.php');
}
closeDbConnection($db);
?>
 
 
