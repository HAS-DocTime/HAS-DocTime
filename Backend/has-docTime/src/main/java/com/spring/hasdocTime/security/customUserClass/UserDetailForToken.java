package com.spring.hasdocTime.security.customUserClass;


import com.spring.hasdocTime.utills.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class UserDetailForToken {

    private String email;
    private Role role;
}
