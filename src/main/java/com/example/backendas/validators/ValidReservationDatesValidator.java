package com.example.backendas.validators;

import com.example.backendas.dtos.reservation.ReservationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidReservationDatesValidator implements ConstraintValidator<ValidReservationDates, ReservationRequest> {

    @Override
    public void initialize(ValidReservationDates constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ReservationRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }

        // Si alguna de las fechas es null, dejar que @NotNull lo maneje
        if (request.getStartDateTime() == null || request.getEndDateTime() == null) {
            return true;
        }

        // Validar que endDateTime no sea anterior a startDateTime
        // endDateTime debe ser >= startDateTime
        if (request.getEndDateTime().isBefore(request.getStartDateTime())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("La fecha y hora de fin no puede ser anterior a la fecha y hora de inicio")
                    .addPropertyNode("endDateTime")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
