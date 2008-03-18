<?php
include 'AbstractHuman.php';
final class Collective extends AbstractHuman {
    
    public $place;
    
    function __construct($id, $name, $nameQualification, $place) {
        parent::__construct($id, $name, $nameQualification);
        $this->place = $place;
    }
}
?>