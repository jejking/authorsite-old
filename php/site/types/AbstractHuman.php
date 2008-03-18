<?php
include 'AbstractEntry.php';
abstract class AbstractHuman extends AbstractEntry {
    
    public $name;
    public $nameQualification;

    function __construct($id, $name, $nameQualification) {
        parent::__construct($id);
        $this->name = $name;
        $this->nameQualification = $nameQualification;
    }
}
?>