package com.project.hotel_management.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiError> handleNotFound(
      ResourceNotFoundException ex, HttpServletRequest request) {

    return build(HttpStatus.NOT_FOUND, ex.getMessage(), request);
  }

  @ExceptionHandler({
    HttpMessageNotReadableException.class,
    MethodArgumentTypeMismatchException.class
  })
  public ResponseEntity<ApiError> handleBadRequest(Exception ex, HttpServletRequest request) {

    return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidationError(
      MethodArgumentNotValidException ex, HttpServletRequest request) {

    String message =
        ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .findFirst()
            .orElse("Validation failed");

    return build(HttpStatus.BAD_REQUEST, message, request);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleInternalError(Exception ex, HttpServletRequest request) {

    log.error("Unhandled exception occurred", ex);

    return build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred", request);
  }

  private ResponseEntity<ApiError> build(
      HttpStatus status, String message, HttpServletRequest request) {

    ApiError error =
        new ApiError(status.value(), message, LocalDateTime.now(), request.getRequestURI());

    return new ResponseEntity<>(error, status);
  }
}
