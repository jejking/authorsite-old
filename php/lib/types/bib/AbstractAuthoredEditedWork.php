<?php
require_once ('types/shared/AbstractWork.php');
abstract class AbstractAuthoredEditedWork extends AbstractWork {
    
    public $authors;
    public $editors;
    
    /**
     * Constructs instance.
     *
     * @param integer $id
     * @param DateTime $createdAt
     * @param DateTime $updatedAt
     * @param string $title
     * @param DateTime $fromDate
     * @param DateTime $toDate
     * @param array $authors
     * @param array $editors
     */
    function __construct($id, $createdAt, $updatedAt, $title, $fromDate, $toDate, $authors, $editors) {
        parent::__construct($id, $createdAt, $updatedAt, $title, $fromDate, $toDate);
        $this->authors = $authors;
        $this->editors = $editors;
    }
}
?>