package com.spring.hasdocTime.entity;

import com.spring.hasdocTime.utills.Role;
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
