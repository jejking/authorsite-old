<?php
include 'AbstractHuman.php';

final class Individual extends AbstractHuman {
    
    public $givenNames;
    
    function  __construct($id, $name, $nameQualification, $givenNames) {
        parent::__construct($id, $name, $nameQualification);
        $this->givenNames = $givenNames;
    }
}
?>