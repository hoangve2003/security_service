package com.example.securityserviceapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name isn't null")
    private String name;
    @NotBlank(message = "Email isn't null")
    private String email;
    @NotBlank(message = "Password isn't null")
    private String password;
    @NotBlank(message = "Role isn't null")
    private String roles;
}
