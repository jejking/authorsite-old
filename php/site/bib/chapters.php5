<?php
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/bib/Chapter.php');


try {
    $db = openDbConnection();

    $chaptersCount = Chapter::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $chaptersCount);

    $chapters = Chapter::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    require_once('view/renderChapters.php5');
}
catch (PDOException $pdoExeption) {
    require_once('../errors/500.php5');
}
?> 
