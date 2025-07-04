package dev.filters.hub.controller;

import dev.filters.hub.domain.Filter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class FilterController {

  @PostMapping("/filter")
  public ResponseEntity<Filter> create(@Valid @RequestBody Filter filter) {
    return ResponseEntity.ok(filter);
  }

  @PatchMapping("/filter/{filterId}")
  public ResponseEntity<Filter> update(@Valid @RequestBody Filter filter, @PathVariable String filterId) {
    return ResponseEntity.ok(filter);
  }

  @DeleteMapping("/filter/{filterId}")
  public ResponseEntity<Void> delete(@PathVariable String filterId) {
    return ResponseEntity.noContent().build();
  }

}
