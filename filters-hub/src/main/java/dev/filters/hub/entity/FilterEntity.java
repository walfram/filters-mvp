package dev.filters.hub.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "filter")
public class FilterEntity {

	@Id
	@GeneratedValue
	private UUID id;

	private String name; // Matches 'name: string' in Filter

	private Boolean active;

	// One-to-Many relationship with criteria
	@OneToMany(
			mappedBy = "filter",
			cascade = CascadeType.ALL, // Persist criteria when saving the filter
			orphanRemoval = true,      // Remove criteria if they're removed from the list
			fetch = FetchType.LAZY
	)
	private List<CriterionEntity> criteria; // Matches 'criteria: Criterion[]'

}
