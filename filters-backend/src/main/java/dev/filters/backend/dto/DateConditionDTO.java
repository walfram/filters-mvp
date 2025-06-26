package dev.filters.backend.dto;

import dev.filters.backend.dto.operator.DateOperator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateConditionDTO implements Condition {

  DateOperator operator;
  LocalDateTime value;

  public DateConditionDTO(DateOperator operator, String value) {
    this(operator, Instant.parse(value).atZone(ZoneId.of("UTC")).toLocalDateTime());
  }

}
