{* Smarty *}
{include file="shared/header.tpl"}

<h1>{$article->title}</h1>

{humanlinks arg=$article->authors}. '<span class="articleTitle">{$article->title}</span>' {if $hasEditors}, edited by: {humanlinks arg=$articles->editors}{/if}, in
<a href="/bib/journals/{$article->journal->id}"><span class="journalTitle">{$article->journal->title}</span></a>
{if $hasVolume}, {$article->volume}{/if}{if $hasIssue}, issue {$article->issue}{/if} ({$article->fromYear}{if $article->toYear != null} - {$article->toYear}{/if}).



{include file="shared/footer.tpl"}