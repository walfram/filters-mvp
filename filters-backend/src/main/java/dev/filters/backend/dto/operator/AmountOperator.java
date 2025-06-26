package dev.filters.backend.dto.operator;

import lombok.Getter;

@Getter
public enum AmountOperator {
  GT(">"), GTE(">="), EQ("="), LTE("<="), LT("<");

  private final String symbol;

  AmountOperator(String symbol) {
    this.symbol = symbol;
  }

}
