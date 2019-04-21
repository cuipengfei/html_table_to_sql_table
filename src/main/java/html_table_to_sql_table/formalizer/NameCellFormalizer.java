package html_table_to_sql_table.formalizer;


import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.reverse;
import static java.util.stream.Collectors.joining;

public class NameCellFormalizer extends Formalizer {
	@Override
	protected void formalize() {
		convertCells(
			column -> column.getName().contains("name"),
			nameCell -> {
				String noSingleQuote = nameCell.replace("'", "");
				List<String> nameParts = asList(noSingleQuote.split(","));
				reverse(nameParts);
				
				return nameParts.stream()
					.map(String::trim)
					.collect(joining(" "));
			}
		);
	}
}
