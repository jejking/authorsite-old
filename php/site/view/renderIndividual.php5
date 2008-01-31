<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Work Producer (Individual <?php echo ($individualId) ?>)</title>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
    </head>
    <body>
        <h1>Work Producer (Individual)</h1>
        
        <h2>Details</h2>
        <table>
          <tr>
            <td>Name</td>
            <td><?php echo (htmlspecialchars( $individualResultSet[0]['name'])); ?></td>
          </tr>
          <tr>
            <td>Given Names</td>
            <td><?php echo (htmlspecialchars( $individualResultSet[0]['givennames'])); ?></td>
          </tr>
        </table>
        
        <?php require ('viewWorksSummaryUtil.php5') ?>
        
    </body>
</html> 

