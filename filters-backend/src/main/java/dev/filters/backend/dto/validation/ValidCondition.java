package dev.filters.backend.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConditionValidator.class)
public @interface ValidCondition {
  String message() default "Invalid condition";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
