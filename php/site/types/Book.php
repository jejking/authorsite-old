<?php
include 'AuthoredEditoredPublishedWork.php';
final class Book extends AuthoredEditoredPublishedWork {
    
    function __construct($id, $title, $authors, $editors, $publisher) {
        parent::__construct($id, $title, $authors, $editors, $publisher);
    }
}
?>