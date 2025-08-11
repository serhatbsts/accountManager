package com.Project.accountManager.exception;

import com.Project.accountManager.exception.errorResponse.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleInsufficientFundsException(InsufficientFundsException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Insufficient funds", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleCustomNotFoundException(CustomNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Account not found", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("Internal server error", "An unexpected error occurred.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
