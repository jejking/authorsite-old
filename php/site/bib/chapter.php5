<?php
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/bib/Chapter.php');

$db = openDbConnection();

$chapterId = getId($_GET['id']);
  
$chapter = Chapter::get($chapterId, $db);

if (!is_null($chapter)) {
    require('view/renderChapter.php5');
}
else {
    require ('../errors/404.php5');
}
closeDbConnection($db);
?>
 
 
 
 
