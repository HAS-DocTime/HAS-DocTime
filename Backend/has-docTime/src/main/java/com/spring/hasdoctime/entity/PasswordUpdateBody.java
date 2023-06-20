package com.spring.hasdoctime.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateBody {

    private String otp;
    private String email;
    private String password;
    private String confirmPassword;

}
