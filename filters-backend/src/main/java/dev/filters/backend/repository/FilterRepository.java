package dev.filters.backend.repository;

import dev.filters.backend.entity.FilterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FilterRepository extends JpaRepository<FilterEntity, UUID> {
}
