package html_table_to_sql_table.saver;

import html_table_to_sql_table.formalizer.FormalizedData;
import org.jooq.CreateTableAsStep;
import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.Record;

import static org.jooq.impl.DSL.table;
import static org.jooq.impl.DSL.using;

public class Saver {
	
	private static final Saver saver = new Saver();
	
	private final DSLContext dsl = using("jdbc:h2:file:./h2db");
	
	private Saver() {
	}
	
	public static Saver instance() {
		return saver;
	}
	
	public void save(FormalizedData data) {
		String tableName = data.getTableName().toUpperCase();
		createTable(data, tableName);
		saveRows(data, tableName);
	}
	
	private void createTable(FormalizedData data, String tableName) {
		CreateTableAsStep<Record> table = dsl.createTableIfNotExists(tableName);
		data.getColumns().stream()
			.map(column -> table.column(column.getName(), column.getType()))
			.reduce((first, second) -> second)
			.map(Query::execute);
	}
	
	private void saveRows(FormalizedData data, String tableName) {
		data.getRows().forEach(row ->
			dsl.insertInto(table(tableName))
				.values(row)
				.execute());
	}
}
