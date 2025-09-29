package dev.filters.hub.convert;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import dev.filters.hub.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;

public class JsonConversionTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  void should_convert_json_to_filter() throws IOException {
    InputStream stream = new ClassPathResource("jsons/sample-filter.json").getInputStream();
    Filter filter = mapper.readValue(stream, Filter.class);
    
    assertNotNull(filter);
    assertEquals("Sample Filter", filter.name());
    assertTrue(filter.active());
    
    assertEquals(3, filter.criteria().size());
    
    Criterion first = filter.criteria().get(0);
    assertEquals(new AmountCriterion(UUID.randomUUID(), AmountOperator.gt, 100.0), first);
    
    Criterion second = filter.criteria().get(1);
    assertEquals(new TitleCriterion(UUID.randomUUID(), TitleOperator.contains, "example", false), second);
    
    Criterion third = filter.criteria().get(2);
    assertEquals(new DateCriterion(UUID.randomUUID(), DateOperator.after, "2025-07-03"), third);
  }

}
