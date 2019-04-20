package html_table_to_sql_table.formalizer;

import html_table_to_sql_table.FormalizedData;
import html_table_to_sql_table.RawData;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.jooq.impl.SQLDataType.*;

public class ColumnsFormalizerTest {
	@Test
	public void column_with_number_should_be_integer_type() {
		//given
		RawData rawData = new RawData("A", asList("X Number"), emptyList());
		
		//when
		ColumnsFormalizer columnsFormalizer = new ColumnsFormalizer();
		FormalizedData formalizedData = columnsFormalizer.init(rawData).run();
		
		//then
		assertThat(formalizedData.getColumns().get(0).getType(), is(INTEGER));
	}
	
	@Test
	public void column_with_date_should_be_date_type() {
		//given
		RawData rawData = new RawData("A", asList("X Date"), emptyList());
		
		//when
		ColumnsFormalizer columnsFormalizer = new ColumnsFormalizer();
		FormalizedData formalizedData = columnsFormalizer.init(rawData).run();
		
		//then
		assertThat(formalizedData.getColumns().get(0).getType(), is(DATE));
	}
	
	@Test
	public void column_with_no_special_word_should_be_varchar_type() {
		//given
		RawData rawData = new RawData("A", asList("XYZ"), emptyList());
		
		//when
		ColumnsFormalizer columnsFormalizer = new ColumnsFormalizer();
		FormalizedData formalizedData = columnsFormalizer.init(rawData).run();
		
		//then
		assertThat(formalizedData.getColumns().get(0).getType(), is(VARCHAR));
	}
	
	@Test
	public void column_name_should_be_lower_case_with_underscore() {
		//given
		RawData rawData = new RawData("A", asList("XYZ ABC"), emptyList());
		
		//when
		ColumnsFormalizer columnsFormalizer = new ColumnsFormalizer();
		FormalizedData formalizedData = columnsFormalizer.init(rawData).run();
		
		//then
		assertThat(formalizedData.getColumns().get(0).getName(), is("xyz_abc"));
	}
}