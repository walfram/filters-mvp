package dev.filters.backend.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
    visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = AmountConditionDTO.class, name = "amount"),
    @JsonSubTypes.Type(value = DateConditionDTO.class, name = "date"),
    @JsonSubTypes.Type(value = TitleConditionDTO.class, name = "title")
})
public abstract class FilterConditionDTO {
  @NotNull(message = "Condition type cannot be null")
  private String type;
}
