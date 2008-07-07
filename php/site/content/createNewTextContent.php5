<?php
ob_start();
require_once('utils/initPage.php5');
require_once('../login/loginNeeded.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/content/TextContent.php5');
require_once('types/shared/Individual.php');
require_once('_fckUtil.php5');

$hasInputErrors = false;
if (!isset($_POST['name'])) {
    $smarty->append("generalErrorMessage", "Please supply a name!");
    $hasInputErrors = true;
}

$suppliedName = getCleanContentName($_POST['name']);
if (is_null($suppliedName)) {
    $smarty->append("generalErrorMessage", "Please supply an appropriate name (A-Z, a-z, 0-9, and _ or -)");
    $hasInputErrors = true;
}
$smarty->assign("name", $suppliedName);

$suppliedTitle = getCleanTitle($_POST['title']);
if (is_null($suppliedTitle)) {
    $smarty->append("generalErrorMessage", "Please supply an appropriate title");
    $hasInputErrors = true;
}
else {
    $smarty->assign("title", $suppliedTitle);
}

$newTextContent = $_POST['newTextContent'];
// TODO we should now validate the submitted text for dodgy stuff

if (is_null($newTextContent)) {
    $smarty->append("generalErrorMessage", "Please supply some content");
    $hasInputErrors = true;
}
else if (strlen($newTextContent) == 0) {
    $smarty->append("generalErrorMessage", "Please supply some content");
    $hasInputErrors = true;
}
    
if (!$hasInputErrors) {
    $db = openDbConnectionToWrite();
    $author = Individual::get($_SESSION['systemuser_id'], $db);
    $textContent = new TextContent(1, new DateTime(), new DateTime(), $suppliedTitle, new DateTime(), new DateTime(),
         $suppliedName, "text/html", $newTextContent, $author);

    // do the insert into the database
    $id = TextContent::insert($textContent, $author, $db);

    // do a redirect to the page

    $host  = $_SERVER['HTTP_HOST'];
    $uri   = rtrim(dirname($_SERVER['PHP_SELF']), '/\\');
    $extra = 'content/text/' . $suppliedName;
    header("Location: http://$host$uri/$extra");
    ob_flush();
}
else {
    // rerender new text content template
    $fckEditor = getFckEditor($smarty, "newTextContent", $newTextContent);
    $smarty->display("content/newTextContent.tpl");
}

?>