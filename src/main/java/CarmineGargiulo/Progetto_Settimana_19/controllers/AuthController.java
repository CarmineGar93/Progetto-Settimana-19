package CarmineGargiulo.Progetto_Settimana_19.controllers;

import CarmineGargiulo.Progetto_Settimana_19.dto.UserDTO;
import CarmineGargiulo.Progetto_Settimana_19.dto.UserLoginDTO;
import CarmineGargiulo.Progetto_Settimana_19.dto.UsersTokenDTO;
import CarmineGargiulo.Progetto_Settimana_19.entities.User;
import CarmineGargiulo.Progetto_Settimana_19.exceptions.BadRequestException;
import CarmineGargiulo.Progetto_Settimana_19.service.AuthService;
import CarmineGargiulo.Progetto_Settimana_19.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody @Validated UserDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining(
                            ", "));
            throw new BadRequestException(message);
        }
        return usersService.registerUser(body);
    }

    @PostMapping("/login")
    public UsersTokenDTO login(@RequestBody @Validated UserLoginDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining(
                            ", "));
            throw new BadRequestException(message);
        }
        return new UsersTokenDTO(authService.generateToken(body));
    }
}
