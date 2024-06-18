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

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import io.com.github.matheusfy.api.domain.consulta.ConsultaNotFoundException;
import io.com.github.matheusfy.api.domain.paciente.NotUpdatedException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleMissMatchArgumentType(MethodArgumentTypeMismatchException ex,
            WebRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("error", "MissMatch argument type");
        errorResponse.put("message", ex.getLocalizedMessage());
        errorResponse.put("path", request.getDescription(false).replace("uri=", ""));

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleSQLException(SQLIntegrityConstraintViolationException ex,
            WebRequest request) {

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.CONFLICT.value());
        errorResponse.put("error", "Duplicate entry");
        errorResponse.put("message", "An entry with the same key already exists: " + ex.getLocalizedMessage());
        errorResponse.put("path", request.getDescription(false).replace("uri=", ""));

        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMsg>> handleInvalidArgument(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getFieldErrors();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errors.stream().map(ErrorMsg::new).toList());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(ex.getMessage());
    }

    @ExceptionHandler(MedicoNotFoundException.class)
    public ResponseEntity<String> handleMedicoNotFoundException(MedicoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(ex.getMessage());
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<String> handleJWTVerificationException(JWTVerificationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(ex.getMessage());
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<String> handleJWTCreationException(JWTCreationException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());

    }

    @ExceptionHandler(ConsultaNotFoundException.class)
    public ResponseEntity<String> handleConsultaNotFoundException(ConsultaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NotUpdatedException.class)
    public ResponseEntity<String> handleNotUpdatedException(NotUpdatedException ex) {
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(ex.getMessage());
    }

    public record ErrorMsg(String campo, String msg) {
        public ErrorMsg(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

}
