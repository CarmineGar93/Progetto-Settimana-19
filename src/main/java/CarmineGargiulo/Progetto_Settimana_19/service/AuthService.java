package CarmineGargiulo.Progetto_Settimana_19.service;

import CarmineGargiulo.Progetto_Settimana_19.dto.UserLoginDTO;
import CarmineGargiulo.Progetto_Settimana_19.entities.User;
import CarmineGargiulo.Progetto_Settimana_19.exceptions.NotFoundException;
import CarmineGargiulo.Progetto_Settimana_19.exceptions.UnauthorizedException;
import CarmineGargiulo.Progetto_Settimana_19.tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private JWT jwt;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private UsersService usersService;

    public String generateToken(UserLoginDTO body) {
        try {
            User current = usersService.findUserByUsername(body.username());
            if (bcrypt.matches(body.password(), current.getPassword()))
                return jwt.generateToken(current);
            throw new UnauthorizedException("Invalid credentials");
        } catch (NotFoundException e) {
            throw new UnauthorizedException("Invalid credentials");
        }
    }
}
