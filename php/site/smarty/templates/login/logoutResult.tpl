{include file="shared/header.tpl"}
<div id="textContent">
	<h1>Logged out!</h1>
	{if isset($generalErrorMessage)}
		<div id="generalErrorMessage">
			{$generalErrorMessage}
		</div>
	{else}
	<p>
		You have successfully logged out.
	</p>
	{/if}
</div>
{include file="shared/footer.tpl"}