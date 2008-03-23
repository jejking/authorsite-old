<?php
require_once('AuthoredEditedPublishedWork.php');
final class Book extends AuthoredEditedPublishedWork {
    
    function __construct($id, $title, $authors, $editors, $publisher) {
        parent::__construct($id, $title, $authors, $editors, $publisher);
    }
}
?>