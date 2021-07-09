package com.barbershop.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

    private int id;
    private String name;
    private String email;
    private String password;
    private boolean active;

}
