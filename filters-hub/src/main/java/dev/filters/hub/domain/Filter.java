package dev.filters.hub.domain;

import jakarta.validation.Valid;

import java.util.List;

public record Filter(
    String name,
    boolean active,
    List<@Valid Condition> conditions
) {}
