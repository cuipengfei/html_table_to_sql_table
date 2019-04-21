package html_table_to_sql_table.formalizer;

import org.jooq.DataType;

public class Column {
	private final String name;
	private final DataType type;
	
	public Column(String name, DataType type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public DataType getType() {
		return type;
	}
}
