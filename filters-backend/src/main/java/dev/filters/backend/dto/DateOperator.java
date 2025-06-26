package dev.filters.backend.dto;

public enum DateOperator {
  BEFORE("before"), AFTER("after"), ON("on");

  private final String name;

  DateOperator(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
