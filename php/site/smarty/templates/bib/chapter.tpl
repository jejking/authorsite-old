{* Smarty *}
{include file="shared/header.tpl"}

<h1>{$chapter->title}</h1>

{humanlinks arg=$chapter->authors}. '<span class="chapterTitle">{$chapter->title}</span>' {if $hasEditors}. Edited by: {humanlinks arg=$chapter->editors}{/if}, in
<a href="/bib/books/{$chapter->book->id}"><span class="bookTitle">{$chapter->book->title}</span></a>
{if $bookHasAuthors}, by {humanlinks arg=$chapter->book->authors}{/if} 
{if $bookHasEditors}, edited by: {humanlinks arg=$chapter->book->editors}{/if}
({humanlink arg=$chapter->book->publisher tabular=false}, {$chapter->book->fromYear}{if $chapter->book->toYear != null} - {$chapter->book->toYear}{/if}).


{include file="shared/footer.tpl"}