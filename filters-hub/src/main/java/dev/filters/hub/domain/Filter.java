package dev.filters.hub.domain;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public record Filter(
    UUID id,
    String name,
    boolean active,
    List<@Valid Condition> conditions
) {}
