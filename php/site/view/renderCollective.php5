<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Work Producer (Collective <?php echo ($collectiveId) ?>)</title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>Work Producer (Collective)</h1>
        
        <h2>Details</h2>
        <table>
          <tr>
            <td>Name</td>
            <td><?php echo (htmlspecialchars( $collectiveResultSet[0]['name'])); ?></td>
          </tr>
          <tr>
            <td>Place</td>
            <td><?php echo (htmlspecialchars( $collectiveResultSet[0]['place'])); ?></td>
          </tr>
        </table>
        
        <?php require ('viewWorksSummaryUtil.php5') ?>
        
    </body>
</html> 

