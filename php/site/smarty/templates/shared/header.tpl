<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>{$title|default:"authorsite.org"}</title>
	<link rel="stylesheet" type="text/css" href="/css/main.css"/>
</head>
<body>
<div id="header">
	<div id="title">
    <h1>{$headerTitle|default:"authorsite.org"}</h1>
  </div>
    <form action="/search/search.php5" method="post">
      <div id="search">
        <span>Search: </span>
        <input type="text" size="20" name="term"/>
        <input type="submit" value="go"/>
      </div>
  </form>
  </div>
  <div id="body">
      <ul id="tabs">
        {if !isset($activeTab) or $activeTab =='content'}
        <li class="active">
        {else}
        <li>
        {/if}
          <a href="/content/index.php5">content</a>
        </li>
        {if $activeTab == 'bibliography'}
        <li class="active">
        {else}
        <li>
        {/if}
          <a href="/bib/index.php5">bibliography</a>
        </li>
        {if $activeTab == 'community'}
        <li class="active">
        {else}
        <li>
        {/if}
        <a href="/community/index.php5">community</a>
        </li>
        {if $activeTab == 'search'}
        <li class="active">
        {else}
        <li>
        {/if}
          <a href="/search/index.php5">search</a>
        </li>
        {if $activeTab == 'login'}
        <li class="active">
        {else}
        <li>
        {/if}
        {if isset($smarty.session.systemuser_username)}
        	<a href="/login/logout.php5">logout</a>
    	{else}
    		<a href="/login/login.php5">login</a>
		{/if}
		</li>
      </ul>
      {if isset($smarty.session.systemuser_username)}
      	<div id="loggedInUser">
      		Logged in as: {$smarty.session.systemuser_username}.
      	</div>
      {/if}
    <div id="content">
    
    
    

