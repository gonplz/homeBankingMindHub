package com.ap.homebanking.service.implement;

import com.ap.homebanking.dtos.LoanDTO;
import com.ap.homebanking.models.Loan;
import com.ap.homebanking.repositories.LoanRepository;
import com.ap.homebanking.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanImplement implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public List<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());}

    @Override
    public Loan getLoanById(Long id) {return loanRepository.findById(id).orElse(null);}

    @Override
    public void createdLoans(Loan loan) {loanRepository.save(loan);}

    @Override
    public boolean existsById(Long loanId) {return loanRepository.existsById(loanId);}
}
