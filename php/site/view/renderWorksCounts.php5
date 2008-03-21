<?php
    $workTypeMap = array();
    $workTypeMap[AbstractWork::ARTICLE] = 'Articles';
    $workTypeMap[AbstractWork::BOOK] = 'Books';
    $workTypeMap[AbstractWork::CHAPTER] = 'Chapters';
    $workTypeMap[AbstractWork::THESIS] = 'Theses';
    $workTypeMap[AbstractWork::WEB_RESOURCE] = 'Web Resources';
    
    $relTypeMap = array();
    $relTypeMap[AbstractHuman::AUTHOR] = 'Authored';
    $relTypeMap[AbstractHuman::EDITOR] = 'Edited';
    $relTypeMap[AbstractHuman::AWARDING_BODY] = 'Awarded';
    $relTypeMap[AbstractHuman::PUBLISHER] = 'Published';
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
