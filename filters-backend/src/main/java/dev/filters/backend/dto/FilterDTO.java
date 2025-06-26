package dev.filters.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterDTO {
  private UUID id;

  @NotBlank(message = "Filter name is required")
  private String name;

  @NotNull(message = "At least one condition is required")
  @Valid
  private List<FilterConditionDTO> conditions;

  @NotNull(message = "Active status is required")
  private Boolean active;
}
