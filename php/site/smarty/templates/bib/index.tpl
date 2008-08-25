{* Smarty *}
{include file="shared/header.tpl"}

<div id="textContent">
    <h1>Bibliographic Resources</h1>
    <p>
       The site maintains a list of interlinked bibliographic resources. These
       can be browsed by works - or by the people and organisations who created
       them.
    </p>
    <h2>Works</h2>
    <table>
        <thead>
            <tr>
                <th align="left">Type</th>
                <th align="left">Count</th>
                <th align="left">Notes</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><a href="/bib/books/index">Books</a></td>
                <td>{$booksCount}</td>
                <td>Published, printed books.</td>
            </tr>
            <tr>
                <td><a href="/bib/chapters/index">Chapters</a></td>
                <td>{$chaptersCount}</td>
                <td>Chapters (essays, articles) contained in published, printed books.</td>
            </tr>
            <tr>
                <td><a href="/bib/articles/index">Articles</a></td>
                <td>{$articlesCount}</td>
                <td>Articles published in printed journals.</td>
            </tr>
            <tr>
                <td><a href="/bib/journals/index">Journals</a></td>
                <td>{$journalsCount}</td>
                <td>Printed journals and other serials</td>
            </tr>
            <tr>
                <td><a href="/bib/theses/index">Theses</a></td>
                <td>{$thesesCount}</td>
                <td>Unpublished theses and dissertations accepted for university degrees.</td>
            </tr>
            <tr>
                <td><a href="/bib/externalWebResources/index">Web Resources</a></td>
                <td>{$externalWebResourcesCount}</td>
                <td>External web based resources.</td>
            </tr>
        </tbody>
    </table>
    
    <h2>People</h2>
    <table>
        <thead>
            <tr>
                <th align="left">Type</th>
                <th align="left">Count</th>
                <th align="left">Notes</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><a href="/bib/individuals/index">Individuals</a></td>
                <td>{$individualsCount}</td>
                <td>Distinct, individual human beings who have authored, edited works.</td>
            </tr>
            <tr>
                <td><a href="/bib/collectives/index">Collectives</a></td>
                <td>{$collectivesCount}</td>
                <td>Organisations such as publishing houses, universities, who have been involved in the production of works.</td>
            </tr>
        </tbody>
    </table>
</div>

{include file="shared/footer.tpl"}