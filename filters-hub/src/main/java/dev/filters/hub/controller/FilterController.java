package dev.filters.hub.controller;

import dev.filters.hub.domain.Filter;
import dev.filters.hub.service.FilterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/api")
@RestController
public class FilterController {

  private final FilterService filterService;

  public FilterController(FilterService filterService) {
    this.filterService = filterService;
  }

  @GetMapping("/filter")
  public ResponseEntity<List<Filter>> filters() {
    return ResponseEntity.ok(filterService.allFilter());
  }

  @PostMapping("/filter")
  public ResponseEntity<Filter> create(@Valid @RequestBody Filter filter) {
    Filter created = filterService.create(filter);
    return ResponseEntity.created(URI.create("/api/filter/" + created.id())).body(created);
  }

  @PutMapping("/filter/{filterId}")
  public ResponseEntity<Filter> update(@Valid @RequestBody Filter filter, @PathVariable String filterId) {
    return ResponseEntity.ok(filter);
  }

  @DeleteMapping("/filter/{filterId}")
  public ResponseEntity<Void> delete(@PathVariable String filterId) {
    return ResponseEntity.noContent().build();
  }

}
