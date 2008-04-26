{* Smarty *}
{include file="shared/header.tpl"}

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
		Created by {$textContent->author->name}
		{if !is_null($textContent->author->givenNames)}
		, {$textContent->author->givenNames}
		{/if}.
		
	</div>

</div>
{include file="shared/footer.tpl"}