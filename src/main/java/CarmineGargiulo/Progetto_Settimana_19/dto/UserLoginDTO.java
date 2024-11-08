package CarmineGargiulo.Progetto_Settimana_19.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserLoginDTO(
        @NotEmpty(message = "Username must be provided")
        @Size(min = 4, max = 16, message = "Username size must be between 4 and 16")
        String username,
        @NotEmpty(message = "Password must be provided")
        @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&+=]).*$",
                message = "Password must have at least 8 characters, one must be a number , one in lower case, one in" +
                        " upper case and one special character @#!$%^&+=")
        String password) {
}
