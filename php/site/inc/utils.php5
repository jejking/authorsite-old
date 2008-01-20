<?php

function getPageNumber($string, $pageSize, $totalCount) {
  
  if (is_numeric($string)) {
    $pageNumber = intval($string);
    if ($pageNumber < 0) {
      return 0;
    }
    
    $maxPageNumber = calculateMaxPageNumber($pageSize, $totalCount);
    if ( $pageNumber > $maxPageNumber) {
      return $maxPageNumber;
    }
    else {
      return $pageNumber;
    }
    
  }
  else {
    return 0;
  }
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

functionRenderPaging($pageNumber, $totalCount) {
}


?>
