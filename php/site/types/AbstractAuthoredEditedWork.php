<?php
require_once ('AbstractWork.php');
abstract class AbstractAuthoredEditedWork extends AbstractWork {
    
    public $authors;
    public $editors;
    
    function __construct($id, $title, $fromDate, $toDate, $authors, $editors) {
        parent::__construct($id, $title, $fromDate, $toDate);
        $this->authors = $authors;
        $this->editors = $editors;
    }
}
?>