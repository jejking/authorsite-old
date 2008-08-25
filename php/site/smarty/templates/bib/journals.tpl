{* Smarty *}
{include file="shared/header.tpl"}

<h1>Journals</h1>

<table>
    <th>
        <tr>
            <th>Title</th>
        </tr>
        {foreach from=$journals item=journal}
            <tr>
                <td class="workTitle">
                    <a href="/bib/journal/{$journal->id}">{$journal->title}</a>
                </td>
            </tr>
        {/foreach}
    </th>

</table>

{pager baseUrl = "/bib/journals/index" pageNumber = $pageNumber totalCount = $journalsCount}

{include file="shared/footer.tpl"}