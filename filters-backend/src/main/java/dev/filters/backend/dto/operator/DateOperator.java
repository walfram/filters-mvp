package dev.filters.backend.dto.operator;

import lombok.Getter;

@Getter
public enum DateOperator {
  BEFORE("before"), AFTER("after"), ON("on");

  private final String name;

  DateOperator(String name) {
    this.name = name;
  }

}
