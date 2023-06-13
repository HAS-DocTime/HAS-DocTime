package com.spring.hasdocTime.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class EmailUpdateRequestBody {

    private int id;
    private String email;

}
