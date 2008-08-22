<?php
    function smarty_function_humanlink($params, &$smarty) {
        
        $human = $params['arg'];
        $return = '<a href="';
        
        if ($human instanceof Individual) {
            $return = $return .'/bib/individual/';
            $return = $return . $human->id;
            $return = $return . '">';
            $return = $return . htmlspecialchars($human->name);
            if (!is_null($human->givenNames)) {
                $return = $return . ", " . htmlspecialchars($human->givenNames);
            }    
        }
        else {
            $return = $return .'/bib/collective/';
            $return = $return . $human->id;
            $return = $return . '">';
            $return = $return . htmlspecialchars($human->name);
            if (!is_null($human->place)) {
                $return = $return . "(" . htmlspecialchars($human->place) . ")";
            }    
        }
        $return = $return . '</a>';
        return $return;
    }
?>  