<?php
ob_start();
$host  = $_SERVER['HTTP_HOST'];
$uri   = rtrim(dirname($_SERVER['PHP_SELF']), '/\\');
$extra = 'content/index.php5';
header("Location: http://$host$uri/$extra");
ob_flush();
exit;
?>
