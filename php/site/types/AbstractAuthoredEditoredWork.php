<?php
include 'AbstractWork.php';
abstract class AbstractAuthoredEditoredWork extends AbstractWork {
    
    public $authors;
    public $editors;
    
    function __construct($id, $title, $fromDate, $toDate, $authors, $editors) {
        parent::__construct($id, $title, $fromDate, $toDate);
        $this->authors = $authors;
        $this->editors = $editors;
    }
}
?>