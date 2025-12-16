package com.example.backendas.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidPaymentDatesValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPaymentDates {
    String message() default "La fecha de pago no puede ser anterior a la fecha de vencimiento";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
