package CarmineGargiulo.Progetto_Settimana_19.service;

import CarmineGargiulo.Progetto_Settimana_19.dto.UserDTO;
import CarmineGargiulo.Progetto_Settimana_19.entities.User;
import CarmineGargiulo.Progetto_Settimana_19.exceptions.BadRequestException;
import CarmineGargiulo.Progetto_Settimana_19.exceptions.NotFoundException;
import CarmineGargiulo.Progetto_Settimana_19.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder bcrypt;

    public List<User> findAllUsers() {
        return usersRepository.findAll();
    }

    public User findUserById(UUID id) {
        return usersRepository.findById(id).orElseThrow(() -> new NotFoundException("user", id));
    }

    public User findUserByUsername(String username) {
        return usersRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User with given " +
                "username " + username + " not found"));
    }

    public User registerUser(UserDTO body) {
        if (usersRepository.findByUsername(body.username()).isPresent())
            throw new BadRequestException("Username already in use");
        if (usersRepository.findByEmail(body.email()).isPresent())
            throw new BadRequestException("Email already in use");
        User user = new User(body.username(), bcrypt.encode(body.password()), body.email(), body.name(),
                body.surname());
        return usersRepository.save(user);
    }

}
