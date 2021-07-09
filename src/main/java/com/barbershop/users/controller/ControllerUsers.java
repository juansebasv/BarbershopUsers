package com.barbershop.users.controller;

import com.barbershop.users.dto.UserDto;
import com.barbershop.users.dto.UserNewPasswordDto;
import com.barbershop.users.service.UserService;
import com.barbershop.users.util.Validations;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/app")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class ControllerUsers {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Insertar un nuevo cliente en la aplicacion",
            notes = "Se registran nuevos clientes con los parametros basicos de nombre, email y contraseña",
            response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = UserDto.class, message = "OK"),
            @ApiResponse(code = 404, response = UserDto.class, message = "Not found")})
    @PostMapping("/user/")
    public ResponseEntity<String> insertClient(@RequestBody(required = true) UserDto user) {
        try {
            if (null != user) {
                userService.createUser(user);
                return new ResponseEntity<>("saved", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("user null",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Consultar todos los clientes del sistema",
            notes = "Devuelve una lista con todos usuarios registrados en el sistema",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = List.class, message = "OK"),
            @ApiResponse(code = 404, response = List.class, message = "Not found")})
    @GetMapping("/users/")
    public ResponseEntity<List<UserDto>> getClients() {
        try {
            List<UserDto> users = userService.getUsers();
            if (null != users) {
                return new ResponseEntity<>(users, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Actualizar clientes en el sistema",
            notes = "Se actualizan datos de los clientes en el sistema",
            response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = UserDto.class, message = "OK"),
            @ApiResponse(code = 404, response = UserDto.class, message = "Not found")})
    @PutMapping("/user/")
    public ResponseEntity<String> updateClient(@RequestBody(required = true) UserDto user) {
        try {
            if (null != user) {
                userService.createUser(user);
                return new ResponseEntity<>("updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Actualizar clientes en el sistema",
            notes = "Se actualizan datos de los clientes en el sistema",
            response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = Integer.class, message = "OK"),
            @ApiResponse(code = 404, response = Integer.class, message = "Not found")})
    @DeleteMapping("/user/")
    public ResponseEntity<String> deleteClient(int id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>("deleted", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Cambia la contraseña de un usuario",
            notes = "Permite cambiar la contraseña de los usuarios en el sistema",
            response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = UserNewPasswordDto.class, message = "OK"),
            @ApiResponse(code = 404, response = UserNewPasswordDto.class, message = "Not found")})
    @PostMapping("/changePassword/")
    public ResponseEntity<String> changePassword(@RequestBody UserNewPasswordDto userDto) {
        try {
            if (Validations.isValid(userDto.getEmail()) && Validations.isValid(userDto.getOldPassword()) && Validations.isValid(userDto.getNewPassword())) {
                userService.changePassword(userDto.getEmail(), userDto.getOldPassword(), userDto.getNewPassword());
                return new ResponseEntity<>("password updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("error en los atributos", HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
