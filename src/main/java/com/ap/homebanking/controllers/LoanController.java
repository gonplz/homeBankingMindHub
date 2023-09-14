package com.ap.homebanking.controllers;

import com.ap.homebanking.dtos.LoanAplicationDTO;
import com.ap.homebanking.dtos.LoanDTO;
import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.ClientLoanRepository;
import com.ap.homebanking.service.AccountService;
import com.ap.homebanking.service.ClientService;
import com.ap.homebanking.service.LoanService;
import com.ap.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private LoanService loanService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ClientLoanRepository clientLoanRepository;


    @RequestMapping(value = "/loans", method = RequestMethod.GET)
    public List<LoanDTO> getLoans(){
        return loanService.getLoans();}

    @Transactional
    @RequestMapping (value = "/loans",method = RequestMethod.POST)
    ResponseEntity<Object> createdLoans (@RequestBody LoanAplicationDTO loanApplicationDTO,
                                         Authentication authentication){

        Long loanId = loanApplicationDTO.getLoanId();
        Double amount = loanApplicationDTO.getAmount();
        Integer payments = loanApplicationDTO.getPayments();
        String toAccountNumber = loanApplicationDTO.getToAccountNumber();

        Client clientAuth = clientService.getCurrentClient(authentication.getName());
        Account accountDestination = accountService.findByNumber(toAccountNumber);
        Loan loan = loanService.getLoanById(loanApplicationDTO.getLoanId());

        //Verificar que los datos sean correctos, es decir no estén vacíos, que el monto no sea 0 o que las cuotas no sean 0.
        if (loanId == null) {
            return new ResponseEntity<>("Missing data,loan is required", HttpStatus.FORBIDDEN);
        }
        if (amount <= 0){
            return new ResponseEntity<>("Amount can not be null ", HttpStatus.FORBIDDEN);
        }
        if (payments <=0){
            return new ResponseEntity<>("Payments can not be null",HttpStatus.FORBIDDEN);
        }
        if (toAccountNumber.isBlank()){
            return new ResponseEntity<>("Missing Data, destination account is required",HttpStatus.FORBIDDEN);
        }

        //Verificar que el préstamo exista
        if (!loanService.existsById(loanId)) {
            return new ResponseEntity<>("This Loan don't exists",HttpStatus.FORBIDDEN);
        }
        //Verificar que el monto solicitado no exceda el monto máximo del préstamo
        if(amount > loanService.getLoanById(loanId).getMaxAmount()){
            return new ResponseEntity<>("This amount is greater than what is allowed ",HttpStatus.FORBIDDEN);
        }
        //Verifica que la cantidad de cuotas se encuentre entre las disponibles del préstamo
        if(loan.getPayments().equals(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("This numbers of payments is not available",HttpStatus.FORBIDDEN);
        }

        //Verificar que la cuenta de destino exista
        if(!accountService.existByNumber(toAccountNumber)){
            return new ResponseEntity<>("This destination account don't exists", HttpStatus.FORBIDDEN);
        }
        //Verificar que la cuenta de destino pertenezca al cliente autenticado
        if(!clientAuth.getAccounts().contains(accountDestination)){
            System.out.println(clientAuth.getAccounts());
            return new ResponseEntity<>("This Account don't belong to authentication client ",HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount()+ amount*0.2, loanApplicationDTO.getPayments());
        Transaction transactionLoan = new Transaction(amount,TransactionType.CREDIT,"Loan Approved",LocalDateTime.now());
        accountDestination.addTransaction(transactionLoan);

        loan.addClientLoan(clientLoan);
        clientAuth.addClientLoan(clientLoan);

        clientLoanRepository.save(clientLoan);
        transactionService.createdTransaction(transactionLoan);
        accountService.createdAccount(accountDestination);

        return new ResponseEntity<>("Loan approved",HttpStatus.CREATED);
    }
}