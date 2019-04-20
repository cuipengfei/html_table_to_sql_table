package html_table_to_sql_table;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.ClassLoader.getSystemResource;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HtmlExtractorTest {
	@Test
	public void should_extract_html_table_from_local_file() throws URISyntaxException, IOException {
		//given
		Path path = Paths.get(getSystemResource("simple_file.htm").toURI());
		
		//when
		RawData rawData = new HtmlExtractor().extractFrom(path);
		
		//then
		assertThat(rawData.getTableName(), is("simple_file"));
		assertThat(rawData.getColumns(), hasItems("FieldA", "FieldB"));
		assertThat(rawData.getRows(), hasItems(
			asList("hello", "ni hao"),
			asList("whatever", "does not matter")));
	}
}