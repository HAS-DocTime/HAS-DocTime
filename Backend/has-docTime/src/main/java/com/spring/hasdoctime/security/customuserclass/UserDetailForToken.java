/**
 * Represents the user details included in the JWT token.
 */
package com.spring.hasdoctime.security.customuserclass;

import com.spring.hasdoctime.utills.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailForToken {

    /**
     * The email of the user.
     */
    private String email;

    /**
     * The ID of the user.
     */
    private Integer id;


    /**
     * The role of the user.
     */
    private Role role;

}
