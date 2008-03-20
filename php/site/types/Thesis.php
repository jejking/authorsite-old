<?php
include 'AbstractWork.php';

final class Thesis extends AbstractWork {
    
    public $author;
    public $awardingBody;
    
    function __construct($id, $title, $fromDate, $toDate, $author, $awardingBody) {
        parent::__construct($id, $title, $fromDate, $toDate);
        $this->author = $author;
        $this->awardingBody = $awardingBody; 
    }
}
?>