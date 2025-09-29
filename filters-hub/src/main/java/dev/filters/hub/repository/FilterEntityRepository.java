package dev.filters.hub.repository;

import dev.filters.hub.entity.FilterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FilterEntityRepository extends JpaRepository<FilterEntity, UUID> {



}
