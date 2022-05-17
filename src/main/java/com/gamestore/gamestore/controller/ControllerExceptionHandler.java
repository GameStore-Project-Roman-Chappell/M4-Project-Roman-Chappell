package com.gamestore.gamestore.controller;

import com.gamestore.gamestore.model.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {



    @ExceptionHandler(value=Exception.class)
    public ResponseEntity<CustomErrorResponse> handleException(Exception e) {
        CustomErrorResponse error = new CustomErrorResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        ResponseEntity<CustomErrorResponse> responseEntity = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        return responseEntity;
    }

}
