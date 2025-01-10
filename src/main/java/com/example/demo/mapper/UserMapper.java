package com.example.demo.mapper;

import com.example.demo.dto.user.BaseUserRequestDTO;
import com.example.demo.dto.user.UserDTO;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    public User fromUserDTO (UserDTO dto);

    public UserDTO toUserDTO (User user);

    public User fromBaseUserRequestDTO (BaseUserRequestDTO dto);

}
