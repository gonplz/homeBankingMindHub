package com.ap.homebanking.service;

import com.ap.homebanking.dtos.AccountDTO;
import com.ap.homebanking.models.Account;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountService {

    List<AccountDTO> getAccounts();

    Account getAccountById (Long id);

    Account findByNumber (String number);

    boolean existByNumber (String number);

    List<AccountDTO> getCurrentAccount (Authentication authentication);

    void createdAccount (Account account);

}
