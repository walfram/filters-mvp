package dev.filters.hub.bootstrap;

import dev.filters.hub.domain.*;
import dev.filters.hub.entity.FilterEntity;
import dev.filters.hub.mapper.FilterMapper;
import dev.filters.hub.repository.FilterEntityRepository;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class DatabaseInitializer implements CommandLineRunner {

	private static final Logger logger = getLogger(DatabaseInitializer.class);

	private final FilterEntityRepository filterEntityRepository;
	private final FilterMapper filterMapper;

	public DatabaseInitializer(FilterEntityRepository filterEntityRepository, FilterMapper filterMapper) {
		this.filterEntityRepository = filterEntityRepository;
		this.filterMapper = filterMapper;
	}

	@Override
	public void run(String... args) throws Exception {
		if (filterEntityRepository.count() == 0) {
			logger.info("No filters found, creating");

			List<FilterEntity> entities = sampleFilters()
					.stream()
					.peek(filter -> logger.debug("filter = {}", filter))
					.map(filterMapper::toEntity)
					.peek(filterEntity -> logger.info("saved filter = {}", filterEntity))
					.toList();

			filterEntityRepository.saveAll(entities);
		}
	}

	private List<Filter> sampleFilters() {
		return List.of(
				// Filter 1: Simple Amount Condition
				new Filter(
						null,
						"High Value Purchases",
						true,
						List.of(
								new AmountCriterion(null, AmountOperator.gt, 500.0)
						)
				),
				// Filter 2: Title and Date Conditions
				new Filter(
						null,
						"Recent Reports",
						true,
						List.of(
								new TitleCriterion(null, TitleOperator.contains, "Report", false),
								new DateCriterion(null, DateOperator.after, LocalDate.now().minusMonths(3).toString()) // Last 3 months
						)
				),
				// Filter 3: Multiple Conditions of Different Types
				new Filter(
						null,
						"Sales Over 100 on Specific Date",
						false, // Could be an inactive filter
						List.of(
								new AmountCriterion(null, AmountOperator.gte, 100.0),
								new TitleCriterion(null, TitleOperator.startsWith, "Sale", false),
								new DateCriterion(null, DateOperator.equals, "2025-06-15")
						)
				),
				// Filter 4: Only Title Condition (Case-Insensitive Example if operators allowed)
				new Filter(
						null,
						"Customer Feedback",
						true,
						List.of(
								new TitleCriterion(null, TitleOperator.eq, "Customer Feedback", false)
						)
				),
				// Filter 5: Less Common Amount Operator & Date Before
				new Filter(
						null,
						"Old Low Value Invoices",
						true,
						List.of(
								new AmountCriterion(null, AmountOperator.lte, 75.0),
								new DateCriterion(null, DateOperator.before, "2024-12-31")
						)
				)
		);
	}

}
