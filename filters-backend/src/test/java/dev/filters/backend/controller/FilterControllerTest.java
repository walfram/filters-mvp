package dev.filters.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.filters.backend.dto.AmountCondition;
import dev.filters.backend.dto.DateCondition;
import dev.filters.backend.dto.FilterDto;
import dev.filters.backend.dto.TitleCondition;
import dev.filters.backend.entity.FilterEntity;
import dev.filters.backend.service.FilterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(FilterController.class)
public class FilterControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private FilterService filterService;

  @Test
  public void testCreateFilter_ValidInput_ReturnsOk() throws Exception {
    FilterDto validFilterDto = new FilterDto(
        UUID.randomUUID(),
        "Test Filter",
        List.of(
            new TitleCondition(TitleCondition.TitleOperator.STARTS_WITH, "Test"),
            new AmountCondition(AmountCondition.AmountOperator.EQ, 100.00),
            new DateCondition(DateCondition.DateOperator.BEFORE, "2022-01-01T00:00:00")
        ),
        true
    );

    // Mock the service to return the same DTO when create is called
    when(filterService.create(any(FilterDto.class))).thenReturn(validFilterDto);

    String validFilterRequest = objectMapper.writeValueAsString(validFilterDto);

    mockMvc.perform(post("/api/filters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(validFilterRequest))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.id").isNotEmpty())
        .andExpect(jsonPath("$.name").value("Test Filter"))
        .andExpect(jsonPath("$.active").value(true))
        .andExpect(jsonPath("$.conditions").isArray())
        .andExpect(jsonPath("$.conditions", hasSize(3)))
        .andExpect(jsonPath("$.conditions[0].type").value("title"))
        .andExpect(jsonPath("$.conditions[0].operator").value("STARTS_WITH"))
        .andExpect(jsonPath("$.conditions[0].value").value("Test"))
        .andExpect(jsonPath("$.conditions[1].type").value("amount"))
        .andExpect(jsonPath("$.conditions[1].operator").value("EQ"))
        .andExpect(jsonPath("$.conditions[1].value").value(100.00))
        .andExpect(jsonPath("$.conditions[2].type").value("date"))
        .andExpect(jsonPath("$.conditions[2].operator").value("BEFORE"))
        .andExpect(jsonPath("$.conditions[2].value").value("2022-01-01T00:00:00"))
        .andExpect(content().json(validFilterRequest, JsonCompareMode.STRICT));
  }

  @Test
  public void testCreateFilter_InvalidInput_MissingName_ReturnsBadRequest() throws Exception {
    String invalidFilterRequest = """
        {
            "conditions": [{"field": "status", "operator": "EQUALS", "value": "ACTIVE"}],
            "active": true
        }
        """;

    mockMvc.perform(post("/api/filters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidFilterRequest))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testCreateFilter_InvalidInput_EmptyConditions_ReturnsBadRequest() throws Exception {
    String invalidFilterRequest = """
        {
            "name": "Invalid Filter",
            "conditions": [],
            "active": true
        }
        """;

    mockMvc.perform(post("/api/filters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidFilterRequest))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testCreateFilter_InvalidInput_MissingActive_ReturnsBadRequest() throws Exception {
    String invalidFilterRequest = """
        {
            "name": "Filter Without Active",
            "conditions": [{"field": "status", "operator": "EQUALS", "value": "ACTIVE"}]
        }
        """;

    mockMvc.perform(post("/api/filters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidFilterRequest))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testCreateFilter_InvalidCondition_AmountTypeWithDateOperatorAndValue_ReturnsBadRequest() throws Exception {
    String invalidFilterRequest = """
        {
            "name": "Invalid Amount Condition",
            "conditions": [
                {
                    "type": "amount",
                    "operator": "BEFORE",
                    "value": "2022-01-01T00:00:00"
                }
            ],
            "active": true
        }
        """;

    mockMvc.perform(post("/api/filters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidFilterRequest))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testCreateFilter_InvalidCondition_DateTypeWithAmountOperatorAndValue_ReturnsBadRequest() throws Exception {
    String invalidFilterRequest = """
        {
            "name": "Invalid Date Condition",
            "conditions": [
                {
                    "type": "date",
                    "operator": "GT",
                    "value": 100.00
                }
            ],
            "active": true
        }
        """;

    mockMvc.perform(post("/api/filters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidFilterRequest))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testCreateFilter_InvalidCondition_TitleTypeWithAmountOperatorAndValue_ReturnsBadRequest() throws Exception {
    String invalidFilterRequest = """
        {
            "name": "Invalid Title Condition",
            "conditions": [
                {
                    "type": "title",
                    "operator": "GT",
                    "value": 100.00
                }
            ],
            "active": true
        }
        """;

    mockMvc.perform(post("/api/filters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidFilterRequest))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testCreateFilter_InvalidCondition_AmountTypeWithObjectValue_ReturnsBadRequest() throws Exception {
    String invalidFilterRequest = """
        {
            "name": "Invalid Amount Condition",
            "conditions": [
                {
                    "type": "amount",
                    "operator": "GT",
                    "value": {}
                }
            ],
            "active": true
        }
        """;

    mockMvc.perform(post("/api/filters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidFilterRequest))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testCreateFilter_InvalidCondition_AmountTypeWithUndefinedValue_ReturnsBadRequest() throws Exception {
    String invalidFilterRequest = """
        {
            "name": "Invalid Amount Condition",
            "conditions": [
                {
                    "type": "amount",
                    "operator": "GT",
                    "value": "undefined"
                }
            ],
            "active": true
        }
        """;

    mockMvc.perform(post("/api/filters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidFilterRequest))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testCreateFilter_InvalidCondition_AmountTypeWithNullValue_ReturnsBadRequest() throws Exception {
    String invalidFilterRequest = """
        {
            "name": "Invalid Amount Condition",
            "conditions": [
                {
                    "type": "amount",
                    "operator": "GT",
                    "value": null
                }
            ],
            "active": true
        }
        """;

    mockMvc.perform(post("/api/filters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidFilterRequest))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testCreateFilter_InvalidCondition_AmountTypeMissingConditionFields_ReturnsBadRequest() throws Exception {
    String invalidFilterRequest = """
        {
            "name": "Invalid Amount Condition",
            "conditions": [
                {
                    "type": "amount"
                }
            ],
            "active": true
        }
        """;

    mockMvc.perform(post("/api/filters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidFilterRequest))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testCreateFilter_InvalidInput_MissingConditions_ReturnsBadRequest() throws Exception {
    String invalidFilterRequest = """
        {
            "name": "Filter Without Conditions",
            "active": true
        }
        """;

    mockMvc.perform(post("/api/filters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidFilterRequest))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testGetAllFilters_ReturnsListOfFilters() throws Exception {
    // Create test data
    FilterDto filter1 = new FilterDto(
        UUID.randomUUID(),
        "First Filter",
        List.of(
            new TitleCondition(TitleCondition.TitleOperator.CONTAINS, "test")
        ),
        true
    );
    
    FilterDto filter2 = new FilterDto(
        UUID.randomUUID(),
        "Second Filter",
        List.of(
            new AmountCondition(AmountCondition.AmountOperator.GT, 50.0)
        ),
        false
    );

    List<FilterDto> expectedFilters = List.of(filter1, filter2);

    // Mock the service to return the list of filters
    when(filterService.allFilters()).thenReturn(expectedFilters);

    mockMvc.perform(get("/api/filters")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].name").value("First Filter"))
        .andExpect(jsonPath("$[0].active").value(true))
        .andExpect(jsonPath("$[0].conditions").isArray())
        .andExpect(jsonPath("$[0].conditions", hasSize(1)))
        .andExpect(jsonPath("$[0].conditions[0].type").value("title"))
        .andExpect(jsonPath("$[0].conditions[0].operator").value("CONTAINS"))
        .andExpect(jsonPath("$[0].conditions[0].value").value("test"))
        .andExpect(jsonPath("$[1].id").exists())
        .andExpect(jsonPath("$[1].name").value("Second Filter"))
        .andExpect(jsonPath("$[1].active").value(false))
        .andExpect(jsonPath("$[1].conditions").isArray())
        .andExpect(jsonPath("$[1].conditions", hasSize(1)))
        .andExpect(jsonPath("$[1].conditions[0].type").value("amount"))
        .andExpect(jsonPath("$[1].conditions[0].operator").value("GT"))
        .andExpect(jsonPath("$[1].conditions[0].value").value(50.0));
  }

  @Test
  public void testGetAllFilters_EmptyList_ReturnsEmptyArray() throws Exception {
    // Mock the service to return an empty list
    when(filterService.allFilters()).thenReturn(List.of());

    mockMvc.perform(get("/api/filters")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(0)));
  }

@Test
public void testGetFilterById_ExistingFilter_ReturnsFilter() throws Exception {
  UUID filterId = UUID.randomUUID();
  
  FilterDto filterDto = new FilterDto(
      filterId,
      "Test Filter",
      List.of(new TitleCondition(TitleCondition.TitleOperator.CONTAINS, "test")),
      true
  );
  
  // Mock the service to return the filter entity
  when(filterService.findById(filterId)).thenReturn(Optional.of(filterDto));

  mockMvc.perform(get("/api/filters/" + filterId)
          .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(filterId.toString()))
      .andExpect(jsonPath("$.name").value("Test Filter"))
      .andExpect(jsonPath("$.active").value(true))
      .andExpect(jsonPath("$.conditions").isArray())
      .andExpect(jsonPath("$.conditions", hasSize(1)))
      .andExpect(jsonPath("$.conditions[0].type").value("title"))
      .andExpect(jsonPath("$.conditions[0].operator").value("CONTAINS"))
      .andExpect(jsonPath("$.conditions[0].value").value("test"));
}

@Test
public void testGetFilterById_NonExistentFilter_ReturnsNotFound() throws Exception {
  UUID nonExistentId = UUID.randomUUID();
  
  // Mock the service to return empty Optional
  when(filterService.findById(nonExistentId)).thenReturn(Optional.empty());

  mockMvc.perform(get("/api/filters/" + nonExistentId)
          .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
}

@Test
public void testGetFilterById_InvalidUUID_ReturnsBadRequest() throws Exception {
  String invalidUUID = "invalid-uuid";

  mockMvc.perform(get("/api/filters/" + invalidUUID)
          .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
}
}
