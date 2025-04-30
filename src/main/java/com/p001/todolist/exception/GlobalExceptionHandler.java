package com.p001.todolist.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

 // Handle Data Integrity violations (e.g., unique constraint violations)
 @ExceptionHandler(DataIntegrityViolationException.class)
 public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
  if (ex.getRootCause() != null && ex.getRootCause().getMessage().contains("Duplicate entry")) {
   return ResponseEntity.status(HttpStatus.BAD_REQUEST)
     .body("Error: The username already exists. Please choose a different username.");
  }
  // Handle other data integrity issues if needed
  return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    .body("Database error: " + ex.getMessage());
 }

 // Handle any other general exceptions
 @ExceptionHandler(Exception.class)
 public ResponseEntity<String> handleGenericException(Exception ex) {
  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    .body("An error occurred: " + ex.getMessage());
 }
}
