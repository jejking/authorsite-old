<?php
require_once('../shared/utils/initPage.php5');
require_once('../shared/utils/db.php5');
require_once('../shared/utils/utils.php5');
$content_name = getCleanContentName($_GET['name']);
if (is_null($content_name)) {
    // do a 404
    include('../errors/404.ph5');
}
else {
    $db = openDbConnection();
        
}
?>