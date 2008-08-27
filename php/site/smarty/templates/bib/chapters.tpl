{* Smarty *}
{include file="shared/header.tpl"}

<h1>Chapters</h1>

<table>
    <th>
        <tr>
            <th>Title</th>
            <th>Book</th>
            <th>Pages</th>
            <th>Dates</th>
            <th>Author(s)</th>
            <th>Editor(s)</th>
        </tr>
        {foreach from=$chapters item=chapter}
            <tr>
                <td class="workTitle">
                    <a href="/bib/chapters/{$chapter->id}">{$chapter->title}</a>
                </td>
                <td class="chapterBook">
                    <a href="/bib/books/{$chapter->book->id}">{$chapter->book->title}</a>
                </td>
                <td class="pages">
                    {$chapter->pages}
                </td>
                <td class="workDates">
                    {$chapter->fromYear} {if $chapter->toYear != null} - {$chapter->toYear}{/if}
                </td>
                <td class="workAuthors">
                    {foreach from=$chapter->authors item=author}
                        {humanlink arg="$author"} <br/>
                    {/foreach}
                <td>
                <td class="workEditors">
                    {foreach from=$chapter->editors item=editor}
                        {humanlink arg="$editor"} <br/>
                    {/foreach}
                </td>
            </tr>
        {/foreach}
    </th>

</table>

{pager baseUrl = "/bib/chapters/index" pageNumber = $pageNumber totalCount = $chaptersCount}
{include file="shared/footer.tpl"}