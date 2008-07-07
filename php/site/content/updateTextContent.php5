<?php
ob_start();
require_once('utils/initPage.php5');
require_once('../login/loginNeeded.php5');
require_once('utils/utils.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/content/TextContent.php5');

$db = openDbConnectionToWrite();

$id = getId($_POST['id']);
$title = getCleanTitle($_POST['title']);
$name = $_POST['name'];

$textContent = $_POST['textContent'];
$updatedById = $_SESSION['systemuser_id'];
TextContent::updateTitleAndTextContent($id, $updatedById, $title, $textContent, $db);

$host  = $_SERVER['HTTP_HOST'];
$uri   = rtrim(dirname($_SERVER['PHP_SELF']), '/\\');
$extra = 'text/' . $name;
header("Location: http://$host$uri/$extra");
ob_flush();

?>