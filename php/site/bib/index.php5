<?php
ob_start();
require_once('utils/initPage.php5');
require_once('utils/db.php5');
require_once('types/shared/Individual.php');
require_once('types/shared/Collective.php');
require_once('types/bib/Article.php');
require_once('types/bib/Book.php');
require_once('types/bib/Journal.php');
require_once('types/bib/Chapter.php');
require_once('types/bib/Thesis.php');
require_once('types/bib/ExternalWebResource.php');
$db = openDbConnection();

// do counts
$individualsCount = Individual::count($db);
$collectivesCount = Collective::count($db);
$booksCount = Book::count($db);
$articlesCount = Article::count($db);
$chaptersCount = Chapter::count($db);
$thesesCount = Thesis::count($db);
$externalWebResourcesCount = ExternalWebResource::count($db);
$journalsCount = Journal::count($db);

closeDbConnection($db);

$smarty->assign('individualsCount', $individualsCount);
$smarty->assign('collectivesCount', $collectivesCount);
$smarty->assign('booksCount', $booksCount);
$smarty->assign('articlesCount', $articlesCount);
$smarty->assign('chaptersCount', $chaptersCount);
$smarty->assign('thesesCount', $thesesCount);
$smarty->assign('externalWebResourcesCount', $externalWebResourcesCount);
$smarty->assign('journalsCount', $journalsCount);
$smarty->display('bib/index.tpl');

?>