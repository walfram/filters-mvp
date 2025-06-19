package dev.filters.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.filters.backend.dto.AmountCondition;
import dev.filters.backend.dto.DateCondition;
import dev.filters.backend.dto.FilterDto;
import dev.filters.backend.dto.TitleCondition;
import dev.filters.backend.service.FilterNotFoundException;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

  @Test
  public void testUpdateFilter_ValidInput_ReturnsUpdatedFilter() throws Exception {
    UUID filterId = UUID.randomUUID();
    
    // Updated filter data - everything changed except ID
    FilterDto updateFilterDto = new FilterDto(
        filterId, // Same ID as path parameter
        "Updated Filter Name", // New name
        List.of( // New conditions
            new TitleCondition(TitleCondition.TitleOperator.ENDS_WITH, "updated"),
            new AmountCondition(AmountCondition.AmountOperator.LT, 250.0),
            new DateCondition(DateCondition.DateOperator.AFTER, "2023-06-01T00:00:00")
        ),
        false // New active status
    );

    // Mock the service to return the updated DTO
    when(filterService.update(eq(filterId), any(FilterDto.class))).thenReturn(updateFilterDto);

    String updateFilterRequest = objectMapper.writeValueAsString(updateFilterDto);

    mockMvc.perform(put("/api/filters/" + filterId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateFilterRequest))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        // Verify ID remains the same
        .andExpect(jsonPath("$.id").value(filterId.toString()))
        // Verify name was updated
        .andExpect(jsonPath("$.name").value("Updated Filter Name"))
        // Verify active status was updated
        .andExpect(jsonPath("$.active").value(false))
        // Verify conditions were updated
        .andExpect(jsonPath("$.conditions").isArray())
        .andExpect(jsonPath("$.conditions", hasSize(3)))
        .andExpect(jsonPath("$.conditions[0].type").value("title"))
        .andExpect(jsonPath("$.conditions[0].operator").value("ENDS_WITH"))
        .andExpect(jsonPath("$.conditions[0].value").value("updated"))
        .andExpect(jsonPath("$.conditions[1].type").value("amount"))
        .andExpect(jsonPath("$.conditions[1].operator").value("LT"))
        .andExpect(jsonPath("$.conditions[1].value").value(250.0))
        .andExpect(jsonPath("$.conditions[2].type").value("date"))
        .andExpect(jsonPath("$.conditions[2].operator").value("AFTER"))
        .andExpect(jsonPath("$.conditions[2].value").value("2023-06-01T00:00:00"));
    
    // Verify that the service was called with the correct parameters
    verify(filterService).update(eq(filterId), argThat(dto -> 
        dto.getId().equals(filterId) &&
        dto.getName().equals("Updated Filter Name") &&
        dto.getActive().equals(false) &&
        dto.getConditions().size() == 3
    ));
  }

  @Test
  public void testUpdateFilter_NonExistentFilter_ReturnsNotFound() throws Exception {
    UUID nonExistentId = UUID.randomUUID();
    
    FilterDto updateFilterDto = new FilterDto(
        nonExistentId,
        "Non-existent Filter",
        List.of(new TitleCondition(TitleCondition.TitleOperator.CONTAINS, "test")),
        true
    );

    // Mock the service to throw FilterNotFoundException
    when(filterService.update(eq(nonExistentId), any(FilterDto.class)))
        .thenThrow(new FilterNotFoundException("Filter not found"));

    String updateFilterRequest = objectMapper.writeValueAsString(updateFilterDto);

    mockMvc.perform(put("/api/filters/" + nonExistentId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateFilterRequest))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testUpdateFilter_InvalidInput_MissingName_ReturnsBadRequest() throws Exception {
    UUID filterId = UUID.randomUUID();
    
    String invalidUpdateRequest = """
        {
            "id": "%s",
            "conditions": [{"type": "title", "operator": "CONTAINS", "value": "test"}],
            "active": true
        }
        """.formatted(filterId);

    mockMvc.perform(put("/api/filters/" + filterId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidUpdateRequest))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testUpdateFilter_InvalidInput_EmptyConditions_ReturnsBadRequest() throws Exception {
    UUID filterId = UUID.randomUUID();
    
    String invalidUpdateRequest = """
        {
            "id": "%s",
            "name": "Updated Filter",
            "conditions": [],
            "active": true
        }
        """.formatted(filterId);

    mockMvc.perform(put("/api/filters/" + filterId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidUpdateRequest))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testUpdateFilter_InvalidUUID_ReturnsBadRequest() throws Exception {
    String invalidUUID = "invalid-uuid";
    
    FilterDto updateFilterDto = new FilterDto(
        UUID.randomUUID(),
        "Test Filter",
        List.of(new TitleCondition(TitleCondition.TitleOperator.CONTAINS, "test")),
        true
    );

    String updateFilterRequest = objectMapper.writeValueAsString(updateFilterDto);

    mockMvc.perform(put("/api/filters/" + invalidUUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateFilterRequest))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testUpdateFilter_InvalidCondition_AmountTypeWithDateOperator_ReturnsBadRequest() throws Exception {
    UUID filterId = UUID.randomUUID();
    
    String invalidUpdateRequest = """
        {
            "id": "%s",
            "name": "Invalid Update Filter",
            "conditions": [
              {
                  "type": "amount",
                  "operator": "AFTER",
                  "value": "2022-01-01T00:00:00"
              }
          ],
            "active": true
        }
        """.formatted(filterId);

    mockMvc.perform(put("/api/filters/" + filterId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidUpdateRequest))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testDeleteFilter_ExistingFilter_ReturnsNoContent() throws Exception {
    UUID filterId = UUID.randomUUID();

    // Mock the service to return true (filter was successfully deleted)
    when(filterService.deleteById(filterId)).thenReturn(true);

    mockMvc.perform(delete("/api/filters/" + filterId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent())
        .andExpect(content().string(""));

    // Verify that the service was called with the correct ID
    verify(filterService).deleteById(filterId);
  }

  @Test
  public void testDeleteFilter_NonExistentFilter_ReturnsNotFound() throws Exception {
    UUID nonExistentId = UUID.randomUUID();

    // Mock the service to return false (filter was not found)
    when(filterService.deleteById(nonExistentId)).thenReturn(false);

    mockMvc.perform(delete("/api/filters/" + nonExistentId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(""));

    // Verify that the service was called with the correct ID
    verify(filterService).deleteById(nonExistentId);
  }

  @Test
  public void testDeleteFilter_InvalidUUID_ReturnsBadRequest() throws Exception {
    String invalidUUID = "invalid-uuid";

    mockMvc.perform(delete("/api/filters/" + invalidUUID)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    // Verify that the service was never called due to invalid UUID
    verify(filterService, never()).deleteById(any(UUID.class));
  }

  @Test
  public void testDeleteFilter_EmptyId_ReturnsNotFound() throws Exception {
    mockMvc.perform(delete("/api/filters/")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    // Verify that the service was never called
    verify(filterService, never()).deleteById(any(UUID.class));
  }
}
