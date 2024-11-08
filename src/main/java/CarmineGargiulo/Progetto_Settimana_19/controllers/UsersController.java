package CarmineGargiulo.Progetto_Settimana_19.controllers;

import CarmineGargiulo.Progetto_Settimana_19.entities.User;
import CarmineGargiulo.Progetto_Settimana_19.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getUsers() {
        return usersService.findAllUsers();
    }

    @GetMapping("/me")
    public User getPersonalProfile(@AuthenticationPrincipal User current) {
        return current;
    }

}
