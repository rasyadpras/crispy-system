package com.project.assesment.transaction.service;

import com.project.assesment.transaction.entity.Transaction;
import com.project.assesment.transaction.entity.TransactionDetail;
import com.project.assesment.transaction.model.*;
import com.project.assesment.transaction.repository.TransactionDetailRepository;
import com.project.assesment.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private final RestTemplate restTemplate;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private LocalDate convertToLocalDate(String date) {
        try {
            return LocalDate.parse(date, FORMATTER);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Convert failed. Invalid date format (yyyy-MM-dd)");
        }
    }

    public void createTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(convertToLocalDate(transactionRequest.getTransactionDate()));
        transaction.setCustomerId(transactionRequest.getCustomerId());
        Integer transactionId = transactionRepository.create(transaction);

        transactionRequest.getTransactionDetails().stream()
                .map(detailReq -> {
                    TransactionDetail transactionDetail = new TransactionDetail();
                    transactionDetail.setTransactionId(transactionId);
                    transactionDetail.setProductId(detailReq.getProductId());
                    transactionDetail.setQuantity(detailReq.getQuantity());
                    return transactionDetail;
                }).forEach(transactionDetailRepository::create);
    }

    private String getCustomerName(Integer customerId) {
        String url = "http://localhost:8081/api/v1/customers/" + customerId;
        try {
            ResponseEntity<UserResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, UserResponse.class
            );

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
            }
            return response.getBody() != null ? response.getBody().getName() : null;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed: " + e.getMessage());
        }
    }

    private ProductResponse getProductData(Integer productId) {
        String url = "http://localhost:8083/api/v1/products/" + productId;
        try {
            ResponseEntity<ProductResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, ProductResponse.class
            );

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
            }
            return response.getBody();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed: " + e.getMessage());
        }
    }

    public TransactionResponse findById(Integer id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found")
        );

        List<TransactionDetail> transactionDetails = transactionDetailRepository.findByTransactionId(transaction.getId());
        List<TransactionDetailResponse> detailResponses = transactionDetails.stream().map(detail -> {
            ProductResponse product = getProductData(detail.getProductId());
            TransactionDetailResponse detailResponse = new TransactionDetailResponse();
            detailResponse.setId(detail.getId());
            detailResponse.setProduct(product.getName());
            detailResponse.setPrice(product.getPrice());
            detailResponse.setQuantity(detail.getQuantity());
            return detailResponse;
        }).toList();

        long totalPrice = detailResponses.stream()
                .mapToLong(detail -> detail.getPrice() * detail.getQuantity()).sum();

        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setCustomer(getCustomerName(transaction.getCustomerId()));
        response.setTransactionDate(transaction.getTransactionDate());
        response.setTotalPrice(totalPrice);
        response.setTransactionDetails(detailResponses);
        return response;
    }
}
