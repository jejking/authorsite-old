<?php
include 'AbstractAuthoredEditedPublishedWork.php';

final class WebResource extends AuthoredEditoredPublishedWork {
    
    public $url;
    public $lastChecked;
    public $lastStatusCode;
    
    function __construct($id, $title, $authors, $editors, $publisher, $url, $lastChecked, $lastStatusCode) {
        parent::__construct($id, $title, $authors, $editors, $publisher);
        $this->url = $url;
        $this->lastChecked = $lastChecked;
        $this->lastStatusCode = $lastStatusCode;
    }
    
}
?>