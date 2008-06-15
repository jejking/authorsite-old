<?php
require_once('types/bib/AbstractAuthoredEditedWork.php');
require_once('types/bib/Journal.php');
final class Article extends AbstractAuthoredEditedWork {
    
    const GET_ARTICLES_CORE_QUERY = 
    	'SELECT a.id, a.pages, a.issue, a.volume, a.journal_id, wa.createdAt AS articleCreatedAt,
    	    wa.updatedAt AS articleUpdatedAt, wa.date, wa.toDate, wa.title AS article_title, 
    		wj.createdAt AS journalCreatedAt, wj.updatedAt AS journalUpdatedAt, 
    		wj.title AS journal_title, wj.date AS journal_date, wj.toDate AS journal_toDate
		 FROM article a, work wa, work wj
		 WHERE a.id = wa.id
		 AND a.journal_id = wj.id
		 ORDER BY wa.title ASC';
    
    const GET_SINGLE_ARTICLE_CORE_QUERY = 
         'SELECT a.id, a.pages, a.issue, a.volume, a.journal_id, wa.createdAt AS articleCreatedAt, 
           wa.updatedAt AS articleUpdatedAt, wa.date, wa.toDate, wa.title AS article_title, 
           wj.createdAt AS journalCreatedAt, wj.updatedAt AS journalUpdatedAt, 
           wj.title AS journal_title
		 FROM article a, work wa, work wj
		 WHERE a.id = wa.id
		 AND a.journal_id = wj.id
		 and a.id = ?';
    
    /**
     * Journal in which the article was published.
     *
     * @var Journal
     */    
    public $journal;
    
    /**
     * Volume in which the article was published.
     *
     * @var string
     */
    public $volume;
    
    /**
     * Journal issue in which the article was published.
     *
     * @var string
     */
    public $issue;
    
    /**
     * Pages on which the article can be found.
     *
     * @var string
     */
    public $pages;
    
    /**
     * Creates new article instance.
     *
     * @param integer $id
     * @param DateTime $createdAt
     * @param DateTime $updatedAt
     * @param string $title
     * @param DateTime $fromDate
     * @param DateTime $toDate
     * @param array $authors
     * @param array $editors
     * @param Journal $journal
     * @param string $volume
     * @param string $issue
     * @param string $pages
     */
    function __construct($id, $createdAt, $updatedAt, $title, $fromDate, $toDate, $authors, $editors, $journal, $volume, $issue, $pages) {
        parent::__construct($id, $createdAt, $updatedAt, $title, $fromDate, $toDate, $authors, $editors);
        $this->journal = $journal;
        $this->volume = $volume;
        $this->issue = $issue;
        $this->pages = $pages;
    }
    
    static function count($db) {
        return AbstractEntry::doCount("article", $db);
    }

    static function getPage($pageNumber, $pageSize, $db) {
        $coreResultSet = AbstractEntry::doPagingQuery(Article::GET_ARTICLES_CORE_QUERY, $pageNumber, $pageSize, $db);
        $resultArray = array();
        foreach ($coreResultSet as $coreResultSetRow) {
            $workProducers = AbstractWork::getWorkProducersForWork($coreResultSetRow['id'], $db);
            $article = Article::buildArticle($coreResultSetRow, $workProducers);
            array_push($resultArray, $article);
        }
        return $resultArray;
    }

    static function get($id, $db) {
        $coreResultSet = AbstractEntry::doQueryWithIdParameter(Article::GET_SINGLE_ARTICLE_CORE_QUERY, $id, $db);
        if (count($coreResultSet) == 0) {
            return null;
        }
        $workProducers = AbstractWork::getWorkProducersForWork($id, $db);
        $coreResultSetRow = $coreResultSet[0];
        return Article::buildArticle($coreResultSetRow, $workProducers);
    }

    static function getAll($db) {
        $coreResultSet = AbstractEntry::doSimpleQuery(Article::GET_ARTICLES_CORE_QUERY, $db);
        $resultArray = array();
        foreach ($coreResultSet as $coreResultSetRow) {
            $workProducers = AbstractWork::getWorkProducersForWork($coreResultSetRow['id'], $db);
            $article = Article::buildArticle($coreResultSetRow, $workProducers);
            array_push($resultArray, $article);
        }
        return $resultArray;
    }
    
    
    static function insert($individual, $user, $db) {
        // TODO build
    }
    
    static function delete($id, $db) {
        // TODO
    }
    
    static function isSafeToDelete($id, $db) {
        // TODO
    }
    
    private static function buildArticle($coreResultSetRow, $workProducers) {
        $id = $coreResultSetRow['id'];
        $createdAt = new DateTime($coreResultSetRow['articleCreatedAt']);
        $updatedAt = new DateTime($coreResultSetRow['articleUpdatedAt']);
        $title = $coreResultSetRow['article_title'];
        $fromDate = $coreResultSetRow['date'];
        $toDate = $coreResultSetRow['toDate'];
        $authors = $workProducers[Constants::AUTHOR];
        $editors = $workProducers[Constants::EDITOR];
        
        $journal = new Journal($coreResultSetRow['journal_id'], new DateTime($coreResultSetRow['journalCreatedAt']), 
                        new DateTime($coreResultSetRow['journalUpdatedAt']),
                        $coreResultSetRow['journal_title'], $coreResultSetRow['journal_date'], 
                        $coreResultSetRow['journal_toDate']);
        
        $volume = $coreResultSetRow['volume'];
        $issue = $coreResultSetRow['issue'];
        $pages = $coreResultSetRow['pages'];
        return new Article($id, $createdAt, $updatedAt, $title, $fromDate, $toDate, $authors, $editors, $journal, $volume, $issue, $pages);
    }
}


?>
