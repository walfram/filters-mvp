package dev.filters.backend.dto.operator;

import lombok.Getter;

@Getter
public enum TitleOperator {
  STARTS_WITH("startsWith"), ENDS_WITH("endsWith"), CONTAINS("contains"), EQUALS("equals");

  private final String name;

  TitleOperator(String name) {
    this.name = name;
  }

}
