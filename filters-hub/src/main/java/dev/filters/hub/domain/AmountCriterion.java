package dev.filters.hub.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JsonTypeName("amount")
public record AmountCriterion(
		UUID id,
    @NotNull AmountOperator operator,
    @NotNull Double value
) implements Criterion {}
