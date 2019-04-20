package html_table_to_sql_table;

import java.util.List;

public class RawData {
	private String tableName;
	private List<String> columns;
	private List<List<String>> rows;
	
	public RawData(String tableName, List<String> columns, List<List<String>> rows) {
		this.tableName = tableName;
		this.columns = columns;
		this.rows = rows;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public List<String> getColumns() {
		return columns;
	}
	
	public List<List<String>> getRows() {
		return rows;
	}
}
