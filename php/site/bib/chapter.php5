<?php
ob_start();
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/bib/Chapter.php');

$db = openDbConnection();

$chapterId = getId($_GET['id']);

$chapter = Chapter::get($chapterId, $db);
closeDbConnection($db);




if (!is_null($chapter)) {
    $smarty->assign("title", "authorsite.org - bibliography - chapters");

    if (count($chapter->editors) == 0) {
        $smarty->assign("hasEditors", false);
    }
    else {
        $smarty->assign("hasEditors", true);
    }

    if (count($chapter->book->authors) == 0) {
        $smarty->assign("bookHasAuthors", false);
    }
    else {
        $smarty->assign("bookHasAuthors", true);
    }
    
    if (count($chapter->book->editors) == 0) {
        $smarty->assign("bookHasEditors", false);
    }
    else {
        $smarty->assign("bookHasEditors", true);
    }

    $smarty->assign("chapter", $chapter);
    $smarty->display("bib/chapter.tpl");
}
else {
    require ('../errors/404.php5');
}

?>




