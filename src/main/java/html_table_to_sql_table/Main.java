package html_table_to_sql_table;

import html_table_to_sql_table.extractor.HtmlExtractor;
import html_table_to_sql_table.extractor.RawData;
import html_table_to_sql_table.formalizer.*;
import html_table_to_sql_table.saver.Saver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.ClassLoader.getSystemResource;

public class Main {
	public static void main(String[] args) throws URISyntaxException, IOException {
		Path path = Paths.get(getSystemResource("bar_list.htm").toURI());
		RawData rawData = new HtmlExtractor().extractFrom(path);
		
		FormalizedData formalizedData = new ColumnsFormalizer()
			.init(rawData)
			.next(new ColumnsFormalizer())
			.next(new DateCellFormalizer())
			.next(new NumberCellFormalizer())
			.next(new NameCellFormalizer())
			.run();
		
		Saver.instance().save(formalizedData);
	}
}
