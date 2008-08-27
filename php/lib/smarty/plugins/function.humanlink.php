<?php
function smarty_function_humanlink($params, &$smarty) {
        
    $human = $params['arg'];
    $tabluar = $params['tabular'];
    if (is_null($tabluar) || !is_bool($tabluar)) {
        $tabluar = false;
    }
        
    if ($tabular) {
        $return = '<a href="';
        
        if ($human instanceof Individual) {
            $return = $return .'/bib/individuals/';
            $return = $return . $human->id;
            $return = $return . '">';
            $return = $return . htmlspecialchars($human->name);
            if (!is_null($human->givenNames)) {
                $return = $return . ", " . htmlspecialchars($human->givenNames);
            }    
        }
        else {
            $return = $return .'/bib/collectives/';
            $return = $return . $human->id;
            $return = $return . '">';
            $return = $return . htmlspecialchars($human->name);
            $return = $return . '</a>';
            if (!is_null($human->place)) {
                $return = $return . " (" . htmlspecialchars($human->place) . ")";
            }    
        }
        
        return $return;    
    }
    else {
        if ($human instanceof Individual) {
            $return = '<a href="';
            $return = $return .'/bib/individuals/';
            $return = $return . $human->id;
            $return = $return . '">';
            $return = $return . htmlspecialchars($human->name);
            if (!is_null($human->givenNames)) {
                $return = $return . ", " . htmlspecialchars($human->givenNames);
            }    
        }
        else {
            if (!is_null($human->place)) {
                $return = $return . htmlspecialchars($human->place);
            }
            else {
                $return = $return . "Unknown";
            }
            $return = $return . ": ";
            $return = $return . '<a href="';
            $return = $return .'/bib/collectives/';
            $return = $return . $human->id;
            $return = $return . '">';
            $return = $return . htmlspecialchars($human->name);
            $return = $return . '</a>';

            return $return;
        }
    }
        
}
?>  