<?php
ob_start();
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/bib/Book.php');

$db = openDbConnection();

$bookId = getId($_GET['id']);
  
$book = Book::get($bookId, $db);

closeDbConnection($db);

if (!is_null($book)) {
    $chaptersCount = $book->getChaptersCount($db);
    $smarty->assign("title", "authorsite.org - bibliography - books");

    if (count($book->editors) == 0) {
        $smarty->assign("hasEditors", false);
    }
    else {
        $smarty->assign("hasEditors", true);
    }

    $smarty->assign("book", $book);
    $smarty->assign("chaptersCount", $chaptersCount);
    $smarty->display("bib/book.tpl");
}
else {
    require ('../errors/404.php5');
}

?>
 
 
 
