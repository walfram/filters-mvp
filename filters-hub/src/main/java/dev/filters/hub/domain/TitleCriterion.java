package dev.filters.hub.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JsonTypeName("title")
public record TitleCriterion(
		@NotNull UUID id,
    @NotNull TitleOperator operator,
    @NotBlank String value,
		Boolean caseSensitive
) implements Criterion {}
