package com.ap.homebanking.repositories;

import com.ap.homebanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientLoanRepository extends JpaRepository<ClientLoan,Long> {
}
