package dev.filters.backend.service;

import dev.filters.backend.dto.FilterDto;
import dev.filters.backend.entity.FilterEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FilterService {

  private final List<FilterEntity> filters = new ArrayList<>();

  public Optional<FilterDto> findById(UUID id) {
    return filters.stream()
        .filter(f -> f.getId().equals(id))
        .map(FilterEntity::dto)
        .findFirst();
  }

  public FilterDto create(FilterDto filterDto) {
    FilterEntity created = filterDto.entity(UUID.randomUUID());
    filters.add(created);
    return created.dto();
  }

  public FilterDto update(UUID id, FilterDto filterDto) throws FilterNotFoundException {
    Optional<FilterEntity> existingFilter = filters.stream()
        .filter(f -> f.getId().equals(id))
        .findFirst();

    if (existingFilter.isEmpty()) {
      throw new FilterNotFoundException("Filter not found");
    }

    filters.removeIf(f -> f.getId().equals(id));
    FilterEntity entity = filterDto.entity(id);
    filters.add(entity);

    return entity.dto();
  }

  public boolean deleteById(UUID id) {
    return filters.removeIf(f -> f.getId().equals(id));
  }

  public List<FilterDto> allFilters() {
    return filters.stream().map(FilterEntity::dto).toList();
  }
}
