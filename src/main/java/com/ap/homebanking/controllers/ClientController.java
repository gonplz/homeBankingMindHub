package com.ap.homebanking.controllers;

import com.ap.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientController {

    @Autowired
    public AccountRepository accountRepository;

    public class List<Account> getAll(){
        return findAll;
    }

  //  private void addAtrribute() {
}

//    public String greeting(@RequestParam("name") String name, Model model) {
//        model.addAttribute("name", name);
//        return "greeting";