<?php
require_once('../shared/utils/headers.php5');
require_once('../shared/utils/db.php5');
require_once('../shared/utils/utils.php5');
require_once('types/Thesis.php');


try {
    $db = openDbConnection();

    $thesesCount = Thesis::count($db);
    $pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $thesesCount);

    $theses = Thesis::getPage($pageNumber, AbstractEntry::PAGE_SIZE, $db);
    closeDbConnection($db);
    require_once('view/renderTheses.php5');
}
catch (PDOException $pdoExeption) {
    require_once('errors/500.php5');
}
?>
 
 
