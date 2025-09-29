package dev.filters.hub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "date_criterion")
@Data
public class DateCriterionEntity extends CriterionEntity {

	private String operator;

	@Column(name = "date_value")
	private Instant value;

}
