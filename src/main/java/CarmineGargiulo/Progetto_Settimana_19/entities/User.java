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
        "role", "password", "username", "eventsCreated", "bookings"})
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "user_id")
    private UUID userId;
    @Column(nullable = false)
    private String username, password, email, name, surname;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "organizer")
    @Setter(AccessLevel.NONE)
    private List<Event> eventsCreated;
    @OneToMany(mappedBy = "user")
    @Setter(AccessLevel.NONE)
    private List<Booking> bookings;

    public User(String username, String password, String email, String name, String surname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.role = Role.USER;
    }

    @Override
    public String toString() {
        return "User " + userId +
                " = fullName: " + name + " " + surname +
                ", username: " + username +
                ", role: " + role +
                ", email: " + email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
}
