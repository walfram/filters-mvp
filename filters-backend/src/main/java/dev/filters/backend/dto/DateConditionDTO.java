package dev.filters.backend.dto;

import dev.filters.backend.dto.operator.DateOperator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateConditionDTO implements Condition {

  DateOperator operator;
  LocalDate value;

  public DateConditionDTO(DateOperator operator, String value) {
    this(operator, LocalDate.parse(value));
  }

}
