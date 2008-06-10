<?php
ob_start();
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('utils/utils.php5');
require_once('types/login/SystemUser.php5');

$hasInputErrors = false;
if (isset($_SESSION['systemuser_id'])) {
    // already logged in
    $smarty->assign("generalErrorMessage", "You are already logged in");
    displayLoginPage($smarty);
}
else {
    // are both input fields actually filled in?
    if (!isset($_POST['username']) && !isset($_POST['password'])) {
       displayLoginPage($smarty);
    }
    else {
       // validate username is plausible
       if (!isset($_POST['username'])) {
           $smarty->assign("usernameErrorMessage", "Please supply a username");
           $hasInputErrors = true;
       }
       else {
           if (preg_match("/^[a-zA-Z0-9]+$/", $_POST['username']) != 1) {
               $smarty->assign("usernameErrorMessage", "Please supply a plausible username");
               $smarty->assign("username", $_POST['username']);
               $hasInputErrors = true;
           }
       }
       if (!isset($_POST['password'])) {
           $smarty->assign("passwordErrorMessage", "Please supply a password");
           $hasInputErrors = true;
       }
       if($hasInputErrors) {
           displayLoginPage($smarty);
       }
       else {
           // attempt login...
           $db = openDbConnection();
           $systemUser = SystemUser::getByUsername($_POST['username'], $db);
           if (is_null($systemUser)) {
               $smarty->assign("generalErrorMessage", "Could not find username and password match");
               displayLoginPage($smarty);
           }
           else {
               // do hashes match?
               if ($systemUser->checkPassword($_POST['password'])) {
                   // we match!!
                   $_SESSION['systemuser_id'] = $systemUser->id;
                   $_SESSION['systemuser_username'] = $systemUser->username;
                   $smarty->display("login/loginSuccessful.tpl");
               }
               else {
                   $smarty->assign("generalErrorMessage", "Could not find username and password match");
                   displayLoginPage($smarty);
               }
           }
       }
    }
}

function displayLoginPage($smarty) {
    ob_flush();
    $smarty->display("login/login.tpl");
}

?>