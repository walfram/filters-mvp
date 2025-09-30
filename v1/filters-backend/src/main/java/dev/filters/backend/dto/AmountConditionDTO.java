package dev.filters.backend.dto;

import dev.filters.backend.dto.operator.AmountOperator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmountConditionDTO implements Condition {

  private AmountOperator operator;
  private Double value;

}
