package dev.filters.hub.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;

@JsonTypeName("amount")
public record AmountCondition(
    @NotNull AmountOperator operator,
    @NotNull Double value
) implements Condition {}
