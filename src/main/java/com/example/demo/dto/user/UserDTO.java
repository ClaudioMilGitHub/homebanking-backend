package com.example.demo.dto.user;

import com.example.demo.entity.Role;
import lombok.Data;

@Data
public class UserDTO {

    private long id;

    private String username;
    private Role role;
}
