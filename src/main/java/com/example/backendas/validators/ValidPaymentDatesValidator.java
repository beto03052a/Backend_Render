package com.example.backendas.validators;

import com.example.backendas.dtos.payment.PaymentRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPaymentDatesValidator implements ConstraintValidator<ValidPaymentDates, PaymentRequest> {

    @Override
    public void initialize(ValidPaymentDates constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PaymentRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }

        // Si alguna de las fechas es null, dejar que @NotNull lo maneje
        if (request.getPaymentDate() == null || request.getDueDate() == null) {
            return true;
        }

        // Validar que paymentDate no sea anterior a dueDate
        // paymentDate debe ser >= dueDate
        if (request.getPaymentDate().isBefore(request.getDueDate())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("La fecha de pago no puede ser anterior a la fecha de vencimiento")
                    .addPropertyNode("paymentDate")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
