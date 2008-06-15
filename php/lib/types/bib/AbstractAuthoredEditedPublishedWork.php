<?php
require_once('types/bib/AbstractAuthoredEditedWork.php');
abstract class AbstractAuthoredEditedPublishedWork extends AbstractAuthoredEditedWork  {
    
    public $publisher;
    
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
     * @param AbstractHuman $publisher
     */
    function __construct($id, $createdAt, $updatedAt, $title, $fromDate, $toDate, 
            $authors, $editors, $publisher) {
        
        parent::__construct($id, $createdAt, $updatedAt, $title, $fromDate, $toDate, $authors, $editors);
        $this->publisher = $publisher;
    }
}
?>