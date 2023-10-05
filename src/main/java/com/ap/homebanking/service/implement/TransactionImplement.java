package com.ap.homebanking.service.implement;

import com.ap.homebanking.dtos.TransactionDTO;
import com.ap.homebanking.models.Transaction;
import com.ap.homebanking.repositories.TransactionRepository;
import com.ap.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionImplement implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<TransactionDTO> getTransactions() {
        return transactionRepository.findAll().stream().map(TransactionDTO::new).collect(Collectors.toList());}

    @Override
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public void createdTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
