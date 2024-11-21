package com.project.assesment.transaction.controller;

import com.project.assesment.transaction.model.Response;
import com.project.assesment.transaction.model.TransactionRequest;
import com.project.assesment.transaction.model.TransactionResponse;
import com.project.assesment.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<com.project.assesment.transaction.model.Response<String>> createProduct(@RequestBody TransactionRequest request) {
        transactionService.createTransaction(request);
        Response<String> response = new Response<>();
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage(HttpStatus.CREATED.getReasonPhrase());
        response.setData("Product created");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Response<TransactionResponse>> findById(@PathVariable String id) {
        TransactionResponse transactionResponse = transactionService.findById(id);
        Response<TransactionResponse> response = new Response<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setData(transactionResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
