package dev.filters.hub.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = AmountCriterion.class, name = "number"),
    @JsonSubTypes.Type(value = TitleCriterion.class, name = "string"),
    @JsonSubTypes.Type(value = DateCriterion.class, name = "date")
})
public interface Criterion {
}
