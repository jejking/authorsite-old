<?php
session_start();
require_once ('../login/types/SystemUser.php5');
require_once('../libs/smarty/Smarty.class.php');

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
error_reporting(E_ALL);

?>