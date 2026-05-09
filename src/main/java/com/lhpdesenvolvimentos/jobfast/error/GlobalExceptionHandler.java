package com.lhpdesenvolvimentos.jobfast.error;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.lhpdesenvolvimentos.jobfast.job.application.dto.ErrorResponse;
import com.lhpdesenvolvimentos.jobfast.job.domain.exception.ApiUnavailableException;
import com.lhpdesenvolvimentos.jobfast.job.domain.exception.DomainException;
import com.lhpdesenvolvimentos.jobfast.job.domain.exception.NoJobException;
import com.lhpdesenvolvimentos.jobfast.profile.domain.error.AboutException;
import com.lhpdesenvolvimentos.jobfast.profile.domain.error.AcademicExperienceException;
import com.lhpdesenvolvimentos.jobfast.user.domain.error.UserVerificationException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApiUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleApiUnavailable(ApiUnavailableException ex, HttpServletRequest req) {
        log.error("External API unavailable: {}", ex.getMessage(), ex);
        ErrorResponse body = new ErrorResponse(Instant.now(),
                HttpStatus.BAD_GATEWAY.value(),
                HttpStatus.BAD_GATEWAY.getReasonPhrase(),
                ex.getMessage(),
                req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(body);
    }

    @ExceptionHandler(NoJobException.class)
    public ResponseEntity<ErrorResponse> handleNoJob(NoJobException ex, HttpServletRequest req) {
        log.info("No jobs found: {}", ex.getMessage());
        ErrorResponse body = new ErrorResponse(Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                req.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // Fallback for other domain errors
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomain(DomainException ex, HttpServletRequest req) {
        log.warn("Domain error: {}", ex.getMessage(), ex);
        ErrorResponse body = new ErrorResponse(Instant.now(),
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                HttpStatus.UNPROCESSABLE_CONTENT.getReasonPhrase(),
                ex.getMessage(),
                req.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(body);
    }

    @ExceptionHandler(AboutException.class)
    public ResponseEntity<ErrorResponse> handleAbout(AboutException ex, HttpServletRequest req) {
        log.warn("About error: {}", ex.getMessage(), ex);
        ErrorResponse body = new ErrorResponse(Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(AcademicExperienceException.class)
    public ResponseEntity<ErrorResponse> handleAcademic(AcademicExperienceException ex, HttpServletRequest req) {
        log.warn("Academic Experience error: {}", ex.getMessage(), ex);
        ErrorResponse body = new ErrorResponse(Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpServletRequest req) {
        String message = "Malformed JSON request";
        // busca InvalidFormatException
        Throwable cause = ex.getCause();
        InvalidFormatException ife = findCause(ex, InvalidFormatException.class);
        AcademicExperienceException aee = findCause(ex, AcademicExperienceException.class);

        if (aee != null) {
            message = aee.getMessage();
        } else if (ife != null) {
            String field = ife.getPath().stream()
                    .map(p -> p.getFieldName())
                    .filter(n -> n != null)
                    .collect(Collectors.joining("."));
            String value = String.valueOf(ife.getValue());
            String target = ife.getTargetType() != null ? ife.getTargetType().getSimpleName() : "target";
            message = String.format("Invalid value '%s' for field '%s'. Expected type %s", value,
                    field.isEmpty() ? "<unknown>" : field, target);
        } else if (cause != null && cause.getMessage() != null) {
            message = cause.getMessage();
        } else if (ex.getMessage() != null) {
            message = ex.getMessage();
        }

        log.warn("Malformed request: {}", message, ex);
        ErrorResponse body = new ErrorResponse(Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message,
                req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex,
            HttpServletRequest req) {
        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> Map.of("field", fe.getField(), "message", fe.getDefaultMessage()))
                .collect(Collectors.toList());

        Map<String, Object> body = Map.of(
                "timestamp", Instant.now(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "message", "Validation failed",
                "path", req.getRequestURI(),
                "errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(UserVerificationException.class)
    public ResponseEntity<ErrorResponse> handleUserVerification(UserVerificationException ex, HttpServletRequest req) {
        log.error("User verification error: {}", ex.getMessage(), ex);
        ErrorResponse body = new ErrorResponse(Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // Catch-all for unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAny(Exception ex, HttpServletRequest req) {
        log.error("Unhandled error: {}", ex.getMessage(), ex);
        ErrorResponse body = new ErrorResponse(Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "An unexpected error occurred",
                req.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @SuppressWarnings("unchecked")
    private <T extends Throwable> T findCause(Throwable ex, Class<T> cls) {
        Throwable current = ex;
        while (current != null) {
            if (cls.isInstance(current)) {
                return (T) current;
            }
            current = current.getCause();
        }
        return null;
    }
}