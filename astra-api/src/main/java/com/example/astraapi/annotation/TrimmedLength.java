package com.example.astraapi.annotation;

import com.example.astraapi.config.TrimmedLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = TrimmedLengthValidator.class)
public @interface TrimmedLength {
  String message() default "Invalid value";

  long min() default 0;

  long max() default Long.MAX_VALUE;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
