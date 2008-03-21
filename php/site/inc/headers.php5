<?php
ob_start();
if (stristr($_SERVER["HTTP_ACCEPT"], "application/xhtml+xml") || stristr($_SERVER["HTTP_USER_AGENT"],"W3C_Validator")) {
    header("Content-Type: application/xhtml+xml; charset=utf-8");
    header("Vary: Accept");
    echo("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
    echo('<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">');
} else {
    header("Content-Type: text/html; charset=utf-8");
    header("Vary: Accept");
}

// cache instructions could come here 
?> 
