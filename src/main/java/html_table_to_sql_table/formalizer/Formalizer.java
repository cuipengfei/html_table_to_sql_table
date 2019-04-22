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
		if (this.next == null) {
			this.next = next;
			next.init(rawData);
			next.formalizedData = formalizedData;
		} else {
			this.next.next(next); //穿成一串职责链
		}
		return this;
	}
	
	public FormalizedData run() {
		this.formalize();
		if (next != null) {
			return next.run(); // 依次执行职责链
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
	
	// 子类直接调用这个函数就好了，不需要自己去识别要convert哪些单元格，以及如何替换单元格里面的值
	// 子类只需要提供一个predicate来表明它想要识别具有哪些特征的column，并提供一个单元格值的转换函数即可
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
