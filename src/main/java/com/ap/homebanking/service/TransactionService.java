package com.ap.homebanking.service;


import com.ap.homebanking.dtos.TransactionDTO;
import com.ap.homebanking.models.Transaction;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> getTransactions();

    Transaction getTransactionById (Long id);

    public void createdTransaction (Transaction transaction);
}
