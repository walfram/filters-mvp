package dev.filters.hub.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.filters.hub.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class JsonConversionTest {

	private final ObjectMapper mapper = new ObjectMapper();

	{
		mapper
				.registerModule(new JavaTimeModule())
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

	@Test
	void should_convert_json_to_filter() throws IOException {
		InputStream stream = new ClassPathResource("jsons/sample-filter.json").getInputStream();
		Filter filter = mapper.readValue(stream, Filter.class);

		assertNotNull(filter);
		assertEquals("Sample Filter", filter.name());
		assertTrue(filter.active());

		assertEquals(3, filter.criteria().size());

		Criterion first = filter.criteria().get(0);
		assertEquals(new AmountCriterion(null, AmountOperator.gt, 100.0), first);

		Criterion second = filter.criteria().get(1);
		assertEquals(new TitleCriterion(null, TitleOperator.contains, "example", false), second);

		Criterion third = filter.criteria().get(2);
		assertEquals(new DateCriterion(null, DateOperator.after, LocalDate.parse("2025-07-03")), third);
	}

}
