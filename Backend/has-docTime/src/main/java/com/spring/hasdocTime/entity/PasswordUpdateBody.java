package com.spring.hasdocTime.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateBody {

    private int id;
    private String newPassword;
    private String confirmPassword;

}
