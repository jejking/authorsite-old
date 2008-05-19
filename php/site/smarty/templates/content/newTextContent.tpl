{* Smarty *}
{include file="shared/header.tpl"}

<div id="textContent">

	<form method="post" action="/content/createNewTextContent.php5">
		<table>
			<tr>
				<td>Content Name (for URL): </td>
				<td>
					<input type="text" size="40" name="name" value="{$name}"/>
				</td>
			</tr>
			<tr>
				<td>Content Title: </td>
				<td>
					<input type="text" size="40" name="title" value="{$title}"/>
				</td>
			</tr>
		</table>
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