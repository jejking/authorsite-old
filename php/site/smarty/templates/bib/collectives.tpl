{* Smarty *}
{include file="shared/header.tpl"}

<h1>Collectives</h1>

<table>
    <th>
        <tr>
            <th>Name</th>
        </tr>
        {foreach from=$collectives item=collective}
            <tr>
                <td class="collectiveName">
                    {humanlink arg=$collective}
                </td>
            </tr>
        {/foreach}
    </th>

</table>

{include file="shared/footer.tpl"}