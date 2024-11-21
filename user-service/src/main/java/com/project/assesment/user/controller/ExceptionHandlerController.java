package com.project.assesment.user.controller;

import com.project.assesment.user.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Response<String>> handleResponseException(ResponseStatusException e) {
        Response<String> response = new Response<>();
        response.setStatus(e.getStatusCode().value());
        response.setMessage(e.getMessage());
        response.setData(e.getReason());
        return ResponseEntity.status(e.getStatusCode()).body(response);
    }
}
