<?php
    $workTypeMap = array();
    $workTypeMap[Constants::ARTICLE] = 'Articles';
    $workTypeMap[Constants::BOOK] = 'Books';
    $workTypeMap[Constants::CHAPTER] = 'Chapters';
    $workTypeMap[Constants::THESIS] = 'Theses';
    $workTypeMap[Constants::WEB_RESOURCE] = 'Web Resources';
    
    $relTypeMap = array();
    $relTypeMap[Constants::AUTHOR] = 'Authored';
    $relTypeMap[Constants::EDITOR] = 'Edited';
    $relTypeMap[Constants::AWARDING_BODY] = 'Awarded';
    $relTypeMap[Constants::PUBLISHER] = 'Published';
?>

<table>
	<thead>
		<tr>
			<th>Work Type</th>
			<th>Relationship</th>
		</tr>
	</thead>
	<tbody>
		<?php
            foreach (array_keys($worksCounts) as $key) {
                echo '<tr>';
                    echo '<td>';
                        echo $workTypeMap[$key];
                    echo '</td>';
                    echo '<td>';
                        $relationshipsCount = $worksCounts[$key];
                        foreach (array_keys($relationshipsCount) as $relKey) {
                            echo $relTypeMap[$relKey];
                            echo ' (';
                            echo $relationshipsCount[$relKey];
                            echo ') ';
                        }
                    echo '</td>';
                echo '</tr>';
            }
		?>
	</tbody>
</table>
