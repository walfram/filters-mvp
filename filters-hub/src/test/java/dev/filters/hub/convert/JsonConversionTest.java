package dev.filters.hub.convert;

import java.io.IOException;
import java.io.InputStream;

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
    
    assertEquals(3, filter.conditions().size());
    
    Condition first = filter.conditions().get(0);
    assertEquals(new AmountCondition(AmountOperator.GREATER_THEN, 100.0), first);
    
    Condition second = filter.conditions().get(1);
    assertEquals(new TitleCondition(TitleOperator.CONTAINS, "example"), second);
    
    Condition third = filter.conditions().get(2);
    assertEquals(new DateCondition(DateOperator.AFTER, "2025-07-03"), third);
  }

}
