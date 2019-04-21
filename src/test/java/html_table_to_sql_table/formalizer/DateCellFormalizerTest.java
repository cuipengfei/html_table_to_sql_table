package html_table_to_sql_table.formalizer;

import html_table_to_sql_table.FormalizedData;
import html_table_to_sql_table.RawData;
import org.junit.Test;

import java.sql.Date;

import static java.sql.Date.valueOf;
import static java.time.YearMonth.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DateCellFormalizerTest {
	@Test
	public void should_convert_string_to_date_when_column_type_is_date() {
		//given
		RawData rawData = new RawData(
			"A",
			asList("XYZ Date"),
			asList(asList("January 1970")));
		
		//when
		FormalizedData formalizedData = new ColumnsFormalizer()
			.init(rawData)
			.next(new DateCellFormalizer())
			.run();
		
		//then
		Date date = (Date) formalizedData.getRows().get(0).get(0);
		assertThat(date.toString(), is("1970-01-01"));
	}
}