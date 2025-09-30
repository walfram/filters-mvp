package dev.filters.hub.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.filters.hub.domain.Filter;
import dev.filters.hub.service.FilterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FilterController.class)
public class FilterControllerHappyPathsTest {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private FilterService filterService;

	@Test
	void should_return_ok_for_update_filter_request_with_extra_fields() throws Exception {
		String payload = """
				{
				  "id": "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11",
				  "foo": "bar",
				  "name": "Updated High Value Transactions",
				  "active": true,
				  "criteria": [
				    {
				      "type": "number",
				      "operator": "gt",
				      "value": 1500.0
				    }
				  ]
				}
				""";

		when(filterService.update(any(UUID.class), any(Filter.class))).thenReturn(mapper.readValue(payload, Filter.class));

		mockMvc.perform(
						put("/api/filter/a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
								.contentType(MediaType.APPLICATION_JSON)
								.content(payload)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").exists());
	}

	@Test
	void should_return_ok_for_create_filter_request_with_extra_fields() throws Exception {
		String payload = """
				{
					"foo": "bar",
					"name": "Updated High Value Transactions",
					"active": true,
					"criteria": [
						{
							"type": "number",
							"operator": "gt",
							"value": 1500.0
						}
					]
				}
				""";

		UUID id = UUID.randomUUID();
		Filter filter = mapper.readValue(payload, Filter.class);
		when(filterService.create(any(Filter.class))).thenReturn(new Filter(id, filter));

		mockMvc.perform(
						post("/api/filter")
								.contentType(MediaType.APPLICATION_JSON)
								.content(payload)
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(header().exists("Location"))
				.andExpect(header().string("Location", "/api/filter/" + id))
				.andExpect(jsonPath("$.name").exists());
	}

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
						delete("/api/filter/a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
								.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isNoContent());
	}

	@ParameterizedTest
	@ValueSource(strings = {
			// Update Filter: with 1 amount condition
			"""
			{
				"id": "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11",
				"name": "Updated High Value Transactions",
				"active": true,
				"criteria": [
					{ "type": "number", "operator": "gt", "value": 1500.0 }
				]
			}
			""", // Newline after opening """
			// Update Filter: with amount and title criteria
			"""
			{
				"id": "b1cdef00-1d2e-3f4a-5b6c-7d8e9f0a1b2c",
				"name": "Updated Invoice Filter",
				"active": true,
				"criteria": [
					{ "type": "number", "operator": "lte", "value": 600.0 },
					{ "type": "string", "operator": "contains", "value": "receipt" }
				]
			}
			""",
			// Update Filter: with amount, title, and date criteria
			"""
			{
				"id": "c2d3e4f5-6a7b-8c9d-0e1f-2a3b4c5d6e7f",
				"name": "Updated Recent Large Reports",
				"active": false,
				"criteria": [
					{ "type": "number", "operator": "gte", "value": 250.0 },
					{ "type": "string", "operator": "startsWith", "value": "Revised Report" },
					{ "type": "date", "operator": "after", "value": "2024-06-01" }
				]
			}
			""",
			// Update Filter: with 1 title condition
			"""
			{
				"id": "d3e4f5a6-7b8c-9d0e-1f2a-3b4c5d6e7f8a",
				"name": "Updated Marketing Emails",
				"active": false,
				"criteria": [
					{ "type": "string", "operator": "endsWith", "value": "newsletter" }
				]
			}
			""",
			// Update Filter: with 1 date condition
			"""
			{
				"id": "e4f5a6b7-8c9d-0e1f-2a3b-4c5d6e7f8a9b",
				"name": "Updated Last Month's Entries",
				"active": false,
				"criteria": [
					{ "type": "date", "operator": "before", "value": "2025-06-01" }
				]
			}
			""",
			// Update Filter: with all condition types
			"""
			{
				"id": "f5a6b7c8-9d0e-1f2a-3b4c-5d6e7f8a9b0c",
				"name": "Updated All Condition Types Example",
				"active": false,
				"criteria": [
					{ "type": "number", "operator": "lt", "value": 50.0 },
					{ "type": "string", "operator": "contains", "value": "New Item" },
					{ "type": "date", "operator": "equals", "value": "2025-07-07" }
				]
			}
			"""
	})
	void should_return_200_for_update_filter_valid_payload(String validPayload) throws Exception {
		JsonNode root = mapper.readTree(validPayload);
		String id = root.get("id").asText();

		when(filterService.update(any(UUID.class), any(Filter.class))).thenReturn(mapper.readValue(validPayload, Filter.class));

		mockMvc.perform(
						put("/api/filter/" + id)
								.contentType(MediaType.APPLICATION_JSON)
								.content(validPayload)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").exists());
	}

	@ParameterizedTest
	@ValueSource(strings = {
			// Create Filter: with 1 amount condition
			"""
			{
				"name": "High Value Transactions",
				"active": true,
				"criteria": [
					{ "type": "number", "operator": "gt", "value": 1000.0 }
				]
			}
			""",
			// Create Filter: with amount and title criteria
			"""
			{
				"name": "Invoice Filter (Amount & Title)",
				"active": false,
				"criteria": [
					{ "type": "number", "operator": "lte", "value": 500.0 },
					{ "type": "string", "operator": "contains", "value": "invoice" }
				]
			}
			""",
			// Create Filter: with amount, title, and date criteria
			"""
			{
				"name": "Recent Large Reports",
				"active": true,
				"criteria": [
					{ "type": "number", "operator": "gte", "value": 200.0 },
					{ "type": "string", "operator": "startsWith", "value": "Report" },
					{ "type": "date", "operator": "after", "value": "2024-01-01" }
				]
			}
			""",
			// Create Filter: with 1 title condition
			"""
			{
				"name": "Marketing Emails",
				"active": true,
				"criteria": [
					{ "type": "string", "operator": "endsWith", "value": "marketing" }
				]
			}
			""",
			// Create Filter: with 1 date condition
			"""
			{
				"name": "Last Month's Entries",
				"active": true,
				"criteria": [
					{ "type": "date", "operator": "before", "value": "2025-07-01" }
				]
			}
			""",
			// Create Filter: with all condition types
			"""
			{
				"name": "All Condition Types Example",
				"active": true,
				"criteria": [
					{ "type": "number", "operator": "eq", "value": 99.99 },
					{ "type": "string", "operator": "eq", "value": "Specific Item" },
					{ "type": "date", "operator": "equals", "value": "2025-07-06" }
				]
			}
			"""
	})
	void should_return_201_for_create_filter_valid_payload(String validPayload) throws Exception {
		UUID id = UUID.randomUUID();
		Filter filter = mapper.readValue(validPayload, Filter.class);

		when(filterService.create(any(Filter.class))).thenReturn(new Filter(id, filter));

		mockMvc.perform(
						post("/api/filter")
								.contentType(MediaType.APPLICATION_JSON)
								.content(validPayload)
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(header().exists("Location"))
				.andExpect(header().string("Location", "/api/filter/" + id))
				.andExpect(jsonPath("$.name").exists());
	}

}
