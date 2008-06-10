<?php
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/content/TextContent.php5');
$db = openDbConnection();

$content_name = 'index';

$textContent = TextContent::getByName($content_name, $db);

if (is_null($textContent)) {
    
    $textContent = TextContent::getDefaultIndex();    
}
$smarty->assign('textContent', $textContent);
$smarty->display('content/textContent.tpl');


?>