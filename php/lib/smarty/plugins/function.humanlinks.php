<?php
    function smarty_function_humanlinks($params, &$smarty) {

        $return = '';

        // list of humans to be rendered
        $humans = $params['arg'];
        
        for ($i = 0; $i < count($humans); $i++) {
                $return = $return . humanlink($humans[$i]);
                
                /*if ($i < count($humans) - 2) {
                    $return = $return . ', ';
                }
                else {
                    $return = $return . ' and ';
                }*/
            }
        
        return $return;
    }

    /**
     *
     * @param AbstractHuman $human
     * @return string
     */
    function humanlink($human) {
        $return = '<a href="';
        
        if ($human instanceof Individual) {
            $return = $return .'/bib/individuals/';
            $return = $return . $human->id;
            $return = $return . '">';
            $return = $return . htmlspecialchars($human->name);
            if (!is_null($human->givenNames)) {
                $return = $return . ", " . htmlspecialchars($human->givenNames);
            }
            $return = $return . '</a>';
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
?>  