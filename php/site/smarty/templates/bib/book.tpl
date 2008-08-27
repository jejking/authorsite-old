{* Smarty *}
{include file="shared/header.tpl"}

<h1>{$book->title}</h1>

{humanlinks arg=$book->authors}. <span class="bookTitle">{$book->title}</span> {if $hasEditors}. Edited by: {humanlinks arg=$book->editors}{/if}
({humanlink arg=$book->publisher tabular=false}, {$book->fromYear}{if $book->toYear != null} - {$book->toYear}{/if}).


{include file="shared/footer.tpl"}