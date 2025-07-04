package dev.filters.hub.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.filters.hub.domain.Filter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilterController.class)
public class FilterControllerUnhappyPathsTest {

  @Autowired
  private MockMvc mockMvc;

//  void should_return_400_for_update_filter_request_with_malformed_amount_condition() {}
//  void should_return_400_for_update_filter_request_with_malformed_date_condition() {}
//  void should_return_400_for_update_filter_request_with_malformed_title_condition() {}

  @ParameterizedTest
  @ValueSource(strings = {
      "{ \"name\": \"test\", \"status\": \"ACTIVE\", \"conditions\": [ { \"type\": \"AMOUNT\", \"value\": \"not-a-number\" } ] }",
      "{ \"name\": \"test\", \"status\": \"ACTIVE\", \"conditions\": [ { \"type\": \"DATE\", \"value\": \"invalid-date\" } ] }",
      "{ \"name\": \"test\", \"status\": \"ACTIVE\", \"conditions\": [ { \"type\": \"TITLE\", \"value\": \"\" } ] }" // Assuming empty title is malformed
  })
  void should_return_400_for_update_filter_request_with_malformed_conditions(String malformedPayload) throws Exception {
    mockMvc.perform(put("/filters/123")
            .contentType(MediaType.APPLICATION_JSON)
            .content(malformedPayload))
        .andExpect(status().isBadRequest());
  }

  void should_return_400_for_update_filter_request_empty_conditions() {}
  void should_return_400_foe_update_filter_request_no_conditions() {}
  void should_return_400_for_update_filter_request_no_status() {}
  void should_return_400_for_update_filter_request_no_name() {}
  void should_return_404_for_update_filter_with_valid_payload_and_invalid_id() {}

  void should_return_400_for_create_filter_request_with_malformed_amount_condition() {}
  void should_return_400_for_create_filter_request_with_malformed_date_condition() {}
  void should_return_400_for_create_filter_request_with_malformed_title_condition() {}

  @ParameterizedTest
  @ValueSource(strings = {
      "{ \"name\": \"test\", \"status\": \"ACTIVE\", \"conditions\": [ { \"type\": \"AMOUNT\", \"value\": \"not-a-number\" } ] }",
      "{ \"name\": \"test\", \"status\": \"ACTIVE\", \"conditions\": [ { \"type\": \"DATE\", \"value\": \"invalid-date\" } ] }",
  })
  void should_return_400_for_create_filter_request_with_empty_conditions(String malformedPayload) throws Exception {
    //
  }

  @Test
  void should_return_400_for_create_filter_request_no_status() {
  }

  @Test
  void should_return_400_for_create_filter_request_no_name() {
  }

  static List<String> malformedPayloads() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    return List.of(
        mapper.writeValueAsString(new Filter(null, null, null, null)),
        mapper.writeValueAsString(new Filter(null, "", false, List.of())),
        mapper.writeValueAsString(new Filter(null, "name", false, null))
    );
  }

  @ParameterizedTest
  @MethodSource("malformedPayloads")
  void should_return_400_for_create_filter_malformed_payload(String malformedPayload) throws Exception {
    mockMvc.perform(
            post("/api/filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(malformedPayload)
        )
        .andExpect(status().isBadRequest());
  }

}
