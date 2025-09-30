package dev.filters.backend.dto.operator;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum DateOperator {
  BEFORE("before"), AFTER("after"), ON("on");

  @JsonValue
  private final String name;

  DateOperator(String name) {
    this.name = name;
  }

  public static DateOperator fromSymbol(String operator) {
    for (DateOperator op : values()) {
      if (op.name.equals(operator)) {
        return op;
      }
    }
    throw new IllegalArgumentException("Unknown DateOperator: " + operator);
  }
}
