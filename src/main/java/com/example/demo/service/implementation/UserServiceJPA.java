package com.example.demo.service.implementation;

import com.example.demo.dto.user.BaseUserRequestDTO;
import com.example.demo.dto.user.LoginDTO;
import com.example.demo.dto.user.UserDTO;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.definition.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceJPA implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserDTO findById(long id) throws Exception {
        return userMapper.toUserDTO(userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("user not found with id: " + id)));
    }

    @Override
    public UserDTO findByUsername(String username) throws Exception {
        return userMapper.toUserDTO(userRepository.findByUsername(username)
                .orElseThrow(()-> new EntityNotFoundException("user not found with username: " + username)));
    }

    @Override
    public List<UserDTO> findAll() throws Exception {
        return userRepository.findAll().stream().map(t->userMapper.toUserDTO(t)).toList();
    }

    @Override
    public void insertBaseUser(BaseUserRequestDTO dto) throws Exception {
        User newUser = userMapper.fromBaseUserRequestDTO(dto);
        newUser.setRole(Role.BASE);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
    }

    @Override
    public void deleteUserById(long id) throws Exception {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO login(LoginDTO loginDTO) throws Exception {
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(()-> new EntityNotFoundException("user not found"));
        if(passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return userMapper.toUserDTO(user);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password Errata");
    }

}