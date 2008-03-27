<?php
require_once('../inc/headers.php5');
require_once('../inc/db.php5');
require_once('../inc/utils.php5');
require_once('../types/Chapter.php');


try {
    $db = openDbConnection();

    $chaptersCount = Chapter::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $chaptersCount);

    $chapters = Chapter::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    require_once('../view/renderChapters.php5');
}
catch (PDOException $pdoExeption) {
    require_once('../view/500.php');
}
?> 
