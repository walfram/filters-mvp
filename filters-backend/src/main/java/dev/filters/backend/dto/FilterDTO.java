package dev.filters.backend.dto;

import dev.filters.backend.dto.validation.ValidCondition;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterDTO {
  private UUID id;

  @NotBlank(message = "Filter name is required")
  private String name;

  @NotNull(message = "Conditions list is required")
  @NotEmpty(message = "At least one condition is required")
  @Valid
  @ValidCondition
  private List<Condition> conditions;

  @NotNull(message = "Active status is required")
  private Boolean active;
}
