<?php
require_once('../inc/headers.php5');
require_once('../inc/db.php5');
require_once('../inc/utils.php5');
require_once('../types/Book.php');

$db = openDbConnection();

$bookId = getId($_GET['id']);
  
$book = Book::get($bookId, $db);

if (!is_null($book)) {
    $chaptersCount = $book->getChaptersCount($db);
    require('../view/renderBook.php5');
}
else {
    require ('../view/404.php');
}
closeDbConnection($db);
?>
 
 
 
