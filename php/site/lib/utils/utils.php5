<?php

function getCleanContentName($string) {
    return $string;
}

    
function getId($string) {
  if (is_numeric($string)) {
    $id = intval($string);
    if ($id > 0) {
      return $id;
    }
    else {
      return 0;
    }
  }
  else {
    return 0;
  }
}

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

function renderPaging($baseUrl, $pageNumber, $totalCount) {
  renderPagingFirstPage($baseUrl, $pageNumber, $totalCount);
  renderPagingPreviousPage($baseUrl, $pageNumber, $totalCount);
  renderPagingCentralBlock($baseUrl, $pageNumber, $totalCount);
  renderPagingNextPage($baseUrl, $pageNumber, $totalCount);
  renderPagingLastPage($baseUrl, $pageNumber, $totalCount);
}

function renderPagingFirstPage($baseUrl, $pageNumber, $totalCount) {
  echo ('<div class="pager">');
  echo ('<span id="pager.first">');
  if ($pageNumber > 0) {
    echo('<a href="' . $baseUrl . '?pageNumber=0">&lt;&lt;</a> ');
  }
  else {
    echo('&lt;&lt; ');
  }
  echo ('</span>');
}

function renderPagingPreviousPage($baseUrl, $pageNumber, $totalCount) {
  echo ('<span id="pager.previous">');
  if ($pageNumber > 0) {
    echo('<a href="' . $baseUrl . '?pageNumber=' . ($pageNumber - 1) .'">&lt;</a> ');  
  }
  else   {
    echo('&lt; ');
  }
  echo ('</span>');
}

function renderPagingNextPage($baseUrl, $pageNumber, $totalCount) {
   echo ('<span id="pager.next">');
   $maxPageNumber = calculateMaxPageNumber(20, $totalCount);
   if($pageNumber >= $maxPageNumber) {
     echo (' &gt; ');
   }
   else {
     echo(' <a href="' . $baseUrl . '?pageNumber=' . ($pageNumber + 1) .'">&gt;</a> ');
   }
   
   echo ('</span>');
}

function renderPagingLastPage($baseUrl, $pageNumber, $totalCount) {
  echo('<span id="page.last">');
  $maxPageNumber = calculateMaxPageNumber(20, $totalCount);
  if ($pageNumber >=$maxPageNumber ) {
      echo('&gt;&gt;');
  }
  else {
    echo('<a href="'. $baseUrl .'?pageNumber=' . $maxPageNumber . '">&gt;&gt;</a>');
  }
  echo('</span>');
  echo('</div>');
}

function renderPagingCentralBlock($baseUrl, $pageNumber, $totalCount) {
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
       echo (' ' . $i . ' ');
     }
     else {
       echo (' <a href="' . $baseUrl . '?pageNumber=' . $i . '">' . $i . '</a>' );
     }
     $i++;
   }
   while ($i <= $maxBlockPageNumber);
}

function renderHuman($human) {
    if ($human instanceof Individual) {
        echo ('<a href="individual.php5?id=' . $human->id . '">');
        echo (htmlspecialchars($human->name));
        if (!is_null($human->givenNames)) {
            echo (', ' . htmlspecialchars($human->givenNames));
        }
        echo ('</a>');
    }
    else {
        echo ('<a href="collective.php5?id=' . $human->id . '">');
        echo (htmlspecialchars($human->name));
        echo ('</a>');
        if (!is_null($human->place)) {
            echo (' ('. htmlspecialchars($human->place) . ')');
        }
        
    }
}

function renderPublisher($human) {
    if ($human instanceof Individual) {
        renderHuman($human);
    }
    else {
        echo htmlspecialchars($human->place);
        echo ': ';
        echo ('<a href="collective.php5?id=' . $human->id . '">');
        echo (htmlspecialchars($human->name));
        echo ('</a>');
    }
}

function renderAwardingBody($collective) {
    echo ('<a href="collective.php5?id=' . $collective->id . '">');
    echo (htmlspecialchars($collective->name));
    echo ('</a>');
    if (!is_null($collective->place)) {
        echo ' (' . $collective->place . ')'; 
    }
}

?>
