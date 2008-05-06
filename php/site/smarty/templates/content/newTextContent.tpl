{* Smarty *}
{include file="shared/header.tpl"}

<div id="textContent">

	<form method="post" action="/content/createNewTextContent.php5">
		<p>
			Content Name: <input type="text" size="20" name="name" value="{$name}"/>
		</p>
		{php}
			$fckEditor = $this->get_template_vars("fckEditor");
			$fckEditor->Create();
		{/php}
	
		<p>
			<input type="submit" value="create"/>
		</p>
	</form>

</div>
{include file="shared/footer.tpl"}