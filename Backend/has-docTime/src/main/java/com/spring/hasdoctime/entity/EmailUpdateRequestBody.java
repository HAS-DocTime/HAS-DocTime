package com.spring.hasdoctime.entity;

import com.spring.hasdoctime.utills.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class EmailUpdateRequestBody {

    private int id;
    private String email;
    private Role role;

}
