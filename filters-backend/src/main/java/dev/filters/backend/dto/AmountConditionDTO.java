package dev.filters.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AmountConditionDTO extends FilterConditionDTO {

  @NotNull(message = "Amount operator is required")
  private AmountOperator operator;

  @NotNull(message = "Amount value is required")
  private Double value;

  public AmountConditionDTO() {
    super("amount");
  }

}
