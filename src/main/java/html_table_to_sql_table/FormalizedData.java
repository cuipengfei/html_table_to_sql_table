package html_table_to_sql_table;

import java.util.List;

public class FormalizedData {
	private final String tableName;
	private final List<Column> columns;
	private final List<List> rows;
	
	public FormalizedData(String tableName, List<Column> columns, List<List> rows) {
		this.tableName = tableName;
		this.columns = columns;
		this.rows = rows;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public List<Column> getColumns() {
		return columns;
	}
	
	public List<List> getRows() {
		return rows;
	}
}
