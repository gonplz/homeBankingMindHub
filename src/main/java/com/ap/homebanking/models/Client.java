package com.ap.homebanking.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Client {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String mail;

    public Client(){}

     public Client(String firstName, String lastName, String mail){
        this.getId();
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setMail(mail);
     }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}


