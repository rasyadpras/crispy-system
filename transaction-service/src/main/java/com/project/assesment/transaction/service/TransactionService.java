package com.project.assesment.transaction.service;

import com.project.assesment.transaction.entity.Transaction;
import com.project.assesment.transaction.entity.TransactionDetail;
import com.project.assesment.transaction.model.TransactionDetailResponse;
import com.project.assesment.transaction.model.TransactionRequest;
import com.project.assesment.transaction.model.TransactionResponse;
import com.project.assesment.transaction.repository.TransactionDetailRepository;
import com.project.assesment.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionDetailRepository transactionDetailRepository;

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
        transactionRepository.create(transaction);

        transactionRequest.getTransactionDetails().stream()
                .map(detailReq -> {
                    TransactionDetail transactionDetail = new TransactionDetail();
                    transactionDetail.setTransactionId(transaction.getId());
                    transactionDetail.setProductId(detailReq.getProductId());
                    transactionDetail.setQuantity(detailReq.getQuantity());
                    return transactionDetail;
                }).forEach(transactionDetailRepository::create);
    }

    public TransactionResponse findById(String id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found")
        );
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setCustomerId(transaction.getCustomerId());
        response.setTransactionDate(transaction.getTransactionDate());
        response.setTotalPrice(transaction.getTotalPrice());

        List<TransactionDetail> transactionDetails = transactionDetailRepository.findByTransactionId(transaction.getId());
        List<TransactionDetailResponse> detailResponses = transactionDetails.stream().map(detail -> {
            TransactionDetailResponse detailResponse = new TransactionDetailResponse();
            detailResponse.setId(detail.getId());
            detailResponse.setProductId(detail.getProductId());
            detailResponse.setQuantity(detail.getQuantity());
            return detailResponse;
        }).toList();

        response.setTransactionDetails(detailResponses);
        return response;
    }
}
