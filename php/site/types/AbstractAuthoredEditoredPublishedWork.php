<?php
include 'AbstractAuthoredEditoredWork.php';
abstract class AuthoredEditoredPublishedWork extends AbstractAuthoredEditoredWork  {
    
    public $publisher;
    
    function __construct($id, $title, $authors, $editors, $publisher) {
        parent::__construct($id, $title, $authors, $editors);
        $this->publisher = $publisher;
    }
}
?>