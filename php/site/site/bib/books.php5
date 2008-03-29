<?php
require_once('../inc/headers.php5');
require_once('../inc/db.php5');
require_once('../inc/utils.php5');
require_once('../types/Book.php');


try {
    $db = openDbConnection();

    $booksCount = Book::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $booksCount);

    $books = Book::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    require_once('../view/renderBooks.php5');
}
catch (PDOException $pdoExeption) {
    require_once('../view/500.php');
}
?> 
