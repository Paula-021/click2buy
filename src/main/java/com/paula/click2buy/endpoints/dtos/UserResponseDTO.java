package com.paula.click2buy.endpoints.dtos;

import com.paula.click2buy.domain.User;

public class UserResponseDTO {
    private String name;
    private String email;
    private String telephone;

    public UserResponseDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.telephone = user.getTelephone();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
