package dev.filters.hub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "amount_criterion")
@Data
public class AmountCriterionEntity extends CriterionEntity {

	private String operator;

	@Column(name = "amount_value")
	private Double value;

}
