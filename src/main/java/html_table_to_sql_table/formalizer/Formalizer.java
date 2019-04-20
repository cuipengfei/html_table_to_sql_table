package html_table_to_sql_table.formalizer;

import html_table_to_sql_table.FormalizedData;
import html_table_to_sql_table.RawData;

public abstract class Formalizer {
	protected RawData rawData;
	protected FormalizedData formalizedData = new FormalizedData();
	private Formalizer next;
	
	public Formalizer init(RawData rawData) {
		this.rawData = rawData;
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
	
}
