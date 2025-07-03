package dev.filters.hub.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = AmountCondition.class, name = "amount"),
    @JsonSubTypes.Type(value = TitleCondition.class, name = "title"),
    @JsonSubTypes.Type(value = DateCondition.class, name = "date")
})
public interface Condition {
}
