<?php

// TODO implement with regex
function getCleanContentName($string) {
    return $string;
}

// TODO implement with regex
function getCleanTitle($string) {
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
    
    $maxPageNumber = utilsCalculateMaxPageNumber($pageSize, $totalCount);
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

function utilsCalculateMaxPageNumber($pageSize, $totalCount) {
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
