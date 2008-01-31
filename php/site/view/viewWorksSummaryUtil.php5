<?php


function renderWorksProducedSummary($workType, $resultSet) {
  echo('<tr>');
  echo('<td>');
  echo ($workType);
  echo('</td>');
  echo('<td>');
  foreach ($resultSet as $resultRow) {
    renderWorksProductionTypeSummary($resultRow);
  }
  echo('</td>');
  echo('</tr>');
}
  
function renderWorksProductionTypeSummary($resultRow) {
  $workProducerTypeMap = array('PUBLISHER' => 'Published', 'AUTHOR' => 'Authored', 'EDITOR' => 'Edited', 'AWARDING_BODY' => 'Awarded');
  $key = $resultRow['workproducertype'];
  echo( $workProducerTypeMap[$key] );
  echo(' (' . $resultRow['theCount'] . ') ');
}

?>

<table>
  <?php 
  
  if (count($booksProducedResultSet) > 0 ) {
    renderWorksProducedSummary("Books", $booksProducedResultSet);
  }
  if (count($articlesProducedResultSet) > 0) {
    renderWorksProducedSummary("Articles", $articlesProducedResultSet);
  }
  if (count($thesesProducedResultSet) > 0) {
    renderWorksProducedSummary("Theses", $thesesProducedResultSet);
  }
  if (count($chaptersProducedResultSet) > 0)  {
    renderWorksProducedSummary("Chapters", $chaptersProducedResultSet);
  }
  if (count($webResourcesProducedResultSet) > 0) {
    renderWorksProducedSummary("Web Resources", $webResourcesProducedResultSet);
  }
  
  ?>

</table>
