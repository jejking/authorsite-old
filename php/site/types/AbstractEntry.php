<?php
abstract class AbstractEntry {
    
    public $id;

    function __construct($id) {
        $this->id = $id;
    }
}
?>