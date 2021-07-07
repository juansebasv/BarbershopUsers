package com.restaurant.users.service;

import com.restaurant.users.crypto.CryptoService;
import com.restaurant.users.dto.UserDto;
import com.restaurant.users.model.User;
import com.restaurant.users.repository.UserRepository;
import com.restaurant.users.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(UserDto userDto) throws Exception {
        User var = userRepository.findByEmail(userDto.getEmail());
        if (null != var && !var.getEmail().trim().isEmpty()) {
            throw new Exception("Email ya existe en el sistema");
        }

        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(CryptoService.encrypt(userDto.getPassword().getBytes(), Constants.secretWord))
                .active(Boolean.TRUE)
                .build();

        userRepository.saveAndFlush(user);
    }

    @Override
    public void updateUser(UserDto userDto) throws Exception {
        User var = userRepository.findByEmail(userDto.getEmail());
        if (null == var || var.getEmail().trim().isEmpty()) {
            throw new Exception("Email no existe en el sistema");
        }

        User user = User.builder()
                .name(userDto.getName())
                .build();

        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUser(int id) throws Exception {
        Optional<User> var = userRepository.findById(id);
        if (null == var) {
            throw new Exception("Usuario no existe en el sistema");
        }

        User user = var.get();
        user.setActive(Boolean.FALSE);

        userRepository.saveAndFlush(user);
    }

    @Override
    public void recoveryPassword() {

    }

    @Override
    public void changePassword(UserDto userDto, String newPassword) throws Exception {

        Optional<User> userOptional = userRepository.findById(userDto.getId());
        User varUser = userOptional.get();

        if (!(CryptoService.decrypt(varUser.getPassword(), Constants.secretWord)).equals(userDto.getPassword())) {
            throw new Exception("La contraseña digitada no corresponde con el usuario");
        }

        if (userDto.getPassword().equals(newPassword)) {
            throw new Exception("Las contraseñas actual debe ser diferente a la anterior");
        }

        String passDecrypt = CryptoService.decrypt(varUser.getPassword(), Constants.secretWord);
        if (passDecrypt.equals(newPassword)) {
            throw new Exception("Las contraseñas actual debe ser diferente a la anterior");
        }

        varUser.setPassword(CryptoService.encrypt(newPassword.getBytes(), Constants.secretWord));
        userRepository.saveAndFlush(varUser);
    }

    @Override
    public List<UserDto> getUsers() throws Exception {
        List<User> usersDB = userRepository.findAll();
        List<UserDto> usersDTO = usersDB.stream().map(var -> {
            UserDto varUser = UserDto.builder()
                    .id(var.getId())
                    .name(var.getName())
                    .email(var.getEmail())
                    .password(var.getPassword())
                    .build();
            return varUser;
        }).collect(Collectors.toList());

        return usersDTO;
    }

}
