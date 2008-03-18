<?php
final class Journal extends AbstractWork {
    
    function __construct($id, $title, $fromDate, $toDate) {
        parent::__construct($id, $title, $fromDate, $toDate);
    }
}
?>