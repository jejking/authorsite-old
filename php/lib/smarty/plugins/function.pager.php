<?php
function smarty_function_pager($params, &$smarty) {
    // extract params from array
    $baseUrl = $params['baseUrl'];
    $pageNumber = $params['pageNumber'];
    $totalCount = $params['totalCount'];
    
    $return = "";
    renderPagingFirstPage(&$return, $baseUrl, $pageNumber, $totalCount);
    renderPagingPreviousPage(&$return, $baseUrl, $pageNumber, $totalCount);
    renderPagingCentralBlock(&$return, $baseUrl, $pageNumber, $totalCount);
    renderPagingNextPage(&$return, $baseUrl, $pageNumber, $totalCount);
    renderPagingLastPage(&$return, $baseUrl, $pageNumber, $totalCount);
    
    return $return;

}
    
    
    

function calculateMaxPageNumber($pageSize, $totalCount) {
  $maxPageNumber = intval($totalCount / $pageSize) - 1;
  if ($maxPageNumber < 0) {
    return 0;
  }
  $remainder = $totalCount % $pageSize;
  if ($remainder > 0) {
    $maxPageNumber = $maxPageNumber + 1;
  }
  return $maxPageNumber;
}
/**
 * Renders link to first page.
 *
 * @param string $return
 * @param string $baseUrl
 * @param int $pageNumber
 * @param int $totalCount
 */
function renderPagingFirstPage($return, $baseUrl, $pageNumber, $totalCount) {
  $return = $return . '<div class="pager">';
  $return = $return . '<span id="pager.first">';
  if ($pageNumber > 0) {
    $return = $return . '<a href="' . $baseUrl . '?pageNumber=0">&lt;&lt;</a> ';
  }
  else {
    $return = $return . '&lt;&lt; ';
  }
  $return = $return . ('</span>');
}

function renderPagingPreviousPage($return, $baseUrl, $pageNumber, $totalCount) {
  $return = $return . '<span id="pager.previous">';
  if ($pageNumber > 0) {
    $return = $return . '<a href="' . $baseUrl . '?pageNumber=' . ($pageNumber - 1) .'">&lt;</a> ';  
  }
  else   {
    $return = $return . '&lt; ';
  }
  $return = $return . '</span>';
}

function renderPagingNextPage($return, $baseUrl, $pageNumber, $totalCount) {
   $return = $return . '<span id="pager.next">';
   $maxPageNumber = calculateMaxPageNumber(20, $totalCount);
   if($pageNumber >= $maxPageNumber) {
     $return = $return . ' &gt; ';
   }
   else {
     $return = $return . ' <a href="' . $baseUrl . '?pageNumber=' . ($pageNumber + 1) .'">&gt;</a> ';
   }
   
   $return = $return . '</span>';
}

function renderPagingLastPage($return, $baseUrl, $pageNumber, $totalCount) {
  $return = $return . '<span id="page.last">';
  $maxPageNumber = calculateMaxPageNumber(20, $totalCount);
  if ($pageNumber >=$maxPageNumber ) {
      $return = $return . '&gt;&gt;';
  }
  else {
    $return = $return . '<a href="'. $baseUrl .'?pageNumber=' . $maxPageNumber . '">&gt;&gt;</a>';
  }
  $return = $return . '</span>';
  $return = $return . '</div>';
}

function renderPagingCentralBlock($return, $baseUrl, $pageNumber, $totalCount) {
   $maxPageNumber = calculateMaxPageNumber(20, $totalCount);
   
   
   $minBlockPageNumber = $pageNumber - 4;
   
   
   $distToMax = $maxPageNumber - $pageNumber;
   if ($distToMax < 2) {
     $minBlockPageNumber = $minBlockPageNumber + $distToMax;
   }
   else {
     $minBlockPageNumber = $minBlockPageNumber + 2;
   }
   
   if ($minBlockPageNumber < 0)  {
     $minBlockPageNumber = 0;
   }
   
   $maxBlockPageNumber = $pageNumber + 4;
   
   
   $distToMin = $pageNumber - 1;
   if ($distToMin < 2) {
     $maxBlockPageNumber = $maxBlockPageNumber - $distToMin;
   }
   else {
     $maxBlockPageNumber = $maxBlockPageNumber - 2;
   }
   
   if ($maxBlockPageNumber > $maxPageNumber) {
     $maxBlockPageNumber = $maxPageNumber;
   }
   
   $i = $minBlockPageNumber;

   do {
     if ($i == $pageNumber) {
       $return = $return . ' ' . $i . ' ';
     }
     else {
       $return = $return . ' <a href="' . $baseUrl . '?pageNumber=' . $i . '">' . $i . '</a>';
     }
     $i++;
   }
   while ($i <= $maxBlockPageNumber);
}    
?>