package dev.filters.hub.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

@JsonTypeName("date")
public record DateCriterion(
		UUID id,
    @NotNull DateOperator operator,
    @NotBlank @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}") String value
) implements Criterion {}
