package dev.filters.backend.dto;

import dev.filters.backend.entity.FilterEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class FilterDto {

  private final UUID id;

  @NotBlank(message = "Filter name is required")
  private final String name;

  @NotEmpty(message = "At least one condition is required")
  @Valid
  private final List<FilterCondition> conditions;

  @NotNull
  private final Boolean active;

  public FilterEntity entity(UUID id) {
    return new FilterEntity(id, name, conditions, active);
  }
}
