<?php
require_once('../shared/utils/headers.php5');
require_once('../shared/utils/db.php5');
require_once('../shared/utils/utils.php5');
require_once('types/Book.php');

$db = openDbConnection();

$bookId = getId($_GET['id']);
  
$book = Book::get($bookId, $db);

if (!is_null($book)) {
    $chaptersCount = $book->getChaptersCount($db);
    require('view/renderBook.php5');
}
else {
    require ('../errors/404.php5');
}
closeDbConnection($db);
?>
 
 
 
