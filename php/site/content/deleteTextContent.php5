<?php
ob_start();
require_once('utils/initPage.php5');
require_once('../login/loginNeeded.php5');
require_once('utils/utils.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/content/TextContent.php5');
require_once('_fckUtil.php5');

$db = openDbConnection();
$id = getId($_GET['id']);

$textContent = TextContent::get($id, $db);

if (is_null($textContent)) {
    require_once('../errors/404.php5');
}
else {
    $fckEditor = getFckEditor($smarty, "textContent", $textContent->content);
    $smarty->assign("textContent", $textContent);
    $smarty->display("content/confirmTextContentDeletion.tpl");
}

?>