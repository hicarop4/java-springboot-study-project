package com.example.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.exceptions.ExceptionResponse;
import com.example.exceptions.RequiredObjectIsNullException;
import com.example.exceptions.ResourceNotFoundException;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends
                ResponseEntityExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public final ResponseEntity<ExceptionResponse> handleNotFoundException(
                        Exception ex,
                        WebRequest request) {
                ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                                ex.getMessage(),
                                request.getDescription(false));
                return new ResponseEntity<>(exceptionResponse,
                                HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(RequiredObjectIsNullException.class)
        public final ResponseEntity<ExceptionResponse> handleBadRequestException(
                        Exception ex,
                        WebRequest request) {
                ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                                ex.getMessage(),
                                request.getDescription(false));
                return new ResponseEntity<>(exceptionResponse,
                                HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler
        public final ResponseEntity<ExceptionResponse> handleAllExcpetions(
                        Exception ex,
                        WebRequest request) {
                ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                                ex.getMessage(),
                                request.getDescription(false));
                return new ResponseEntity<>(exceptionResponse,
                                HttpStatus.INTERNAL_SERVER_ERROR);
        }

}
