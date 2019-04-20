package html_table_to_sql_table;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.readAllBytes;
import static java.util.stream.Collectors.toList;

public class HtmlExtractor {
	
	public RawData extractFrom(Path path) throws IOException {
		Elements trs = Jsoup.parse(new String(readAllBytes(path)))
			.select("table")
			.select("tr");
		
		List<String> columns = trs.select("th").stream()
			.map(Element::text)
			.collect(toList());
		
		List<List<String>> rows = trs.stream()
			.map(tr -> tr.select("td").stream()
				.map(Element::text).collect(toList()))
			.filter(row -> !row.isEmpty())
			.collect(toList());
		
		String tableName = path.getFileName().toString().split("\\.")[0];
		return new RawData(tableName, columns, rows);
	}
}
