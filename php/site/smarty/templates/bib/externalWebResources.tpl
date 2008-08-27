{* Smarty *}
{include file="shared/header.tpl"}

<h1>External Web Resources</h1>

<table>
    <th>
        <tr>
            <th>Title</th>
            <th>Dates</th>
            <th>Author(s)</th>
            <th>Editor(s)</th>
            <th>Publisher</th>
        </tr>
        {foreach from=$externalWebResources item=externalWebResource}
            <tr>
                <td class="workTitle">
                    <a href="/bib/externalWebResources/{$externalWebResource->id}">{$externalWebResource->title}</a>
                </td>
                <td class="workDates">
                    {$externalWebResource->fromYear} {if $externalWebResource->toYear != null} - {$externalWebResource->toYear}{/if}
                </td>
                <td class="workAuthors">
                    {foreach from=$externalWebResource->authors item=author}
                        {humanlink arg="$author"} <br/>
                    {/foreach}
                <td>
                <td class="workEditors">
                    {foreach from=$externalWebResource->editors item=editor}
                        {humanlink arg="$editor"} <br/>
                    {/foreach}
                </td>
                <td class="workPublisher">
                    {humanlink arg=$externalWebResource->publisher}
                </td>
            </tr>
        {/foreach}
    </th>

</table>

{pager baseUrl = "/bib/externalWebResources/index" pageNumber = $pageNumber totalCount = $externalWebResourcesCount}
{include file="shared/footer.tpl"}