package dev.filters.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.filters.backend.dto.AmountCondition;
import dev.filters.backend.dto.DateCondition;
import dev.filters.backend.dto.FilterDto;
import dev.filters.backend.dto.TitleCondition;
import dev.filters.backend.service.FilterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
}
