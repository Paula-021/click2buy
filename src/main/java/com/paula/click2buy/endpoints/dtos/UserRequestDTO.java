package com.paula.click2buy.endpoints.dtos;

import com.paula.click2buy.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class UserRequestDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;
    @Size(min=11,max=15, message = "Telephone must be between 11 and 15 characters")
    private String telephone;

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,30}$",
            message = "The password must be at least 8 characters long and include an uppercase letter, a lowercase letter, a number, and a special character"
    )
    private String password;
    //123.456.789-00
    @Pattern(
            regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$",
            message = "CPF must be in the format XXX.XXX.XXX-XX"
    )
//     @Pattern(
//            regexp = "^\\d{11}$",
//            message = "CPF must contain 11 numeric digits"
//    )
    private String cpf;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public User toEntity() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setTelephone(this.telephone);
        user.setPassword(this.password);
        user.setCpf(this.cpf);

        return user;
    }
}
