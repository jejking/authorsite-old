<?php
require_once('libs/smarty/Smarty.class.php');

$smarty = new Smarty();

$smarty->template_dir = 'templates/templates/';
$smarty->compile_dir  = 'templates/templates_c/';
$smarty->config_dir   = 'templates/configs/';
$smarty->cache_dir    = 'templates/cache/';

$smarty->assign('name','Ned');

//** un-comment the following line to show the debug console
//$smarty->debugging = true;

$smarty->display('index.tpl');
?>