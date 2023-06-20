package com.spring.hasdoctime.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class PasswordUpdateRequestBody {
    private int id;
    private String oldPassword;
    private String newPassword;
}
