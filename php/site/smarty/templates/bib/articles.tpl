{* Smarty *}
{include file="shared/header.tpl"}

<h1>Articles</h1>

<table>
    <th>
        <tr>
            <th>Title</th>
            <th>Journal</th>
            <th>Pages</th>
            <th>Dates</th>
            <th>Author(s)</th>
            <th>Editor(s)</th>
        </tr>
        {foreach from=$articles item=article}
            <tr>
                <td class="workTitle">
                    <a href="/bib/articles/{$article->id}">{$article->title}</a>
                </td>
                <td class="articleJournal">
                    <a href="/bib/journals/{$article->journal->id}">{$article->journal->title}</a>
                </td>
                <td class="pages">
                    {$journal->pages}
                </td>
                <td class="workDates">
                    {$article->fromYear} {if $article->toYear != null} - {$article->toYear}{/if}
                </td>
                <td class="workAuthors">
                    {foreach from=$article->authors item=author}
                        {humanlink arg="$author"} <br/>
                    {/foreach}
                <td>
                <td class="workEditors">
                    {foreach from=$article->editors item=editor}
                        {humanlink arg="$editor"} <br/>
                    {/foreach}
                </td>
            </tr>
        {/foreach}
    </th>

</table>

{pager baseUrl = "/bib/articles/index" pageNumber = $pageNumber totalCount = $articlesCount}

{include file="shared/footer.tpl"}