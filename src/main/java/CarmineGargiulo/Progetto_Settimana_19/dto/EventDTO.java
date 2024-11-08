package CarmineGargiulo.Progetto_Settimana_19.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record EventDTO(
        @NotEmpty(message = "Title must be provided")
        @Size(min = 5, max = 255, message = "Title must have more than 4 characters")
        String title,
        @NotEmpty(message = "Description must be provided")
        @Size(min = 20, max = 255, message = "Description must have more than 20 characters")
        String description,
        @NotEmpty(message = "Location must be provided")
        @Size(min = 3, max = 255, message = "Location must have more than 2 characters")
        String location,
        @NotEmpty(message = "Date must be provided")
        String date,
        @Min(value = 1, message = "Event must have at least 1 seat")
        int maxSeats) {
}
