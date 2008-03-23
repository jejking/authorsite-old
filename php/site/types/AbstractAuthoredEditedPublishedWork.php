<?php
include 'AbstractAuthoredEditoredWork.php';
abstract class AuthoredEditedPublishedWork extends AbstractAuthoredEditedWork  {
    
    public $publisher;
    
    function __construct($id, $title, $authors, $editors, $publisher) {
        parent::__construct($id, $title, $authors, $editors);
        $this->publisher = $publisher;
    }
}
?>