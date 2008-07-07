{* Smarty *}
{include file="shared/header.tpl"}

{if $editable}
<div id="contentEditLinks">
    <span class="adminOptions">Edit Options:</span> 
    <a href="/content/editTextContent.php5?id={$textContent->id}">Edit</a> &nbsp;
    <a href="/content/deleteTextContent.php5?id={$textContent->id}">Delete</a>
</div>
{/if}

<div id="textContent">

{* if content is plain text, render in "pre"
else pack into a div... *}

	{if $textContent->mimeType eq 'text/plain'}
		<pre class="content">
			{$textContent->content}
		</pre>
	{else}
		<div>
			{$textContent->content}
		</div>
	{/if}

	<div id="textContentMetadata">
		Created by 
		<a href="/bib/individual/{$textContent->author->id}">{$textContent->author->name}{if !is_null($textContent->author->givenNames)}, {$textContent->author->givenNames}{/if}</a>
		on {$textContent->formattedCreatedAt}.
	</div>

</div>
{include file="shared/footer.tpl"}