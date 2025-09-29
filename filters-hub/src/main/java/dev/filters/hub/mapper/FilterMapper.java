package dev.filters.hub.mapper;

import dev.filters.hub.domain.*;
import dev.filters.hub.entity.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class FilterMapper {

	public FilterEntity toEntity(Filter filter) {
		FilterEntity entity = new FilterEntity();

		entity.setId(filter.id());
		entity.setName(filter.name());
		entity.setActive(filter.active());

		entity.setCriteria( filter.criteria().stream().map(this::toCriterionEntity).toList() );

		return entity;
	}

	private CriterionEntity toCriterionEntity(@NotNull @Valid Criterion source) {
		return switch (source) {
			case AmountCriterion amountCriterion -> amountCriterionEntity(amountCriterion);
			case TitleCriterion titleCriterion -> titleCriterionEntity(titleCriterion);
			case DateCriterion dateCriterion -> dateCriterionEntity(dateCriterion);
			default -> throw new IllegalStateException("toCriterionEntity: Unexpected value " + source);
		};
	}

	private DateCriterionEntity dateCriterionEntity(DateCriterion dateCriterion) {
		DateCriterionEntity entity = new DateCriterionEntity();

		entity.setOperator(dateCriterion.operator().name());
		entity.setValue(Instant.parse(dateCriterion.value()));

		return entity;
	}

	private TitleCriterionEntity titleCriterionEntity(TitleCriterion titleCriterion) {
		TitleCriterionEntity entity = new TitleCriterionEntity();

		entity.setOperator(titleCriterion.operator().name());
		entity.setValue(titleCriterion.value());

		return entity;
	}

	private AmountCriterionEntity amountCriterionEntity(AmountCriterion amountCriterion) {
		AmountCriterionEntity entity = new AmountCriterionEntity();

		entity.setOperator(amountCriterion.operator().name());
		entity.setValue(amountCriterion.value());

		return entity;
	}

	Filter toDto(FilterEntity entity) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	List<Filter> toList(List<FilterEntity> entityList) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

}
