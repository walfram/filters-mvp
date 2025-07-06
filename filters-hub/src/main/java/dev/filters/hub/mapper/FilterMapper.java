package dev.filters.hub.mapper;

import dev.filters.hub.domain.Filter;
import dev.filters.hub.entity.FilterEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FilterMapper {

  FilterEntity toEntity(Filter filter);

  Filter toDto(FilterEntity entity);

  List<Filter> toList(List<FilterEntity> entityList);

}
