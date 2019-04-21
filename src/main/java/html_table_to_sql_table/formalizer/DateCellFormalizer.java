package html_table_to_sql_table.formalizer;

import java.time.format.DateTimeFormatter;

import static java.sql.Date.valueOf;
import static java.time.YearMonth.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.jooq.impl.SQLDataType.DATE;

public class DateCellFormalizer extends Formalizer {
	
	private final DateTimeFormatter formatter = ofPattern("MMMM yyyy");
	
	@Override
	protected void formalize() {
		convertCells(
			column -> column.getType() == DATE,
			pickedCell -> valueOf(parse(pickedCell, formatter).atDay(1)));
	}
	
}
