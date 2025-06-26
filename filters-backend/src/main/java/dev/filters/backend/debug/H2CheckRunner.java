package dev.filters.backend.debug;

import dev.filters.backend.dto.FilterDTO;
import dev.filters.backend.entity.FilterEntity;
import dev.filters.backend.repository.FilterRepository;
import dev.filters.backend.service.FilterConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class H2CheckRunner implements CommandLineRunner {

  private final FilterRepository filterRepository;
  private final FilterConverter filterConverter;

  @Override
  @Transactional(readOnly = true)
  public void run(String... args) throws Exception {
    log.debug("H2CheckRunner is executing...");

    List<FilterEntity> allFilters = filterRepository.findAll();
    allFilters.forEach(filter -> {
      log.debug("Filter: {}", filter);
      filter.getConditions().forEach(condition -> {
        log.debug("\tCondition: {}", condition);
      });
    });

    for (FilterEntity filter : allFilters) {
      FilterDTO dto = filterConverter.toDto(filter);
      log.debug(">>> dto = {}", dto);
    }

    log.info("H2CheckRunner completed successfully");
  }

}
