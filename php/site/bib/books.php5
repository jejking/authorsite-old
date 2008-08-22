<?php
ob_start();
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/bib/Book.php');


try {
    $db = openDbConnection();

    $booksCount = Book::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $booksCount);

    $books = Book::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    
    $smarty->assign("title", "authorsite.org - bibliography - books");
    
    $smarty->assign("booksCount", $booksCount);
    $smarty->assign("pageNumber", $pageNumber);
    $smarty->assign("books", $books);
    $smarty->display("bib/books.tpl");
    
}
catch (PDOException $pdoExeption) {
    require_once('../errors/500.php5');
}
?> 
