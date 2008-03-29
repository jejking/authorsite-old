<?php
require_once('../inc/headers.php5');
require_once('../inc/db.php5');
require_once('../inc/utils.php5');
require_once('../types/Article.php');

$db = openDbConnection();

$articleId = getId($_GET['id']);
  
$article = Article::get($articleId, $db);

if (!is_null($article)) {
    require('../view/renderArticle.php5');
}
else {
    require ('../view/404.php');
}
closeDbConnection($db);
?>
 
 
