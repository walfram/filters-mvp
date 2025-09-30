package dev.filters.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "filter_conditions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "filter")
public class FilterConditionEntity {

  @Id
  @Column(name = "id", length = 36)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY) // Use LAZY fetching to avoid loading Filter when not needed
  @JoinColumn(name = "filter_id", nullable = false)
  private FilterEntity filter;

  @Column(name = "condition_type", nullable = false)
  private String conditionType;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "condition_details", columnDefinition = "JSON", nullable = false)
  private String conditionDetails;

}
