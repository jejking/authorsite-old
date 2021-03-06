<?php
require_once 'types/shared/AbstractEntry.php';
require_once 'Constants.php';
/**
 * Class representing shared data and functionality 
 * between collectives and individuals.
 * 
 * @author jejking
 */
abstract class AbstractHuman extends AbstractEntry {
    
    const BOOKS_PRODUCED_QUERY =
        "SELECT workproducertype, count(wwp.work_id) AS theCount 
         FROM book b, work_workproducers wwp 
         WHERE wwp.abstractHuman_Id = ? 
         AND wwp.work_id = b.id 
         GROUP BY workproducertype";
        
    const ARTICLES_PRODUCED_QUERY = 
    	"SELECT workproducertype, count(wwp.work_id) AS theCount
    	FROM article a, work_workproducers wwp
    	WHERE wwp.abstractHuman_Id = ? 
    	AND wwp.work_id = a.id 
    	GROUP BY workproducertype";
    
    const THESES_PRODUCED_QUERY = 
    	"SELECT workproducertype, count(wwp.work_id) AS theCount
    	FROM thesis t, work_workproducers wwp 
    	WHERE wwp.abstractHuman_Id = ? 
    	AND wwp.work_id = t.id 
    	GROUP BY workproducertype";
    
    const CHAPTERS_PRODUCED_QUERY = 
    	"SELECT workproducertype, count(wwp.work_id) AS theCount
    	FROM chapter ch, work_workproducers wwp 
    	WHERE wwp.abstractHuman_Id = ? 
    	AND wwp.work_id = ch.id
    	GROUP BY workproducertype";

    const WEB_RESOURCES_PRODUCED_QUERY = 
    	"SELECT workproducertype, count(wwp.work_id) AS theCount
    	FROM webresource wr, work_workproducers wwp 
    	WHERE wwp.abstractHuman_Id = ? 
    	AND wwp.work_id = wr.id
    	GROUP BY workproducertype;";
    
    public $name;
    public $nameQualification;

    /**
     * Constructs abstract human.
     *
     * @param int $id
     * @param DateTime $createdAt
     * @param DateTime $updatedAt
     * @param string $name
     * @param string $nameQualification
     */
    function __construct($id, $createdAt, $updatedAt, $name, $nameQualification) {
        parent::__construct($id, $createdAt, $updatedAt);
        $this->name = $name;
        $this->nameQualification = $nameQualification;
    }
    
    /**
     * Gets work produced summary by work type and work producer type for the human (collective or individual)
     * 
     * @param dbConnection $db
     * @return array keyed on work type, in each array cell is a further array keyed on work producer type containing the count
     */
    function getWorksCountSummary($db) {
        $countsArray = array();
        
        // books
        $booksCount = AbstractEntry::doQueryWithIdParameter(AbstractHuman::BOOKS_PRODUCED_QUERY, $this->id, $db);
        if (count($booksCount) > 0) {
            $countsArray[Constants::BOOK] = AbstractHuman::buildHumanWorksCountSummary($booksCount);    
        }
        
        // articles
        $articlesCount = AbstractEntry::doQueryWithIdParameter(AbstractHuman::ARTICLES_PRODUCED_QUERY, $this->id, $db);
        if (count($articlesCount) > 0) {
            $countsArray[Constants::ARTICLE] = AbstractHuman::buildHumanWorksCountSummary($articlesCount);    
        }
                
        // theses
        $thesesCount = AbstractEntry::doQueryWithIdParameter(AbstractHuman::THESES_PRODUCED_QUERY, $this->id, $db);
        if (count($thesesCount) > 0) {
            $countsArray[Constants::THESIS] = AbstractHuman::buildHumanWorksCountSummary($thesesCount);    
        }
                
        // chapters
        $chaptersCount = AbstractEntry::doQueryWithIdParameter(AbstractHuman::CHAPTERS_PRODUCED_QUERY, $this->id, $db);
        if (count($chaptersCount) > 0) {
            $countsArray[Constants::CHAPTER] = AbstractHuman::buildHumanWorksCountSummary($chaptersCount);    
        }
                
        // web resources
        $webResourcesCount = AbstractEntry::doQueryWithIdParameter(AbstractHuman::WEB_RESOURCES_PRODUCED_QUERY, $this->id, $db);
        if (count($webResourcesCount) > 0) {
            $countsArray[Constants::WEB_RESOURCE] = AbstractHuman::buildHumanWorksCountSummary($webResourcesCount);    
        }
        
        return $countsArray;
    }
    
    private static function buildHumanWorksCountSummary($counts) {
        $countArray = array();
        foreach ($counts as $count) {
            $countArray[$count['workproducertype']] = $count['theCount'];
        }
        return $countArray;
    }
}
?>