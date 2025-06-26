package dev.filters.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class DateConditionDTO extends FilterConditionDTO {

  @NotNull(message = "Date operator is required")
  private DateOperator operator;

  @NotNull(message = "Date value is required")
  private Instant value;

  public DateConditionDTO() {
    super("date");
  }
}
