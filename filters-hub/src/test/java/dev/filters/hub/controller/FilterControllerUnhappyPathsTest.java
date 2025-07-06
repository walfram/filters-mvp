package dev.filters.hub.controller;

import dev.filters.hub.service.FilterService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilterController.class)
public class FilterControllerUnhappyPathsTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private FilterService filterService;

  @ParameterizedTest
  @ValueSource(strings = {
      // Filter: null id (not explicitly validated but good to test for)
      "{ \"id\": null, \"name\": \"Valid Filter\", \"active\": true, \"conditions\": [] }",
      // Filter: blank name
      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"name\": \"\", \"active\": true, \"conditions\": [] }",
      // Filter: null name
      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"name\": null, \"active\": true, \"conditions\": [] }",
      // Filter: null active
      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"name\": \"Valid Filter\", \"active\": null, \"conditions\": [] }",

      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"name\": null, \"active\": true }",
      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"name\": null, \"conditions\": [] }",
      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"active\": true, \"conditions\": [] }",
      "{}",

      // Filter: empty conditions list (assuming @NotEmpty applies to the list being non-empty, not just non-null)
      // If @NotEmpty means "at least one element", then [] is invalid. If it means "not null and not empty", it's also invalid.
      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"name\": \"Valid Filter\", \"active\": true, \"conditions\": [] }",
      // Filter: null conditions list
      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"name\": \"Valid Filter\", \"active\": true, \"conditions\": null }",
      // Filter with AmountCondition: null operator
      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"name\": \"Filter with Invalid Amount\", \"active\": true, \"conditions\": [ { \"type\": \"amount\", \"operator\": null, \"value\": 10.0 } ] }",
      // Filter with AmountCondition: null value
      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"name\": \"Filter with Invalid Amount\", \"active\": true, \"conditions\": [ { \"type\": \"amount\", \"operator\": \"EQUAL\", \"value\": null } ] }",
      // Filter with DateCondition: null operator
      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"name\": \"Filter with Invalid Date\", \"active\": true, \"conditions\": [ { \"type\": \"date\", \"operator\": null, \"value\": \"2023-01-01\" } ] }",
      // Filter with DateCondition: blank value
      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"name\": \"Filter with Invalid Date\", \"active\": true, \"conditions\": [ { \"type\": \"date\", \"operator\": \"EQUAL\", \"value\": \"\" } ] }",
      // Filter with DateCondition: null value
      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"name\": \"Filter with Invalid Date\", \"active\": true, \"conditions\": [ { \"type\": \"date\", \"operator\": \"EQUAL\", \"value\": null } ] }",
      // Filter with DateCondition: invalid date format (missing day)
      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"name\": \"Filter with Invalid Date\", \"active\": true, \"conditions\": [ { \"type\": \"date\", \"operator\": \"EQUAL\", \"value\": \"2023-01\" } ] }",
      // Filter with DateCondition: invalid date format (extra characters)
      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"name\": \"Filter with Invalid Date\", \"active\": true, \"conditions\": [ { \"type\": \"date\", \"operator\": \"EQUAL\", \"value\": \"2023-01-01x\" } ] }",
      // Filter with TitleCondition: null operator
      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"name\": \"Filter with Invalid Title\", \"active\": true, \"conditions\": [ { \"type\": \"title\", \"operator\": null, \"value\": \"some title\" } ] }",
      // Filter with TitleCondition: blank value
      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"name\": \"Filter with Invalid Title\", \"active\": true, \"conditions\": [ { \"type\": \"title\", \"operator\": \"CONTAINS\", \"value\": \"\" } ] }",
      // Filter with TitleCondition: null value
      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"name\": \"Filter with Invalid Title\", \"active\": true, \"conditions\": [ { \"type\": \"title\", \"operator\": \"CONTAINS\", \"value\": null } ] }",
      // Filter with multiple invalid conditions (one of each type)
      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"name\": \"Filter with Multiple Invalid Conditions\", \"active\": true, \"conditions\": [ { \"type\": \"amount\", \"operator\": null, \"value\": 10.0 }, { \"type\": \"date\", \"operator\": \"EQUAL\", \"value\": \"invalid-date\" }, { \"type\": \"title\", \"operator\": \"CONTAINS\", \"value\": \"\" } ] }",
      // Filter with valid top-level fields but a null condition in the list
      "{ \"id\": \"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\", \"name\": \"Valid Filter Name\", \"active\": true, \"conditions\": [null] }"
  })
  void should_return_400_for_create_filter_malformed_payload(String malformedPayload) throws Exception {
    mockMvc.perform(
            post("/api/filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(malformedPayload)
        )
        .andExpect(status().isBadRequest());
  }

}
