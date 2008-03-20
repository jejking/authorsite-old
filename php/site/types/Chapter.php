<?php
include 'AbstractAuthoredEditedWork.php';

final class Chapter extends AbstractAuthoredEditoredWork {
    
    public $book;
    
    function __construct($id, $title, $fromDate, $toDate, $authors, $editors, $book) {
        parent::__construct($id, $title, $fromDate, $toDate, $authors, $editors);
        $this->book = $book;
    }
}
?>