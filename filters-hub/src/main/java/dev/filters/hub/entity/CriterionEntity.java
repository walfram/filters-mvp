package dev.filters.hub.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "criterion")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "criterion_type", discriminatorType = DiscriminatorType.STRING)
public abstract class CriterionEntity {

	@Id
	private String id; // Matches 'id: string' in BaseCriterion

	// Column to hold the specific type ('number', 'string', 'date') from the TypeScript interface
	@Column(name = "type", insertable = false, updatable = false) // Handled by concrete class
	private String type;

	// Many-to-One back-reference to the parent Filter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "filter_id", nullable = false)
	private FilterEntity filter;

	// Getters and Setters (omitted for brevity)
	// Note: 'type' will be set in the concrete classes or managed by the discriminator
}

