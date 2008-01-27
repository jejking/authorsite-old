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

function renderPaging($baseUrl, $pageNumber, $totalCount) {
  renderPagingFirstPage($baseUrl, $pageNumber, $totalCount);
  renderPagingPreviousPage($baseUrl, $pageNumber, $totalCount);
  //renderPagingCentralBlock($baseUrl, $pageNumber, $totalCount);
  renderPagingNextPage($baseUrl, $pageNumber, $totalCount);
  renderPagingLastPage($baseUrl, $pageNumber, $totalCount);
}

function renderPagingFirstPage($baseUrl, $pageNumber, $totalCount) {
  echo ('<div class="pager">');
  echo ('<span id="pager.first">');
  if ($pageNumber > 0) {
    echo('<a href="' . $baseUrl . '?pageNumber=0">&lt;&lt; </a>');
  }
  else {
    echo('&lt;&lt; ');
  }
  echo ('</span>');
}

function renderPagingPreviousPage($baseUrl, $pageNumber, $totalCount) {
  echo ('<span id="pager.previous">');
  if ($pageNumber > 0) {
    echo('<a href="' . $baseUrl . '?pageNumber=' . ($pageNumber - 1) .'">&lt; </a>');  
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
     echo ('&gt; ');
   }
   else {
     echo('<a href="' . $baseUrl . '?pageNumber=' . ($pageNumber + 1) .'">&gt; </a>');
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
/*
private int pageNumber;
        private int maxPageNumber;
        
        private int minBlockPageNumber;
        private int maxBlockPageNumber;
        
        public CentralBlock(int pageNumber, int maxPageNumber) {
            this.pageNumber = pageNumber;
            this.maxPageNumber = maxPageNumber;
        }
        
        public void print(JspWriter out) throws IOException {

            this.minBlockPageNumber = this.pageNumber - 4;
            int distToMax = this.maxPageNumber - this.pageNumber;
            this.minBlockPageNumber += distToMax < 2 ? distToMax : 2;
            if ( this.minBlockPageNumber < 1 )  {
                this.minBlockPageNumber = 1;
            }
            
            this.maxBlockPageNumber = this.pageNumber + 4;
            int distToMin = this.pageNumber - 1;
            this.maxBlockPageNumber -= distToMin < 2 ? distToMin : 2;
            if (this.maxBlockPageNumber > this.maxPageNumber) {
                this.maxBlockPageNumber = this.maxPageNumber;
            }
            
            int i = minBlockPageNumber;
            
            do {
                if ( i == this.pageNumber ) {
                    out.print( " " + i + " " );
                }
                else
                {
                    out.print(" ");
                    String title = PagingTag.this.resourceBundle.getString("pager.pageNumber") + " " + i;
                    String url = buildUrl(PagingTag.this.indexUrl, i, PagingTag.this.pageSize);
                    printLink(out, url, title, Integer.toString(i) );
                    out.print(" ");
                }
                i++;
            }
            while (i <= maxBlockPageNumber);
            
        }
        */


?>
