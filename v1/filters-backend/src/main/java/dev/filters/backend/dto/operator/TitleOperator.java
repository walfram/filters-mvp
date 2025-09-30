package dev.filters.backend.dto.operator;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum TitleOperator {
  STARTS_WITH("startsWith"), ENDS_WITH("endsWith"), CONTAINS("contains"), EQUALS("equals");

  @JsonValue
  private final String name;

  TitleOperator(String name) {
    this.name = name;
  }

  public static TitleOperator fromSymbol(String operator) {
    for (TitleOperator op : values()) {
      if (op.name.equals(operator)) {
        return op;
      }
    }
    throw new IllegalArgumentException("Unknown TitleOperator: " + operator);
  }
}
