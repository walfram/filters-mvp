package dev.filters.hub.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilterController.class)
public class FilterControllerHappyPathsTest {

  @Autowired
  private MockMvc mockMvc;

  void should_return_ok_for_create_filter_request_with_extra_fields() {}

  @Test
  void should_return_ok_for_filter_list_request() throws Exception {
    mockMvc.perform(
        get("/api/filter")
            .contentType(MediaType.APPLICATION_JSON)
    )
        .andExpect(status().isOk());
  }

  @Test
  void should_return_ok_for_valid_delete_filter_request() throws Exception {
    mockMvc.perform(
        delete("/api/filter/xxx")
            .contentType(MediaType.APPLICATION_JSON)
    )
        .andExpect(status().isOk());
  }

  @Test
  void should_return_ok_for_valid_update_filter_request() throws Exception {
    String updateFilterRequest = StreamUtils.copyToString(
        new ClassPathResource("requests/update-filter-request-valid.json").getInputStream(),
        StandardCharsets.UTF_8
    );

    mockMvc.perform(
            patch("/api/filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateFilterRequest)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void should_return_ok_for_valid_create_filter_request() throws Exception {
    String createFilterRequest = StreamUtils.copyToString(
        new ClassPathResource("requests/create-filter-request-valid.json").getInputStream(),
        StandardCharsets.UTF_8
    );

    mockMvc.perform(
        post("/api/filter")
            .contentType(MediaType.APPLICATION_JSON)
            .content(createFilterRequest)
    )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

}
