package dev.filters.backend.controller;

import dev.filters.backend.dto.FilterDTO;
import dev.filters.backend.service.FilterNotFoundException;
import dev.filters.backend.service.FilterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/filters")
@CrossOrigin(origins = "*")
public class FilterController {

  private final FilterService filterService;

  public FilterController(FilterService filterService) {
    this.filterService = filterService;
  }

  @GetMapping
  public ResponseEntity<List<FilterDTO>> getAllFilters() {
    return ResponseEntity.ok(filterService.allFilters());
  }

  @GetMapping("/{id}")
  public ResponseEntity<FilterDTO> getFilterById(@PathVariable UUID id) {
    Optional<FilterDTO> filter = filterService.findById(id);

    return filter
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<FilterDTO> createFilter(@Valid @RequestBody FilterDTO FilterDTO) {
    FilterDTO created = filterService.create(FilterDTO);
    return ResponseEntity.ok(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<FilterDTO> updateFilter(
      @PathVariable UUID id,
      @Valid @RequestBody FilterDTO FilterDTO) {

    try {
      FilterDTO updated = filterService.update(id, FilterDTO);
      return ResponseEntity.ok(updated);
    } catch (FilterNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteFilter(@PathVariable UUID id) {
    boolean deleted = filterService.deleteById(id);

    if (deleted) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
