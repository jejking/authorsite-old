<?php
ob_start();
require_once('../shared/utils/initPage.php5');
if (isset($_SESSION['systemuser_id'])) {
    unset($_SESSION['systemuser_id']);
    unset($_SESSION['systemuser_username']);
    if (isset($_COOKIE[session_name()])) {
        setcookie(session_name(), '', time()-42000, '/');
    }
    session_destroy();
}
else {
    $smarty->assign("generalErrorMessage", "You cannot be logged out as you were not logged in!");
}

$smarty->display("login/logoutResult.tpl");
?>