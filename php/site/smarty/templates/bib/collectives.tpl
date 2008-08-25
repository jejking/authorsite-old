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

{pager baseUrl = "/bib/collectives/index" pageNumber = $pageNumber totalCount = $collectivesCount}
{include file="shared/footer.tpl"}