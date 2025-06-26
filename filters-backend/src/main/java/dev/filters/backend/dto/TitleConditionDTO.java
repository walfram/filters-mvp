package dev.filters.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TitleConditionDTO extends FilterConditionDTO {

  @NotNull(message = "Title operator is required")
  private TitleOperator operator;

  @NotBlank(message = "Title value is required")
  private String value;

  public TitleConditionDTO() {
    super("title");
  }
}
