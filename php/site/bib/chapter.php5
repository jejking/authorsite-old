<?php
require_once('../inc/headers.php5');
require_once('../inc/db.php5');
require_once('../inc/utils.php5');
require_once('../types/Chapter.php');

$db = openDbConnection();

$chapterId = getId($_GET['id']);
  
$chapter = Chapter::get($chapterId, $db);

if (!is_null($chapter)) {
    require('../view/renderChapter.php5');
}
else {
    require ('../view/404.php');
}
closeDbConnection($db);
?>
 
 
 
 
