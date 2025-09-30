package dev.filters.hub.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "criterion")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "criterion_type", discriminatorType = DiscriminatorType.STRING)
@Data
public abstract class CriterionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "type", insertable = false, updatable = false)
	private String type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "filter_id", nullable = false)
	private FilterEntity filter;

}

