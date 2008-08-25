{* Smarty *}
{include file="shared/header.tpl"}

<h1>Books</h1>

<table>
    <th>
        <tr>
            <th>Title</th>
            <th>Dates</th>
            <th>Author(s)</th>
            <th>Editor(s)</th>
            <th>Publisher</th>
        </tr>
        {foreach from=$books item=book}
            <tr>
                <td class="workTitle">
                    <a href="/bib/book/{$book->id}">{$book->title}</a>
                </td>
                <td class="workDates">
                    {$book->fromYear} {if $book->toYear != null} - {$book->toYear}{/if}
                </td>
                <td class="workAuthors">
                    {foreach from=$book->authors item=author}
                        {humanlink arg="$author"} <br/>
                    {/foreach}
                <td>
                <td class="workEditors">
                    {foreach from=$book->editors item=editor}
                        {humanlink arg="$editor"} <br/>
                    {/foreach}
                </td>
                <td class="publisher">
                    {humanlink arg=$book->publisher }
                </td>
            </tr>
        {/foreach}
    </th>

</table>

{pager baseUrl = "/bib/books/index" pageNumber = $pageNumber totalCount = $booksCount}

{include file="shared/footer.tpl"}