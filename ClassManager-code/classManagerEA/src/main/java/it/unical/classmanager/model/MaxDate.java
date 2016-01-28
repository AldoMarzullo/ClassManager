package it.unical.classmanager.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxDateValidator.class)
@Documented
public @interface MaxDate {

    String message() default "{constraints.maxDate}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
