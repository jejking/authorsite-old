<?php

final class Article extends AbstractAuthoredEditoredWork {
    
    public $journal;
    public $volume;
    public $issue;
    public $pages;
    
    function __construct($id, $title, $fromDate, $toDate, $authors, $editors, $journal, $volume, $issue, $pages) {
        parent::__construct($id, $title, $fromDate, $toDate, $authors, $editors);
        $this->journal = $journal;
        $this->volume = $volume;
        $this->issue = $issue;
        $this->pages = $pages;
    }
    
}


?>
