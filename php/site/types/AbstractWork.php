<?php
require_once 'AbstractEntry.php';
abstract class AbstractWork extends AbstractEntry {

    const ARTICLE = 'ARTICLE';
    const JOURNAL = 'JOURNAL';
    const BOOK = 'BOOK';
    const THESIS = 'THESIS';
    const CHAPTER = 'CHAPTER';
    const WEB_RESOURCE = 'WEB_RESOURCE';
    
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