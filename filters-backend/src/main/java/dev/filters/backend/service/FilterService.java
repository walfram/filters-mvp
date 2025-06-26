package dev.filters.backend.service;

import dev.filters.backend.dto.FilterDTO;
import dev.filters.backend.entity.FilterEntity;
import dev.filters.backend.repository.FilterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilterService {

  private final FilterConverter filterConverter;
  private final FilterRepository filterRepository;

  @Transactional(readOnly = true)
  public Optional<FilterDTO> findById(UUID id) {
    return filterRepository.findById(id)
        .map(filterConverter::toDto);
  }

  @Transactional
  public FilterDTO create(FilterDTO FilterDTO) {
    FilterEntity created = filterConverter.toEntity(FilterDTO);
    log.debug("Created filter: {}", created);
    filterRepository.save(created);
    return filterConverter.toDto(created);
  }

  @Transactional
  public FilterDTO update(UUID id, FilterDTO filterDTO) throws FilterNotFoundException {
    Optional<FilterEntity> existingFilter = filterRepository.findById(id);

    if (existingFilter.isEmpty()) {
      throw new FilterNotFoundException("Filter not found");
    }

    FilterEntity entity = filterConverter.toEntity(filterDTO);
    filterRepository.save(entity);

    return filterConverter.toDto(entity);
  }

  @Transactional
  public boolean deleteById(UUID id) {
    if (filterRepository.existsById(id)) {
      filterRepository.deleteById(id);
      return true;
    }
    return false;
  }

  @Transactional(readOnly = true)
  public List<FilterDTO> allFilters() {
    return filterRepository
      .findAll()
      .stream()
      .map(filterConverter::toDto)
      .toList();
  }
}
