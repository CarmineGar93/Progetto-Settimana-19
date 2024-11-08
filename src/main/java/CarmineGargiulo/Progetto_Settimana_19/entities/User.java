package CarmineGargiulo.Progetto_Settimana_19.entities;

import CarmineGargiulo.Progetto_Settimana_19.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"accountNonLocked", "accountNonExpired", "credentialsNonExpired", "enabled", "authorities",
        "role", "password", "username"})
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "user_id")
    private UUID userId;
    @Column(nullable = false)
    private String username, password, email;
    @Column(nullable = false, name = "avatar_url")
    private String avatarUrl;
    @Column(nullable = false, name = "full_name")
    private String fullName;
    private Role role;

    public User(String username, String password, String email, String fullName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        String[] nameSurname = fullName.split(" ");
        this.avatarUrl = "https://ui-avatars.com/api/?name=" + nameSurname[0] + "+" + nameSurname[1];
    }

    @Override
    public String toString() {
        return "User " + userId +
                " = fullName: " + fullName +
                ", username: " + username +
                ", role: " + role +
                ", email: " + email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
}
