{* Smarty *}
{include file="shared/header.tpl"}

<h1>Theses</h1>

<table>
    <th>
        <tr>
            <th>Title</th>
            <th>Author</th>
            <th>Degree</th>
            <th>Awarding Body</th>
            <th>Dates</th>
        </tr>
        {foreach from=$theses item=thesis}
            <tr>
                <td class="workTitle">
                    <a href="/bib/thesis/{$thesis->id}">{$thesis->title}</a>
                </td>
                <td class="thesisAuthor">
                    <a href="/bib/individual/{$thesis->author->id}">{humanlink arg=$thesis->author}</a>
                </td>
                <td class="thesisDegree">
                    {$thesis->degree}
                </td>
                <td class="thesisAwardingBody">
                    {humanlink arg=$thesis->awardingBody}
                </td>
                <td class="workDates">
                    {$article->fromYear} {if $article->toYear != null} - {$article->toYear}{/if}
                </td>
            </tr>
        {/foreach}
    </th>

</table>


{pager baseUrl = "/bib/theses/index" pageNumber = $pageNumber totalCount = $thesesCount}
{include file="shared/footer.tpl"}