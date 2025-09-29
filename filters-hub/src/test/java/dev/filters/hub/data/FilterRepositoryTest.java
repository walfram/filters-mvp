package dev.filters.hub.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.filters.hub.domain.Filter;
import dev.filters.hub.entity.FilterEntity;
import dev.filters.hub.mapper.FilterMapper;
import dev.filters.hub.repository.FilterEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(FilterMapper.class)
public class FilterRepositoryTest {

	@Autowired
	private FilterEntityRepository repository;

	@Autowired
	private TestEntityManager manager;

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private FilterMapper filterMapper;

	private static final String[] JSON_FILES = {
			"filters/filter-1-criterion.json",
			"filters/filter-2-criteria.json",
			"filters/filter-3-criteria.json",
			"filters/filter-5-criteria.json"
	};

	static Stream<Arguments> jsonContentProvider() throws IOException {
		return Stream.of(JSON_FILES)
				.map(filePath -> {
					try {
						// Read the JSON file content
						Resource resource = new ClassPathResource(filePath);
//						byte[] contentBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
//						String content = new String(contentBytes, StandardCharsets.UTF_8);
						Filter filter = objectMapper.readValue(resource.getInputStream(), Filter.class);

						return Arguments.of(resource.getFilename(), filter);
					} catch (IOException e) {
						throw new RuntimeException("Failed to read JSON file: " + filePath, e);
					}
				});
	}

	@ParameterizedTest
	@MethodSource("jsonContentProvider")
		// TODO strip uuids (new filter)
	void should_save_new_filter(String fileName, Filter filter) {
		FilterEntity filterEntity = filterMapper.toEntity(filter);

		FilterEntity entity = assertDoesNotThrow(() -> repository.save(filterEntity));

		UUID id = entity.getId();
		assertNotNull(id);

		FilterEntity found = manager.find(FilterEntity.class, id);
		assertEquals(filterEntity, found);
	}

	@Test
	void should_update_filter() {

	}

	void should_delete_filter() {

	}

}
