
</div>
  </div>
</div>
<div id="footer">

  <!-- imprint, copyright, usage rights, date -->
  (C) {$copyrightHolder|default:"authorsite.org"}, 
  {if isset($copyrightDate) }
    ({$copyrightDate|date_format:"%Y"}).
  {else}
    ({$smarty.now|date_format:"%Y"}).
  {/if}
  {if isset($usage) }
    {$usage}.
  {/if}
  <a href="/content/imprint.php5">Imprint &amp; Contact</a>
</div>
</body>
</html>