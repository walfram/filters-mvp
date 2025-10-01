package dev.filters.hub.mapper;

import dev.filters.hub.domain.*;
import dev.filters.hub.entity.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FilterMapper {

	public FilterEntity toEntity(Filter filter) {
		FilterEntity entity = new FilterEntity();

		entity.setId(filter.id());
		entity.setName(filter.name());
		entity.setActive(filter.active());

		entity.setCriteria(new ArrayList<>(filter.criteria().stream().map(this::toCriterionEntity).toList()));
		entity.getCriteria().forEach(criterionEntity -> criterionEntity.setFilter(entity));

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
		entity.setValue(dateCriterion.value());

		return entity;
	}

	private TitleCriterionEntity titleCriterionEntity(TitleCriterion titleCriterion) {
		TitleCriterionEntity entity = new TitleCriterionEntity();

		entity.setOperator(titleCriterion.operator().name());
		entity.setValue(titleCriterion.value());
		entity.setCaseSensitive(titleCriterion.caseSensitive());

		return entity;
	}

	private AmountCriterionEntity amountCriterionEntity(AmountCriterion amountCriterion) {
		AmountCriterionEntity entity = new AmountCriterionEntity();

		entity.setOperator(amountCriterion.operator().name());
		entity.setValue(amountCriterion.value());

		return entity;
	}

	public Filter toDto(FilterEntity entity) {
		List<Criterion> criteria = entity.getCriteria().stream().map(this::toCriterion).toList();
		return new Filter(
				entity.getId(),
				entity.getName(),
				entity.getActive(),
				criteria
		);
	}

	private Criterion toCriterion(CriterionEntity criterionEntity) {
		return switch (criterionEntity) {
			case AmountCriterionEntity amountCriterionEntity -> amountCriterion(amountCriterionEntity);
			case TitleCriterionEntity titleCriterionEntity -> titleCriterion(titleCriterionEntity);
			case DateCriterionEntity dateCriterionEntity -> dateCriterion(dateCriterionEntity);
			default -> throw new IllegalStateException("toCriterion: Unexpected value " + criterionEntity);
		};
	}

	private Criterion dateCriterion(DateCriterionEntity entity) {
		return new DateCriterion(
				entity.getId(),
				DateOperator.valueOf(entity.getOperator()),
				entity.getValue()
		);
	}

	private Criterion titleCriterion(TitleCriterionEntity entity) {
		return new TitleCriterion(
				entity.getId(),
				TitleOperator.valueOf(entity.getOperator()),
				entity.getValue(),
				entity.getCaseSensitive()
		);
	}

	private Criterion amountCriterion(AmountCriterionEntity entity) {
		return new AmountCriterion(
				entity.getId(),
				AmountOperator.valueOf(entity.getOperator()),
				entity.getValue()
		);
	}

}
