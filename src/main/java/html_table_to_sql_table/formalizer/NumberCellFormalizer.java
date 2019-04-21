package html_table_to_sql_table.formalizer;

import static org.jooq.impl.SQLDataType.INTEGER;

public class NumberCellFormalizer extends Formalizer {
	@Override
	protected void formalize() {
		convertCells(
			column -> column.getType() == INTEGER,
			Integer::parseInt
		);
	}
}
