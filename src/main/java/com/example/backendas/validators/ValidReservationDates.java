package com.example.backendas.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidReservationDatesValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidReservationDates {
    String message() default "La fecha y hora de fin no puede ser anterior a la fecha y hora de inicio";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
