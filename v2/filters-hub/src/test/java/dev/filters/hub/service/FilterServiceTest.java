package dev.filters.hub.service;

import dev.filters.hub.domain.*;
import dev.filters.hub.mapper.FilterMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({FilterService.class, FilterMapper.class})
@Commit
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FilterServiceTest {

	@Autowired
	private FilterService filterService;

	@Test
	@Order(1)
	void should_save_filter() {
		Filter filter = new Filter(null, "High Value Purchases", true, List.of(
				new AmountCriterion(null, AmountOperator.gt, 500.0)
		));

		Filter saved = assertDoesNotThrow(() -> filterService.create(filter));

		assertNotNull(saved);
		assertNotNull(saved.id());
	}

	@Test
	@Order(2)
	void should_fetch_all_and_update_filter() {
		List<Filter> filters = assertDoesNotThrow(() -> filterService.allFilters());
		Filter first = filters.getFirst();

		List<Criterion> criteria = List.of(
				new TitleCriterion(null, TitleOperator.contains, "foo", false),
				first.criteria().getFirst()
		);

		Filter updatedFirst = new Filter(first.id(), "Updated " + first.name(), true, criteria);

		Filter updated = assertDoesNotThrow(() -> filterService.update(first.id(), updatedFirst));

		assertEquals("Updated " + first.name(), updated.name());
		assertEquals(2, updated.criteria().size());
	}

	@Test
	@Order(3)
	void should_delete_filter() {
		List<Filter> filters = assertDoesNotThrow(() -> filterService.allFilters());
		Filter first = filters.getFirst();

		assertDoesNotThrow(() -> filterService.delete(first.id()));

		List<Filter> afterDelete = assertDoesNotThrow(() -> filterService.allFilters());
		assertTrue(afterDelete.isEmpty());
	}

}
