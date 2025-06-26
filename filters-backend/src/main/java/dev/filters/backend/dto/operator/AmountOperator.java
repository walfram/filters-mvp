package dev.filters.backend.dto.operator;

import lombok.Getter;

@Getter
public enum AmountOperator {
  GT(">"), GTE(">="), EQ("="), LTE("<="), LT("<");

  private final String symbol;

  AmountOperator(String symbol) {
    this.symbol = symbol;
  }

  public static AmountOperator fromSymbol(String symbol) {
    for (AmountOperator op : values()) {
      if (op.symbol.equals(symbol)) {
        return op;
      }
    }
    throw new IllegalArgumentException("Unknown AmountOperator: " + symbol);
  }

}
