package com.spring.hasdoctime.utills;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

/**
 * Enum class representing different blood groups.
 */
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

    /**
     * Constructor for BloodGroup enum.
     *
     * @param value The value representing the blood group.
     */
    @NotEmpty
    @Pattern(regexp="[A-Z_+-]+", message = "Enter Blood Group in valid Format")
    private BloodGroup(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value representing the blood group.
     *
     * @return The blood group value.
     */
    public String getValue() {
        return value;
    }
}
