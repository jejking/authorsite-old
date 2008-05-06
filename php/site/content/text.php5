<?php
ob_start();
require_once('../shared/utils/initPage.php5');
require_once('../shared/utils/db.php5');
require_once('../shared/utils/utils.php5');
require_once('types/TextContent.php5');
$name = getCleanContentName($_GET['name']);
if (is_null($name)) {
    // do a 404
    include('../errors/404.php5');
}
else {
    $db = openDbConnection();
    $textContent = TextContent::getByName($name, $db);
    if (is_null($textContent)) {
        if (isset($_SESSION['systemuser_id'])) {
            include ('newTextContent.php5');
        }
        else {
            include('../errors/404.php5');
        }
    }
    else {
        $smarty->assign('textContent', $textContent);
        $smarty->display('content/textContent.tpl');
    }
}
?>