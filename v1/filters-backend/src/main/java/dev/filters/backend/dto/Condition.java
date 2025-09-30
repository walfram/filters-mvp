package dev.filters.backend.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = AmountConditionDTO.class, name = "amount"),
    @JsonSubTypes.Type(value = DateConditionDTO.class, name = "date"),
    @JsonSubTypes.Type(value = TitleConditionDTO.class, name = "title")
})
public interface Condition {
}
