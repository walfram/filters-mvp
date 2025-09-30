package dev.filters.backend.dto;

import dev.filters.backend.dto.operator.TitleOperator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TitleConditionDTO implements Condition {

  private TitleOperator operator;
  private String value;

}
