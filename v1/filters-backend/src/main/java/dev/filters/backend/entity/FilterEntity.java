package dev.filters.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "filters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "conditions")
public class FilterEntity {

  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "active", nullable = false)
  private boolean active;

  @OneToMany(mappedBy = "filter", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<FilterConditionEntity> conditions = new ArrayList<>();

  public FilterEntity(UUID id, String name, boolean active) {
    this.id = id;
    this.name = name;
    this.active = active;
  }

  public void setConditions(List<FilterConditionEntity> conditions) {
    this.conditions.clear();
    if (conditions != null) {
      conditions.forEach(this::addCondition);
    }
  }

  public void addCondition(FilterConditionEntity condition) {
    this.conditions.add(condition);
    condition.setFilter(this);
  }

  public void removeCondition(FilterConditionEntity condition) {
    this.conditions.remove(condition);
    if (condition.getFilter() == this) {
      condition.setFilter(null);
    }
  }

}
