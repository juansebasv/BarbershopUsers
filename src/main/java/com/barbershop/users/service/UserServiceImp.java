package com.barbershop.users.service;

import com.barbershop.users.crypto.CryptoService;
import com.barbershop.users.dto.UserDto;
import com.barbershop.users.model.User;
import com.barbershop.users.repository.UserRepository;
import com.barbershop.users.util.Constants;
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
    public void changePassword(String email, String oldPassword, String newPassword) throws Exception {

        User varUser = userRepository.findByEmail(email);
        if (null == varUser) {
            throw new Exception("Usuario no existe en el sistema");
        }

        String passDecrypt = CryptoService.decrypt(varUser.getPassword(), Constants.secretWord);

        if (!(passDecrypt).equals(oldPassword)) {
            throw new Exception("La contraseña digitada no corresponde con el usuario");
        }

        if ((oldPassword.equals(newPassword)) || (passDecrypt.equals(newPassword))) {
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
