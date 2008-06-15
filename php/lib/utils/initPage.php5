<?php
session_start();
header("Content-Type: text/html; charset=utf-8");
require_once ('types/login/SystemUser.php5');
require_once('smarty/Smarty.class.php');

$smarty = new Smarty();

$smarty->template_dir = '../smarty/templates/';
$smarty->compile_dir  = '../smarty/templates_c/';
$smarty->config_dir   = '../smarty/configs/';
$smarty->cache_dir    = '../smarty/cache/';

//work out active tab
$uri = $_SERVER['REQUEST_URI'];
$uriParts = explode("/", $uri );
$smarty->assign("activeTab", $uriParts[1]);

define('PAGE_SIZE', 20);

?>
