{* Smarty *}
{include file="shared/header.tpl"}

<div id="textContent">

    <form method="post" action="/content/updateTextContent.php5">
        <input type="hidden" name="id" value="{$textContent->id}"/>
        <input type="hidden" name="name" value="{$textContent->content_name}"/>
        <table>
            <tr>
                <td>Content Name (for URL): </td>
                <td>
                    <input type="text" size="40" value="{$textContent->content_name}" disabled="true"/>
                </td>
            </tr>
            <tr>
                <td>Content Title: </td>
                <td>
                    <input type="text" size="40" name="title" value="{$textContent->title}"/>
                </td>
            </tr>
        </table>
        {php}
            $fckEditor = $this->get_template_vars("fckEditor");
            $fckEditor->Create();
        {/php}
    
        <p>
            <input type="submit" value="update content for {$textContent->content_name}"/>
        </p>
    </form>

</div>
{include file="shared/footer.tpl"}