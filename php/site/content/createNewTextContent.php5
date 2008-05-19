<?php
require_once('../shared/utils/initPage.php5');
require_once('../shared/utils/db.php5');
require_once('../shared/utils/utils.php5');
require_once('types/TextContent.php5');
require_once('../shared/types/Individual.php');

$hasInputErrors = false;
if (!isset($_POST['name'])) {
    $smarty->assign("generalErrorMessage", "Please supply a name!");
    $hasInputErrors = true;
}

$suppliedName = getCleanContentName($_POST['name']);
if (is_null($suppliedName)) {
    $smarty->assign("generalErrorMessage", "Please supply an appropriate name (A-Z, a-z, 0-9, and _ or -)");
    $hasInputErrors;
}

$suppliedTitle = getCleanTitle($_POST['title']);
if (is_null($suppliedTitle)) {
    $smarty->assign("generalErrorMessage", "Please supply an appropriate title");
}
ob_flush();
$newTextContent = $_POST['newTextContent'];
// TODO we should now validate the submitted text for dodgy stuff

$db = openDbConnectionToWrite();
$author = Individual::get($_SESSION['systemuser_id'], $db);
$textContent = new TextContent(1, $title, date_create(), date_create(), $suppliedName, "text/html", $newTextContent,  $author);

// do the insert into the database, get the ID
TextContent::insert($textContent, $author, $db);

// create a message ("created text content with ID x" for smarty)

$smarty->assign("textContent", $textContent);
$smarty->display('content/textContent.tpl');


?>