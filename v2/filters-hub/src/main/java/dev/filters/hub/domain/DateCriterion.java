package dev.filters.hub.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@JsonTypeName("date")
public record DateCriterion(
		UUID id,
    @NotNull DateOperator operator,
		@NotNull LocalDate value
) implements Criterion {}
