package dev.filters.backend.dto.validation;

import dev.filters.backend.dto.AmountConditionDTO;
import dev.filters.backend.dto.Condition;
import dev.filters.backend.dto.DateConditionDTO;
import dev.filters.backend.dto.TitleConditionDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConditionValidator implements ConstraintValidator<ValidCondition, List<Condition>> {

  @Override
  public boolean isValid(List<Condition> conditions, ConstraintValidatorContext context) {
    if (conditions == null || conditions.isEmpty()) {
      return true; // Let @NotEmpty handle this
    }

    boolean allValid = true;

    for (int i = 0; i < conditions.size(); i++) {
      Condition condition = conditions.get(i);
      if (!validateSingleCondition(condition, context, i)) {
        allValid = false;
      }
    }

    return allValid;
  }

  private boolean validateSingleCondition(Condition condition, ConstraintValidatorContext context, int index) {
    if (condition == null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("Condition at index " + index + " is null")
          .addConstraintViolation();
      return false;
    }

    if (condition instanceof AmountConditionDTO amountCondition) {
      return validateAmountCondition(amountCondition, context, index);
    } else if (condition instanceof DateConditionDTO dateCondition) {
      return validateDateCondition(dateCondition, context, index);
    } else if (condition instanceof TitleConditionDTO titleCondition) {
      return validateTitleCondition(titleCondition, context, index);
    }

    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate("Unknown condition type at index " + index)
        .addConstraintViolation();
    return false;
  }

  private boolean validateAmountCondition(AmountConditionDTO condition, ConstraintValidatorContext context, int index) {
    boolean isValid = true;

    if (condition.getOperator() == null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("Amount condition operator is required at index %s".formatted(index))
          .addConstraintViolation();
      isValid = false;
    }

    if (condition.getValue() == null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("Amount condition value is required at index %s".formatted(index))
          .addConstraintViolation();
      isValid = false;
    }

    return isValid;
  }

  private boolean validateDateCondition(DateConditionDTO condition, ConstraintValidatorContext context, int index) {
    boolean isValid = true;

    if (condition.getOperator() == null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("Date condition operator is required at index %s".formatted(index))
          .addConstraintViolation();
      isValid = false;
    }

    if (condition.getValue() == null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("Date condition value is required at index %s".formatted(index))
          .addConstraintViolation();
      isValid = false;
    }

    return isValid;
  }

  private boolean validateTitleCondition(TitleConditionDTO condition, ConstraintValidatorContext context, int index) {
    boolean isValid = true;

    if (condition.getOperator() == null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("Title condition operator is required at index %s".formatted(index))
          .addConstraintViolation();
      isValid = false;
    }

    if (condition.getValue() == null || condition.getValue().trim().isEmpty()) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("Title condition value is required at index %s".formatted(index))
          .addConstraintViolation();
      isValid = false;
    }

    return isValid;
  }
}
