package com.restaurant.users.service;

import com.restaurant.users.dto.UserDto;

import java.util.List;

public interface UserService {

    public void createUser(UserDto user) throws Exception;

    public void updateUser(UserDto userDto) throws Exception;

    public void deleteUser(int id) throws Exception;

    public void recoveryPassword() throws Exception;

    public void changePassword(UserDto userDto, String newPassword) throws Exception;

    public List<UserDto> getUsers() throws Exception;

}
