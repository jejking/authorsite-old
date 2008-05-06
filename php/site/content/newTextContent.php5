<?php
require_once('../shared/utils/initPage.php5');
require_once('../shared/utils/utils.php5');
require_once('_fckUtil.php5');
if (!isset($newTextContent)) {
    $fckEditor = getFckEditor($smarty, "newTextContent", "<p>Please enter some content here.</p>");    
}
else {
    $fckEditor = getFckEditor($smarty, "newTextContent", $newTextContent);
}


if (!isset($name)) {
    if (isset($_GET['name'])) {
        $name = getCleanContentName($_GET['name']);
    }    
}


if (isset($name)) {
    $smarty->assign("name", $name);
    
}
$smarty->display("content/newTextContent.tpl");


?>