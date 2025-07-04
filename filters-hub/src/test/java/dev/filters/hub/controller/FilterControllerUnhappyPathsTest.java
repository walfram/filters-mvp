package dev.filters.hub.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FilterController.class)
public class FilterControllerUnhappyPathsTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void should_return_400_for_create_filter_request_empty_conditions() {
  }

  @Test
  void should_return_400_for_create_filter_request_no_conditions() {
  }

  @Test
  void should_return_400_for_create_filter_request_no_status() {
  }

  @Test
  void should_return_400_for_create_filter_request_no_name() {
  }

}
