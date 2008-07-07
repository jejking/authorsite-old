<?php
ob_start();
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/content/TextContent.php5');
$name = getCleanContentName($_GET['name']);
if (is_null($name)) {
    // do a 404
    include('../errors/404.php5');
}
else {
    $db = openDbConnection();
    $textContent = TextContent::getByName($name, $db);
    if (is_null($textContent)) {
        // if logged in and content does not exist, allow user to create it
        if (isset($_SESSION['systemuser_id'])) {
            include ('newTextContent.php5');
        }
        else {
            // otherwise 404
            include('../errors/404.php5');
        }
    }
    else {
        // normal case, render content...
        if (isset($_SESSION['systemuser_id'])) {
            $smarty->assign('editable', true);
        }
        else {
            $smarty->assign('editable', false);
        }
        $smarty->assign('textContent', $textContent);
        $smarty->assign('title', $textContent->title);
        $smarty->display('content/textContent.tpl');
    }
}
?>