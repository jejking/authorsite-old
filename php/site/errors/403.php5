<?php
header('HTTP/1.1 403 Not authorized');
ob_flush();
$smarty->display("errors/403.tpl");
exit();
?>