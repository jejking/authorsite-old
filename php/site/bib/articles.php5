<?php
require('../inc/headers.php5');
require('../inc/db.php5');
require('../inc/utils.php5');


DEFINE ('BROWSE_ALL_ARTICLES_QUERY', "select a.id as article_id, a.pages, a.issue, a.volume, a.journal_id, w.createdAt, w.updatedAt, w.date, w.toDate, w.title, wwp.workProducerType, h.DTYPE, h.id as human_id, h.name, h.place, h.givenNames
from article a, work w, work_workproducers wwp, human h
where a.id = w.id and w.id = wwp.work_id and wwp.abstractHuman_id = h.id
order by w.title ASC LIMIT ?, ?");

DEFINE('COUNT_ALL_ARTICLES_QUERY', "select count(*) from article");

DEFINE ('BROWSE_ARTICLES_BY_JOURNAL_QUERY', "select a.id as article_id, a.pages, a.issue, a.volume, a.journal_id, w.createdAt, w.updatedAt, w.date, w.toDate, w.title, wwp.workProducerType, h.DTYPE, h.id as human_id, h.name, h.place, h.givenNames
from article a, work w, work_workproducers wwp, human h
where a.journal_id = ? and a.id = w.id and w.id = wwp.work_id and wwp.abstractHuman_id = h.id
order by w.title ASC LIMIT ?, ?");

DEFINE ('COUNT_ARTICLES_BY_JOURNAL_QUERY', "select count(*) from article a where a.journal_id = ?");

DEFINE ('BROWSE_ARTICLES_BY_WORKPRODUCER_QUERY', "select a.id, a.pages, a.issue, a.volume, a.journal_id, w.createdAt, w.updatedAt, w.date, w.toDate, w.title, wwp.workProducerType, h.DTYPE, h.id, h.name, h.place, h.givenNames
from article a, work w, work_workproducers wwp, human h
where a.id = w.id and w.id = wwp.work_id and wwp.abstractHuman_id = ?
order by w.title ASC LIMIT ?, ?");

DEFINE ('COUNT_ARTICLES_BY_WORKPRODUCER_QUERY', "select count(a.id) from article a, work w, work_workproducers wwp, human h where a.id = w.id and w.id = wwp.work_id and wwp.abstractHuman_id = ?");

$db = openDbConnection();

/*
select a.id as article_id, a.pages, a.issue, a.volume, a.journal_id,
w.date, w.toDate, w.title, j.title, wwp.workProducerType, h.DTYPE, h.id as human_id,
h.name, h.place, h.givenNames
from article a, work w, work j, work_workproducers wwp, human h
where a.id = w.id and a.journal_id = j.id and w.id = wwp.work_id and wwp.abstractHuman_id = h.id
order by w.title ASC LIMIT 1, 10; */


$itemCount = 0;
$items = null;

$journalId = getId( $_GET['journal_id']);
$workProducerId = getId( $_GET['workproducer_id']);

// do count for paging purposes

if ( $journalId > 0) {
  $itemCount = getFirstColumnFromResult(doParameterisedQuery($db, COUNT_ARTICLES_BY_JOURNAL_QUERY, array($journalId)));
}
else if (getId( $_GET['workproducer_id']) > 0) {
  $itemCount = getFirstColumnFromResult(doParameterisedQuery($db, COUNT_ARTICLES_BY_JOURNAL_QUERY, array($workProducerId)));
}
else {
  $itemCount = doCount("article", $db);
}

$pageNumber = getPageNumber($_GET['pageNumber'], PAGE_SIZE, $itemCount);

// now go get the data
if ( $journalId > 0) {
 $items = doParameterisedBrowseQuery($db, BROWSE_ARTICLES_BY_JOURNAL_QUERY, array($journalId), $pageNumber, PAGE_SIZE);
}
else if (getId( $_GET['workproducer_id']) > 0) {
  $items = doParameterisedBrowseQuery($db, BROWSE_ARTICLES_BY_WORKPRODUCER_QUERY, array($workProducerId), $pageNumber, PAGE_SIZE);
}
else {
  $items = doBrowseQuery($db, BROWSE_ALL_ARTICLES_QUERY, $pageNumber, PAGE_SIZE);
}

// render the data
function renderArticle($item) {
 /*
  "select a.id, a.pages, a.issue, a.volume, a.journal_id, w.createdAt, w.updatedAt, w.date, w.toDate, w.title, wwp.workProducerType, h.DTYPE, h.id, h.name, h.place, h.givenNames
from article a, work w, work_workproducers wwp, human h
where a.id = w.id and w.id = wwp.work_id and wwp.abstractHuman_id = ?
*/
  echo "<i>";
  echo $item['title'];
  echo "</i>, ";
  echo $item['name'];
  echo ", ";
  echo $item['givenNames'];
}

?>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Articles</title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>Articles</h1>
        <?php
        foreach ($items as $item) {
          echo "<p>\n";
          renderArticle($item);
          echo "</p>";
          
        }
        renderPaging("articles.php5", $pageNumber, $itemCount);
        ?>
    </body>
</html> 

<?php
$result = null;
closeDbConnection($db);
?>
