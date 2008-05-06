<?php
header('HTTP/1.1 404 Not found');
ob_flush();
$smarty->display("errors/404.tpl");
?>