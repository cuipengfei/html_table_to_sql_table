package html_table_to_sql_table.saver;

import html_table_to_sql_table.formalizer.Column;
import html_table_to_sql_table.formalizer.FormalizedData;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.junit.After;
import org.junit.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.DATE;
import static org.jooq.impl.SQLDataType.INTEGER;


public class SaverTest {
	
	@After
	public void tearDown() throws Exception {
		DSLContext dsl = DSL.using("jdbc:h2:file:~/h2db");
		dsl.dropTable("TABLEA").execute();
	}
	
	@Test
	public void should_create_table_and_save_rows_based_on_formalized_data() {
		
		//given
		FormalizedData formalizedData = new
			FormalizedData("TableA",
			asList(new Column("F1", INTEGER), new Column("F2", DATE)),
			asList(asList(1, java.sql.Date.valueOf(YearMonth.parse("January 2010", DateTimeFormatter.ofPattern("MMMM yyyy")).atDay(1))), asList(2, "1990-01-01"))
		);
		
		//when
		Saver.instance().save(formalizedData);
		
		//then
		DSLContext dsl = DSL.using("jdbc:h2:file:~/h2db");
		Result<Record2<Object, Object>> fetch = dsl
			.select(field(name("F1")), field(name("F2")))
			.from(table("TableA"))
			.fetch();
		
		assertThat(fetch.getValue(0, 0), is(1));
		assertThat(fetch.getValue(0, 1), is("a"));
		assertThat(fetch.getValue(1, 0), is(2));
		assertThat(fetch.getValue(1, 1), is("b"));
	}
	
	@Test
	public void name1() {
		String string = "January 2010";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
		LocalDate date = LocalDate.parse(string, formatter);
		System.out.println(date); // 2010-01-02
	}
}
