package html_table_to_sql_table.formalizer;

import html_table_to_sql_table.extractor.RawData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public abstract class Formalizer {
	RawData rawData;
	FormalizedData formalizedData = new FormalizedData();
	
	private Formalizer next;
	
	public Formalizer init(RawData rawData) {
		this.rawData = rawData;
		initFormalizedData(rawData);
		return this;
	}
	
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
	
	protected abstract void formalize();
	
	private void initFormalizedData(RawData rawData) {
		formalizedData.setTableName(rawData.getTableName());
		List<List> cloned = rawData.getRows().stream()
			.map(ArrayList::new)
			.collect(toList());
		formalizedData.setRows(cloned);
	}
	
	void convertCells(Predicate<Column> columnPredicate, Function<String, Object> cellConvert) {
		formalizedData.getColumns().stream()
			.filter(columnPredicate)
			.forEach(pickedColumn -> {
				int indexOfPickedField = formalizedData.getColumns().indexOf(pickedColumn);
				
				formalizedData.getRows().forEach(row -> {
					String pickedValue = row.get(indexOfPickedField).toString();
					row.set(indexOfPickedField, cellConvert.apply(pickedValue));
				});
			});
	}
}
