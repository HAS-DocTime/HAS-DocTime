package com.spring.hasdocTime.utills;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public enum BloodGroup {
    A_POSITIVE("A+"),
    A_NEGATIVE("A-"),
    B_POSITIVE("B+"),
    B_NEGATIVE("B-"),
    AB_POSITIVE("AB+"),
    AB_NEGATIVE("AB-"),
    O_POSITIVE("O+"),
    O_NEGATIVE("O-");

    private final String value;

    @NotEmpty
    @Pattern(regexp="[A-Z_+-]+", message = "Enter Blood Group in valid Format")
    private BloodGroup(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
