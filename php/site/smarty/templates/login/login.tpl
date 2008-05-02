{include file="shared/header.tpl"}

<div id="textContent">
	<h1>Login</h1>
	{if isset($generalErrorMessage)}
		<div id="generalErrorMessage">
			{$generalErrorMessage}
		</div>
	{/if}
	<form action="/login/login.php5" method="post">
		<table>
			<tr>
				<td>
					Username
				</td
				<td>
					{if isset($username)}
						<input type="text" name="username" value="{$username}"/>
					{else}
						<input type="text" name="username"/>
					{/if}
				</td>
			</tr>
			<tr>
				<td>
					Password
				</td>
				<td>
					<input type="password" name="password"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="login"/>
				</td>
			</tr>
		</table>
	</form>
</div>
{include file="shared/footer.tpl"}