<?php
include 'AbstractEntry.php';
abstract class AbstractWork extends AbstractEntry {
    
    public $title;
    public $fromDate;
    public $toDate;
    
    function __construct($id, $title, $fromDate, $toDate) {
        parent::__construct($id);
        $this->title = $title;
        $this->fromDate = $fromDate;
        $this->toDate = $toDate;
    }
}
?>