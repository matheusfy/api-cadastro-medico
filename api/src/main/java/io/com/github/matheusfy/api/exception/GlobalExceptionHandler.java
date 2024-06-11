package io.com.github.matheusfy.api.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleMissMatchArgumentType(MethodArgumentTypeMismatchException ex, WebRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("error", "MissMatch argument type");
        errorResponse.put("message", ex.getLocalizedMessage());
        errorResponse.put("path", request.getDescription(false).replace("uri=", ""));

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity handleSQLException(SQLIntegrityConstraintViolationException ex, WebRequest request) {

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.CONFLICT.value());
        errorResponse.put("error", "Duplicate entry");
        errorResponse.put("message", "An entry with the same key already exists: " + ex.getLocalizedMessage());
        errorResponse.put("path", request.getDescription(false).replace("uri=", ""));

        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleInvalidArgument(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getFieldErrors();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errors.stream().map(ErrorMsg::new).toList());
    }

    public record ErrorMsg(String campo, String msg) {
        public ErrorMsg(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
