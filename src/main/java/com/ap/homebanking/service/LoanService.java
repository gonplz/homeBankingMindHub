package com.ap.homebanking.service;

import com.ap.homebanking.dtos.LoanDTO;
import com.ap.homebanking.models.Loan;

import java.util.List;

public interface LoanService {

    List<LoanDTO> getLoans();

    Loan getLoanById (Long id);

    void createdLoans (Loan loan);

    boolean existsById(Long loanId);
}
