package dev.filters.hub.service;

import dev.filters.hub.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class FilterService {
  public Filter create(Filter filter) {
    UUID id = UUID.randomUUID();
    return new Filter(id, filter);
  }

  public List<Filter> allFilter() {
    return List.of(
        // Filter 1: Simple Amount Condition
        new Filter(
            UUID.randomUUID(),
            "High Value Purchases",
            true,
            List.of(
                new AmountCondition(AmountOperator.GREATER_THEN, 500.0)
            )
        ),
        // Filter 2: Title and Date Conditions
        new Filter(
            UUID.randomUUID(),
            "Recent Reports",
            true,
            List.of(
                new TitleCondition(TitleOperator.CONTAINS, "Report"),
                new DateCondition(DateOperator.AFTER, LocalDate.now().minusMonths(3).toString()) // Last 3 months
            )
        ),
        // Filter 3: Multiple Conditions of Different Types
        new Filter(
            UUID.randomUUID(),
            "Sales Over 100 on Specific Date",
            false, // Could be an inactive filter
            List.of(
                new AmountCondition(AmountOperator.GREATER_OR_EQUAL, 100.0),
                new TitleCondition(TitleOperator.STARTS_WITH, "Sale"),
                new DateCondition(DateOperator.EQUAL, "2025-06-15")
            )
        ),
        // Filter 4: Only Title Condition (Case-Insensitive Example if operators allowed)
        new Filter(
            UUID.randomUUID(),
            "Customer Feedback",
            true,
            List.of(
                new TitleCondition(TitleOperator.EQUAL, "Customer Feedback")
            )
        ),
        // Filter 5: Less Common Amount Operator & Date Before
        new Filter(
            UUID.randomUUID(),
            "Old Low Value Invoices",
            true,
            List.of(
                new AmountCondition(AmountOperator.LESS_OR_EQUAL, 75.0),
                new DateCondition(DateOperator.BEFORE, "2024-12-31")
            )
        )
    );
  }

  public Filter update(UUID filterId, Filter filter) {
    return new Filter(filterId, filter);
  }

  public void delete(UUID filterId) {

  }
}
