package dev.filters.backend.dto;

public enum AmountOperator {
  GT(">"), GTE(">="), EQ("="), LTE("<="), LT("<");

  private final String symbol;

  AmountOperator(String symbol) {
    this.symbol = symbol;
  }

  public String getSymbol() {
    return symbol;
  }
}
