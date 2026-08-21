package de.schule.informatik.lernplattform.app.web;

import de.schule.informatik.lernplattform.domain.displayname.DisplayNameConflictException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(DisplayNameConflictException.class)
    ProblemDetail handleDisplayNameConflict(DisplayNameConflictException ex) {
        return problem(HttpStatus.CONFLICT, "display-name-conflict", ex.getMessage());
    }

    @ExceptionHandler(SecurityException.class)
    ProblemDetail handleSecurity(SecurityException ex) {
        return problem(HttpStatus.FORBIDDEN, "forbidden", ex.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class, ConstraintViolationException.class})
    ProblemDetail handleBadRequest(RuntimeException ex) {
        return problem(HttpStatus.BAD_REQUEST, "invalid-request", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        String detail = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Ungültige Eingabe.");
        return problem(HttpStatus.BAD_REQUEST, "validation-error", detail);
    }

    private static ProblemDetail problem(HttpStatus status, String type, String detail) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail == null ? status.getReasonPhrase() : detail);
        problem.setType(URI.create("urn:lernplattform:error:" + type));
        return problem;
    }
}
