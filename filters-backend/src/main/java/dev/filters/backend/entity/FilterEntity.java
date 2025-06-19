package dev.filters.backend.entity;

import dev.filters.backend.dto.FilterCondition;
import dev.filters.backend.dto.FilterDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class FilterEntity {

  private final UUID id;
  private final String name;
  private final List<FilterCondition> conditions;
  private final Boolean active;

  public FilterDto dto() {
    return new FilterDto(id, name, conditions, active);
  }
}
