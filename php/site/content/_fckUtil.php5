<?php
require_once("../fckeditor/fckeditor.php") ;
function getFckEditor($smarty, $paramName, $content) {
    $fckEditor = new FCKeditor($paramName) ;
    $fckEditor->BasePath = "/fckeditor/" ;
    
    $fckEditor->Config['ProcessHTMLEntities'] = false;
    $fckEditor->Value = $content;

    $smarty->assign("fckEditor", $fckEditor);
    return $fckEditor;
}
?>