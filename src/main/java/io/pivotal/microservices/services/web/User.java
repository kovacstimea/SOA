package io.pivotal.microservices.services.web;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.Column;

@JsonRootName("User")
public class User {
    protected Long id;
    protected String username;
    protected String number;
    protected String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNumber() {
        return number;
    }

    protected void setNumber(String accountNumber) {
        this.number = accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
