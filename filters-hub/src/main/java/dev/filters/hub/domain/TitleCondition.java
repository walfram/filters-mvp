package dev.filters.hub.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonTypeName("title")
public record TitleCondition(
    @NotNull TitleOperator operator,
    @NotBlank String value
) implements Condition {}
