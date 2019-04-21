package html_table_to_sql_table.formalizer;

import html_table_to_sql_table.extractor.RawData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public abstract class Formalizer {
	protected RawData rawData;
	protected FormalizedData formalizedData = new FormalizedData();
	
	private Formalizer next;
	
	public Formalizer init(RawData rawData) {
		this.rawData = rawData;
		initFormalizedData(rawData);
		return this;
	}
	
	protected abstract void formalize();
	
	public Formalizer next(Formalizer next) {
		this.next = next;
		next.init(rawData);
		next.formalizedData = formalizedData;
		return this;
	}
	
	public FormalizedData run() {
		this.formalize();
		if (next != null) {
			return next.run();
		}
		return formalizedData;
	}
	
	private void initFormalizedData(RawData rawData) {
		formalizedData.setTableName(rawData.getTableName());
		List<List> cloned = rawData.getRows().stream()
			.map(ArrayList::new)
			.collect(toList());
		formalizedData.setRows(cloned);
	}
	
	void convertCells(Predicate<Column> columnPredicate, Function<String, Object> cellConvert) {
		this.formalizedData.getColumns().stream()
			.filter(columnPredicate)
			.forEach(pickedColumn -> {
				int indexOfPickedField = this.formalizedData.getColumns().indexOf(pickedColumn);
				
				this.formalizedData.getRows().forEach(row -> {
					String pickedValue = row.get(indexOfPickedField).toString();
					row.set(indexOfPickedField, cellConvert.apply(pickedValue));
				});
			});
	}
}
