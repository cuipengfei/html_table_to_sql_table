package html_table_to_sql_table.formalizer;

import html_table_to_sql_table.extractor.RawData;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NumberCellFormalizerTest {
	@Test
	public void should_convert_number_column_to_integer() {
		//given
		RawData rawData = new RawData(
			"A",
			asList("XYZ number"),
			asList(asList("123")));
		
		//when
		FormalizedData formalizedData = new ColumnsFormalizer()
			.init(rawData)
			.next(new NumberCellFormalizer())
			.run();
		
		//then
		Object convertedValue = formalizedData.getRows().get(0).get(0);
		assertThat(convertedValue, is(123));
	}
}