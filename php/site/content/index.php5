<?php
require_once('../libs/smarty/Smarty.class.php');

$smarty = new Smarty();

$smarty->template_dir = '../smarty/templates/';
$smarty->compile_dir  = '../smarty/templates_c/';
$smarty->config_dir   = '../smarty/configs/';
$smarty->cache_dir    = '../smarty/cache/';

$smarty->assign('name','Ned');

//** un-comment the following line to show the debug console
//$smarty->debugging = true;

$smarty->display('index.tpl');
?>