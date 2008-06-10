<?php
require_once('AbstractAuthoredEditedWork.php');
abstract class AbstractAuthoredEditedPublishedWork extends AbstractAuthoredEditedWork  {
    
    public $publisher;
    
    function __construct($id, $title, $fromDate, $toDate,$authors, $editors, $publisher) {
        parent::__construct($id, $title, $fromDate, $toDate, $authors, $editors);
        $this->publisher = $publisher;
    }
}
?>