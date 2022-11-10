package com.example.obProyectoSpringBoot.playload;

public class LoginEntity {
    private String email;
    private String password;

    public LoginEntity() {
    }

    public String getEmail() {
        return email;
    }

    public LoginEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginEntity setPassword(String password) {
        this.password = password;
        return this;
    }
}
