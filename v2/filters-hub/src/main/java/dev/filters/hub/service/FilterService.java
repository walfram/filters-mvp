package dev.filters.hub.service;

import dev.filters.hub.domain.*;
import dev.filters.hub.entity.FilterEntity;
import dev.filters.hub.mapper.FilterMapper;
import dev.filters.hub.repository.FilterEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FilterService {

	private final FilterEntityRepository filterEntityRepository;
	private final FilterMapper filterMapper;

	public FilterService(FilterEntityRepository filterEntityRepository, FilterMapper filterMapper) {
		this.filterEntityRepository = filterEntityRepository;
		this.filterMapper = filterMapper;
	}

	public Filter create(Filter filter) {
		FilterEntity entity = filterMapper.toEntity(filter);
		entity.setId(null);
		FilterEntity saved = filterEntityRepository.save(entity);
		return filterMapper.toDto(saved);
  }

  public List<Filter> allFilters() {
		return filterEntityRepository.findAll().stream().map(filterMapper::toDto).toList();
  }

  public Filter update(UUID filterId, Filter filter) {
    return new Filter(filterId, filter);
  }

  public void delete(UUID filterId) {
		filterEntityRepository.deleteById(filterId);
  }
}
