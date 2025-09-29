package dev.filters.hub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "title_criterion")
@Data
public class TitleCriterionEntity extends CriterionEntity {

	private String operator;

	@Column(name = "title_value")
	private String value;
	private Boolean caseSensitive;

}
