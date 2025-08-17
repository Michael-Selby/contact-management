package com.contactmanagementsystem.cms.mapper;

import com.contactmanagementsystem.cms.dto.JwtResponse;
import com.contactmanagementsystem.cms.dto.SignupRequest;
import com.contactmanagementsystem.cms.entity.Role;
import com.contactmanagementsystem.cms.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User signupRequestToUser(SignupRequest signUpRequest);

    @Mapping(target = "token", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRoles")
    JwtResponse userToJwtResponse(User user);

    @Named("mapRoles")
    default List<String> mapRoles(Set<Role> roles) {
        return roles.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList());
    }
}