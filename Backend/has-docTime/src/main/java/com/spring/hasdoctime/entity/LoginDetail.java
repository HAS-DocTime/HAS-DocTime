package com.spring.hasdoctime.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * The LoginDetail class represents the login details of a user.
 * It contains information about the user's email and password.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginDetail {

    private String email;

    private String password;




}