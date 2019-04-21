package html_table_to_sql_table.formalizer;

import html_table_to_sql_table.extractor.RawData;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PersonNameFormalizerTest {
	@Test
	public void first_name_should_be_before_last_name() {
		//given
		RawData rawData = new RawData(
			"A",
			asList("XYZ Name"),
			asList(asList("Aaron Jr, John Bland")));
		
		//when
		FormalizedData formalizedData = new ColumnsFormalizer()
			.init(rawData)
			.next(new PersonNameFormalizer())
			.run();
		
		//then
		String convertedName = formalizedData.getRows().get(0).get(0).toString();
		assertThat(convertedName, is("John Bland Aaron Jr"));
	}
	
	@Test
	public void should_remove_single_quote_in_name() {
		//given
		RawData rawData = new RawData(
			"A",
			asList("XYZ Name"),
			asList(asList("Aa'ronson, Joel Steven")));
		
		//when
		FormalizedData formalizedData = new ColumnsFormalizer()
			.init(rawData)
			.next(new PersonNameFormalizer())
			.run();
		
		//then
		String convertedName = formalizedData.getRows().get(0).get(0).toString();
		assertThat(convertedName, is("Joel Steven Aaronson"));
	}
}