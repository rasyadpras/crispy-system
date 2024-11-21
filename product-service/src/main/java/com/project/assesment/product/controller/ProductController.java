package com.project.assesment.product.controller;

import com.project.assesment.product.model.ProductRequest;
import com.project.assesment.product.model.ProductResponse;
import com.project.assesment.product.model.Response;
import com.project.assesment.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Response<String>> createProduct(@RequestBody ProductRequest request) {
        productService.createProduct(request);
        Response<String> response = new Response<>();
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage(HttpStatus.CREATED.getReasonPhrase());
        response.setData("Product created");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<ProductResponse>>> findProducts() {
        List<ProductResponse> data = productService.findAll();
        Response<List<ProductResponse>> response = new Response<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Response<ProductResponse>> findProductById(@PathVariable Integer id) {
        ProductResponse data = productService.findById(id);
        Response<ProductResponse> response = new Response<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Response<String>> updateProduct(@PathVariable Integer id, @RequestBody ProductRequest request) {
        productService.updateProduct(id, request);
        Response<String> response = new Response<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setData("Product with ID " + id + " updated");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Response<String>> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        Response<String> response = new Response<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setData("Product with ID " + id + " deleted");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
