<?php
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
    require_once('view/renderBooks.php5');
}
catch (PDOException $pdoExeption) {
    require_once('../errors/500.php5');
}
?> 
