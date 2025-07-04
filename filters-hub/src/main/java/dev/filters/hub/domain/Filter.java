package dev.filters.hub.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record Filter(
    UUID id,
    @NotBlank String name,
    @NotNull Boolean active,
    @NotEmpty List<@Valid Condition> conditions
) {}
