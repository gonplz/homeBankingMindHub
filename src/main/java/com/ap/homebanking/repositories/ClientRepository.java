package com.ap.homebanking.repositories;

import com.ap.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,String> {
}
