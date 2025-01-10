package com.example.demo.service.definition;

import com.example.demo.dto.user.BaseUserRequestDTO;
import com.example.demo.dto.user.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO findById(long id) throws Exception;
    UserDTO findByUsername(String username) throws Exception;
    List<UserDTO> findAll() throws Exception;
    void insertBaseUser(BaseUserRequestDTO dto) throws Exception;
    void deleteUserById(long id) throws Exception;
}
