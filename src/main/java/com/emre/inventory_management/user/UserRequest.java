package com.emre.inventory_management.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
}
