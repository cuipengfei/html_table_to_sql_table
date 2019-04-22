package html_table_to_sql_table.formalizer;

import org.jooq.DataType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.jooq.impl.SQLDataType.*;

public class ColumnsFormalizer extends Formalizer {
	
	// 根据字段名里包含的关键字来推断字段类型
	private static final Map<String, DataType> columnKeyWordMap =
		new HashMap<String, DataType>() {{
			put("number", INTEGER);
			put("date", DATE);
		}};
	
	@Override
	protected void formalize() {
		List<Column> columns = this.rawData.getColumns().stream()
			.map(column -> new Column(
				formalizeColumnName(column),
				formalizeDataType(formalizeColumnName(column))))
			.collect(toList());
		
		this.formalizedData.setColumns(columns);
	}
	
	private String formalizeColumnName(String column) {
		return column.toLowerCase().replace(" ", "_");
	}
	
	private DataType formalizeDataType(String columnName) {
		return columnKeyWordMap.keySet().stream()
			.filter(columnName::contains)
			.findFirst()
			.map(columnKeyWordMap::get)
			.orElse(VARCHAR); // 如果没有匹配到关键字，默认当做varchar
	}
}
