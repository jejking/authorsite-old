{* Smarty *}
{include file="shared/header.tpl"}

<div id="textContent">
    <p id="contentEditLinks">
        <span class="adminOptions">
            Are you sure you want to delete text content <i>{$textContent->title}</i>?
        </span>
        <a href="/content/text/{$textContent->content_name}">No</a>
        <a href="/content/deleteTextConfirmed.php5?id={$textContent->id}">Delete!</a>
    </p>
</div>

{include file="shared/footer.tpl"}