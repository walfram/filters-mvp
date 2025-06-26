package dev.filters.backend.dto;

public enum TitleOperator {
  STARTS_WITH("startsWith"), ENDS_WITH("endsWith"), CONTAINS("contains"), EQUALS("equals");

  private final String name;

  TitleOperator(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
