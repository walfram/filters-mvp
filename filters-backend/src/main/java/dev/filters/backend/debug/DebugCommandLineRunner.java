package dev.filters.backend.debug;

import dev.filters.backend.dto.AmountCondition;
import dev.filters.backend.dto.DateCondition;
import dev.filters.backend.dto.FilterCondition;
import dev.filters.backend.dto.FilterDto;
import dev.filters.backend.dto.TitleCondition;
import dev.filters.backend.service.FilterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("debug")
@RequiredArgsConstructor
@Slf4j
public class DebugCommandLineRunner implements CommandLineRunner {

    private final FilterService filterService;

    @Override
    public void run(String... args) throws Exception {
        log.info("Debug CommandLineRunner is executing...");
        log.info("Generating all possible FilterCondition permutations...");

        List<FilterCondition> allConditions = generateAllFilterConditions();
        
        int counter = 1;
        for (FilterCondition condition : allConditions) {
            FilterDto filterDto = new FilterDto(
                null, // id will be generated
                "Debug Filter " + counter + " - " + condition.type(),
                List.of(condition), // each filter contains exactly 1 condition
                true
            );

            FilterDto created = filterService.create(filterDto);
            log.info("Created filter #{}: {} with condition: {}", counter, created.getName(), condition);
            counter++;
        }
        
        log.info("Generated {} FilterEntities with all possible FilterConditions", allConditions.size());
        log.info("Total filters in service: {}", filterService.allFilters().size());
        log.info("Debug CommandLineRunner completed successfully");
    }

    private List<FilterCondition> generateAllFilterConditions() {
        List<FilterCondition> conditions = new ArrayList<>();
        
        // Generate all AmountConditions
        for (AmountCondition.AmountOperator operator : AmountCondition.AmountOperator.values()) {
            conditions.add(new AmountCondition(operator, 100.0));
            conditions.add(new AmountCondition(operator, 500.0));
            conditions.add(new AmountCondition(operator, 1000.0));
        }
        
        // Generate all TitleConditions
        for (TitleCondition.TitleOperator operator : TitleCondition.TitleOperator.values()) {
            conditions.add(new TitleCondition(operator, "test"));
            conditions.add(new TitleCondition(operator, "sample"));
            conditions.add(new TitleCondition(operator, "debug"));
        }
        
        // Generate all DateConditions
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String pastDate = LocalDateTime.now().minusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String futureDate = LocalDateTime.now().plusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        
        for (DateCondition.DateOperator operator : DateCondition.DateOperator.values()) {
            conditions.add(new DateCondition(operator, currentDate));
            conditions.add(new DateCondition(operator, pastDate));
            conditions.add(new DateCondition(operator, futureDate));
        }
        
        return conditions;
    }
}
