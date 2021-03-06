package html_table_to_sql_table.formalizer;

import java.util.List;

public class FormalizedData {
	private String tableName;
	private List<Column> columns;
	private List<List> rows;
	
	FormalizedData() {
	}
	
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
	
	void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	
	void setRows(List<List> rows) {
		this.rows = rows;
	}
}
