{* Smarty *}
{include file="shared/header.tpl"}

<h1>Individuals</h1>

<table>
    <th>
        <tr>
            <th>Name</th>
        </tr>
        {foreach from=$individuals item=individual}
            <tr>
                <td class="individualName">
                    {humanlink arg=$individual}
                </td>
            </tr>
        {/foreach}
    </th>

</table>

{pager baseUrl = "/bib/individuals/index" pageNumber = $pageNumber totalCount = $individualsCount}
{include file="shared/footer.tpl"}